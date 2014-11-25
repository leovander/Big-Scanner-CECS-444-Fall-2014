/* Israel Torres
 * 006997443
 * CECS 444
 * Tues/Thurs 11:00am
 * Professor Konig
 * November 24, 2014
 * Scanner Project
 * 
 * I alone wrote and modified what is turned in here.
 */
import java.io.File;
import java.awt.Font;
import java.util.Map;
import java.awt.TextArea;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class BigScanner {
	//Setting our current read, the state, and current token under construction
	int current_read = 0; 
	int state = 0;
	String token_under_construction = "";
	String outputToFile = "";
	//Create the symbol table to store user created identifiers and a list
	//of reserved words to match against
	Map<String, Integer> symbolTable = new HashMap<String, Integer>();
	Map<String, Integer> sortedSymbolTable;
	ArrayList<String> reservedWords = new ArrayList<String>();
	
	//The three tables that will be populated via the xmlToJava class
	final int [][] state_table = new int[145][33];
	final int [][] action_table = new int[145][33];
	final int [][] look_up_table = new int[145][33];
	
	//When the new BigScanner object is created, the tables are now populated
	//from the original XML file
	public BigScanner() throws IOException {
		xmlToJava.createTables(state_table, action_table, look_up_table);
	}
	
	//Takes in the file of the code to run the parser on, where to output the
	//original source code, and the output of the scanner
	void read_characters(String fileName, TextArea source, TextArea output) throws IOException {
		char current_char = 0; 
		int c; 
		boolean buffered = false;
		
		InputStream inStream = null; 
		InputStreamReader reader = null;
	    BufferedReader bufferedReader = null;
	    //Creata an ArrayList of Characters to read the whole file
	    ArrayList<Character> list = new ArrayList<Character>();
	    symbolTable.clear();
	    String src = "";
	    
		try {
	        inStream = new FileInputStream(fileName);
	        reader = new InputStreamReader(inStream);
	        bufferedReader = new BufferedReader(reader);
	        
	        //Print the source file as we get it and add the characters to 
	        //the ArrayList of Characters
	        while ((c = bufferedReader.read()) != -1){
	        	src += (char) c;
	        	list.add((char) c);
	        }
	        
	        source.append(src);
	        source.setCaretPosition(0);
	        //Add two new lines in order to accept the last token of a file
	        list.add('\n');
	        list.add('\n');
	        
	        inStream.close();
			reader.close(); 
			bufferedReader.close();
		} catch (IOException e) {
			System.out.print("IO Exception caught. \nFile does not exist.");
			System.exit(1);
		}
	        
        int i = 0;
		//while loop that runs until we get to the end of the character ArrayList
        while (i < list.size()) {
			if ((!buffered) || (current_char == ' ' || (current_char == '\n'))) {
				current_char = (char) list.get(i);
				i++;
			}
			
			//System.out.println("current char=" + current_char);

			/* The Read Values to lead into the state tables
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
				case '_': current_read = 2; break;
				case '`': current_read = 3; break;
				case '<': current_read = 4; break;
				case '>': current_read = 5; break;
				case '[': current_read = 6; break;
				case ']': current_read = 7; break;
				case '{': current_read = 8; break;
				case '}': current_read = 9; break;
				case '@': current_read = 10; break;
				case '&': current_read = 11; break;
				case '#': current_read = 12; break;
				case '!': current_read = 13; break;
				case '~': current_read = 14; break;
				case '\'': current_read = 15; break;
				case '"': current_read = 16; break;
				case '$': current_read = 17; break;
				case ':': current_read = 18; break;
				case ';': current_read = 19; break;
				case '.': current_read = 20; break;
				case ',': current_read = 21; break;
				case '+': current_read = 22; break;
				case '-': current_read = 23; break;
				case '/': current_read = 24; break;
				case '*': current_read = 25; break;
				case '=': current_read = 26; break;
				case '^': current_read = 27; break;
				case '(': current_read = 28; break;
				case ')': current_read = 29; break;
				case '\\': current_read = 30; break;
				case '\n': current_read = 31; break;
				default: current_read = 32; break;
				}
			}
			
			//System.out.println("current state=" + state + " current_char=" + current_char + " token status=" + token_under_construction);
			
			//Checks that we have a valid character and of next_state and action,
			//continuing to build the token
			if ((next_state(state, current_read) != -1) && (action(state, current_read) == 1)) {
				buffered = false;
				token_under_construction = token_under_construction + current_char;
				state = next_state(state, current_read);
			} else if ((next_state(state, current_read) == -1) && (action(state, current_read) == 2)) {
				//System.out.println("inside switch with state = " + state + " and char " + current_read);
				//System.out.println("The lookup value is = " + look_up(state, current_read));
				//System.out.println("We have a buffered character = " + "\"" + current_char + "\"");
				
				//If we arrive here, the token has been completed and ready to
				//be accepted and printed out
				buffered = true;
				String token = "";
				switch (look_up(state, current_read)) {
					case 1:
						//If we get a token that is in the reservedWords imported,
						//we do nothing but print that we found the word
						if(reservedWords.contains(token_under_construction.toLowerCase())) {
							token = "=> identifier and reserved word";
						} else if(symbolTable.containsKey(token_under_construction)) {
							//If the token is created by the user and already was
							//found, we increment the count of the amount of times
							//the given token was found
							symbolTable.put(token_under_construction, symbolTable.get(token_under_construction) + 1);
							token = "=> identifier EXISTS in table (" + symbolTable.get(token_under_construction) + ")";
						} else {
							//If the token is created by the use and has not been
							//entered into the symbolTable, we add it to the table
							//and set the count to 1
							symbolTable.put(token_under_construction, 1);
							token = "=> identifier placed into table";
						}
						break;
					case 2: token = "=> valid integer"; break;
					case 3: token = "=> simple op " + token_under_construction; break;
					case 4: token = "=> valid real"; break;
					case 5: token = "=> compund op " + token_under_construction; break;
					case 6: token = "=> string literal"; break;
					case 7: token = "=> single comment"; break;
					case 8: token = "=> signed int or signed int comma"; break;
					case 9: token = "=> block comment"; break;
					case 10: token = "=> currency"; break;
					case 11: token = "=> signed real"; break;
					case 12: token = "=> int comma"; break;
					case 13: token = "=> device"; break;
					case 14: token = "=> valid scientific"; break;
					case 15: token = "=> real comma"; break;
					case 16: token = "=> library angle"; break;
					case 17: token = "=> library quote"; break;
					case 18: token = "=> signed real or signed comma"; break;
					case 19: token = "=> library"; break;
					default: token = "=> error"; break;
				}
				
				output.append(String.format("%-40s  %-40s", token_under_construction.trim(), token));
				output.append("\n");
				outputToFile += String.format("%-40s  %-40s", token_under_construction.trim(), token);
				outputToFile += "\n";
				state = 0;
				token_under_construction = "";
			} else if(action(state, current_read) == 0) {
				//output.append(String.format("%-40s  %-40s", current_char, "=> error with character"));
				token_under_construction = "";
				buffered = false;
				state = 0;
			}
		}
		sortedSymbolTable = new TreeMap<String, Integer>(symbolTable);
		
		output.setCaretPosition(0);
		
		File file = new File("output.txt");
		 
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(outputToFile);
		bw.close();

		System.out.println("Done");
		
		//System.out.println("DONE SCANNING");
	}
	
	//The next three are helper functions that check for the next state of
	//a given table
	int look_up(int new_state, int new_char) {
		return look_up_table[new_state][new_char];
	}
	
	int next_state(int new_state, int new_char) {
		return state_table[new_state][new_char];
	}
	
	int action(int new_state, int new_char) {
		return action_table[new_state][new_char];
	}
	
	//This just fills out the reservedWord textArea for display, as well as
	//adding these words to an ArrayList of strings for the scanner to
	//compare tokens to
	void setReserved(String fileName, TextArea reservedArea) throws IOException {
		reservedWords.clear();
		Scanner scanner = new Scanner(new File(fileName));
		
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			line.trim();
			reservedWords.add(line);
		}
		scanner.close();
		
		Collections.sort(reservedWords);
		reservedArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		
		int count = 1;
		for(String word: reservedWords) {
			reservedArea.append(String.format("%-30s", word));
			if(count % 5 == 0) {
				reservedArea.append("\n");
			}
			count++;
		}
		
		reservedArea.setCaretPosition(0);
	}
	
	//A helper function that displays the tables that were created via
	//the XML sheet originally provided
	void displayTables(TextArea state, TextArea action, TextArea look_up) {	
		state.append(String.format("%c%7c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8s%8s",
				' ', 'L', 'D', '_', '`', '<', '>', '[', ']', '{', '}', '@', '&', '#', '!', '~', '\'', '"', '$', ':', ';', '.', ',', '+',
				'-', '/', '*', '=', '^', '(', ')', '\\', "EOL", "Other"));
		
		action.append(String.format("%c%7c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8s%8s",
				' ', 'L', 'D', '_', '`', '<', '>', '[', ']', '{', '}', '@', '&', '#', '!', '~', '\'', '"', '$', ':', ';', '.', ',', '+',
				'-', '/', '*', '=', '^', '(', ')', '\\', "EOL", "Other"));
		
		look_up.append(String.format("%c%7c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8c%8s%8s",
				' ', 'L', 'D', '_', '`', '<', '>', '[', ']', '{', '}', '@', '&', '#', '!', '~', '\'', '"', '$', ':', ';', '.', ',', '+',
				'-', '/', '*', '=', '^', '(', ')', '\\', "EOL", "Other"));
		
		state.append("\n");
		action.append("\n");
		look_up.append("\n");
		
		for(int i = 0; i < 145; i++) {
			state.append(String.format("%s%7d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d",
										("S" + i), state_table[i][0], state_table[i][1], state_table[i][2], state_table[i][3], state_table[i][4],
										state_table[i][5], state_table[i][6], state_table[i][7], state_table[i][8], state_table[i][9],
										state_table[i][10], state_table[i][11], state_table[i][12], state_table[i][13], state_table[i][14],
										state_table[i][15], state_table[i][16], state_table[i][17], state_table[i][18], state_table[i][19],
										state_table[i][20], state_table[i][21], state_table[i][22], state_table[i][23], state_table[i][24],
										state_table[i][25], state_table[i][26], state_table[i][27], state_table[i][28], state_table[i][29],
										state_table[i][30], state_table[i][31], state_table[i][32]));
			  
			action.append(String.format("%s%7d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d",
										("S" + i), action_table[i][0], action_table[i][1], action_table[i][2], action_table[i][3], action_table[i][4],
										action_table[i][5], action_table[i][6], action_table[i][7], action_table[i][8], action_table[i][9],
										action_table[i][10], action_table[i][11], action_table[i][12], action_table[i][13], action_table[i][14],
										action_table[i][15], action_table[i][16], action_table[i][17], action_table[i][18], action_table[i][19],
										action_table[i][20], action_table[i][21], action_table[i][22], action_table[i][23], action_table[i][24],
										action_table[i][25], action_table[i][26], action_table[i][27], action_table[i][28], action_table[i][29],
										action_table[i][30], action_table[i][31], action_table[i][32]));
			  
			look_up.append(String.format("%s%7d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d%8d",
										("S" + i), look_up_table[i][0], look_up_table[i][1], look_up_table[i][2], look_up_table[i][3], look_up_table[i][4],
										look_up_table[i][5], look_up_table[i][6], look_up_table[i][7], look_up_table[i][8], look_up_table[i][9],
										look_up_table[i][10], look_up_table[i][11], look_up_table[i][12], look_up_table[i][13], look_up_table[i][14],
										look_up_table[i][15], look_up_table[i][16], look_up_table[i][17], look_up_table[i][18], look_up_table[i][19],
										look_up_table[i][20], look_up_table[i][21], look_up_table[i][22], look_up_table[i][23], look_up_table[i][24],
										look_up_table[i][25], look_up_table[i][26], look_up_table[i][27], look_up_table[i][28], look_up_table[i][29],
										look_up_table[i][30], look_up_table[i][31], look_up_table[i][32]));
			
			state.append("\n");
			action.append("\n");
			look_up.append("\n");
		}
	}
}