import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BigScanner {
	int current_read = 0; 
	int state = 0;
	String token_under_construction = "";
	
	final int [][] state_table = new int[145][33];
	final int [][] action_table = new int[145][33];
	final int [][] look_up_table = new int[145][33];
	
	public BigScanner() throws IOException {
		xmlToJava.createTables(state_table, action_table, look_up_table);
	}
	
	void read_characters(String fileName) throws IOException {
		char current_char = 0; 
		int c; 
		boolean buffered = false;
		
		InputStream inStream = null; 
		InputStreamReader reader = null;
	    BufferedReader bufferedReader = null;
	    ArrayList<Character> list = new ArrayList<Character>();
	    String src = "";
	    
		try {
	        inStream = new FileInputStream(fileName);
	        reader = new InputStreamReader(inStream);
	        bufferedReader = new BufferedReader(reader);
	        
	        while ((c = bufferedReader.read()) != -1){
	        	src += (char) c;
	        	list.add((char) c);
	        }
	        
	        list.add(' ');
	        
	        inStream.close();
			reader.close(); 
			bufferedReader.close();
		} catch (IOException e) {
			//System.out.print("IO Exception caught. \nFile does not exist.");
			//System.exit(1);
		}
	        
        int i = 0;
		while (i < list.size()) {
			if ((!buffered) || (current_char == ' ' || (current_char == '\n'))) {
				current_char = (char) list.get(i);
				i++;
			}
			
			//System.out.println("current char=" + current_char + "\n");

			/*
			 * L=0 D=1 _=2 `=3 <=4 >=5 [=6 ]=7 {=8 }=9 @=10 
			 * &=11 #=12 !=13 ~=14 '=15 "=16 $=17 := 18 ;=19 .= 20 ,=21
			 * +=22 -=23 /=24 *=25 ==26 ^=27 (=28 )=29 \=30 EOL=31 Other=32
			 */
			if (Character.isLetter(current_char)) 
				current_read = 0;
			else if (Character.isDigit(current_char))  
				current_read = 1;
			else if (Character.isSpaceChar(current_char))  
				current_read = 32;
			else {
				switch (current_char) {
				case '_':
					current_read = 2;
					break;
				case '`':
					current_read = 3;
					break;
				case '<': 
					current_read = 4;
					break;
				case '>':
					current_read = 5;
					break;
				case '[':
					current_read = 6;
					break;
				case ']':
					current_read = 7;
					break;
				case '{':
					current_read = 8;
					break;
				case '}':
					current_read = 9;
					break;
				case '@':
					current_read = 10;
					break;
				case '&':
					current_read = 11;
					break;
				case '#':
					current_read = 12;
					break;
				case '!':
					current_read = 13;
					break;
				case '~':
					current_read = 14;
					break;
				case '\'':
					current_read = 15;
					break;
				case '"':
					current_read = 16;
					break;
				case '$':
					current_read = 17;
					break;
				case ':':
					current_read = 18;
					break;
				case ';':
					current_read = 19;
					break;
				case '.':
					current_read = 20;
					break;
				case ',':
					current_read = 21;
					break;
				case '+':
					current_read = 22;
					break;
				case '-':
					current_read = 23;
					break;
				case '/':
					current_read = 24;
					break;
				case '*':
					current_read = 25;
					break;
				case '=':
					current_read = 26;
					break;
				case '^':
					current_read = 27;
					break;
				case '(':
					current_read = 28;
					break;
				case ')':
					current_read = 29;
					break;
				case '\\':
					current_read = 30;
					break;
				case '\n':
					current_read = 31;
					break;
				default:
					current_read = 32;
					break;
				}
			}
			
			System.out.println("current state=" + state + " current_char=" + current_char + " token status=" + token_under_construction);
			
			if ((next_state(state, current_read) != -1) && (action(state, current_read) == 1)) {
				buffered = false;
				token_under_construction = token_under_construction + current_char;
				state = next_state(state, current_read);
			} else if ((next_state(state, current_read) == -1) && (action(state, current_read) == 2)) {
				System.out.println("inside switch with state = " + state + " and char " + current_read + "\n");
				System.out.println("The lookup value is = " + look_up(state, current_read) + "\n");
				System.out.println("We have a buffered character = " + "\"" + current_char + "\"" + "\n");
				
				buffered = true;

				switch (look_up(state, current_read)) {
					case 1:
						System.out.println("TOKEN DISCOVERED IS IDENTIFIER \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 2:
						System.out.println("TOKEN DISCOVERED IS INTEGER \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 3:
						System.out.println("TOKEN DISCOVERED IS SIMPLE OP \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 4:
						System.out.println("TOKEN DISCOVERED IS REAL \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 5:
						System.out.println("TOKEN DISCOVERED IS COMPOUND OP \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 6:
						System.out.println("TOKEN DISCOVERED IS STRING \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 7:
						System.out.println("TOKEN DISCOVERED IS SINGLE COMMENT \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 8:
						System.out.println("TOKEN DISCOVERED IS SIGNED INT OR SIGNED INT COMMA \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 9:
						System.out.println("TOKEN DISCOVERED IS BLOCK COMMENT \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 10:
						System.out.println("TOKEN DISCOVERED IS CURRENCY \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 11:
						System.out.println("TOKEN DISCOVERED IS SIGNED REAL \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 12:
						System.out.println("TOKEN DISCOVERED IS INT COMMA \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 13:
						System.out.println("TOKEN DISCOVERED IS DEVICE \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 14:
						System.out.println("TOKEN DISCOVERED IS SCIENTIFIC \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 15:
						System.out.println("TOKEN DISCOVERED IS REAL COMMA \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 16:
						System.out.println("TOKEN DISCOVERED IS LIBRARY ANGLE \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 17:
						System.out.println("TOKEN DISCOVERED IS LIBRARY QUOTE \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 18:
						System.out.println("TOKEN DISCOVERED IS SIGNED REAL OR SIGNED COMMA \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					case 19:
						System.out.println("TOKEN DISCOVERED IS LIBRARY \t=> " + token_under_construction.toUpperCase() + "\n");
						break;
					default:
						System.out.println("error\n");
						break;
				}
				
				state = 0;
				token_under_construction = "";
			} else if(action(state, current_read) == 0) {
				System.out.println("ERROR WITH CHARACTER: " + Character.toUpperCase(current_char) + "\n");
				token_under_construction = "";
				buffered = false;
				//state = 0;
			}
		}
		
		System.out.println("DONE SCANNING");
	}
	
	int look_up(int new_state, int new_char) {
		return look_up_table[new_state][new_char];
	}
	
	int next_state(int new_state, int new_char) {
		return state_table[new_state][new_char];
	}
	
	int action(int new_state, int new_char) {
		return action_table[new_state][new_char];
	}
}
