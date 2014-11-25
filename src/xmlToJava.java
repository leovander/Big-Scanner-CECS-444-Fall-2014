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
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class xmlToJava {
	//Function that creates the ACTION and LOOKUP TABLES and accepts the final
	//tables needed for the scanner, and fills them up with appropriate values
	//Uses Apache POI to read and write to XML files: http://poi.apache.org/ 
    static void createTables(int[][] state_table, int [][] action_table, int [][] lookup_table) throws IOException	{
    	FileInputStream fileIn = null;
        FileOutputStream fileOut = null;

        try
        {
        	//Reads in an EXCEL file that only contains a STATE TABLE and creates
        	//new table pages for the final EXCEL workbook
        	fileIn = new FileInputStream("stateTable.xls");
        	POIFSFileSystem fs = new POIFSFileSystem(fileIn);
        	HSSFWorkbook wb = new HSSFWorkbook(fs);
        	HSSFSheet stateSheet = wb.getSheetAt(0);
        	HSSFSheet stateCodeSheet = wb.createSheet("State Code");
        	HSSFSheet actionSheet = wb.createSheet("Action");
        	HSSFSheet actionCodeSheet = wb.createSheet("Action Code");
        	HSSFSheet lookupSheet = wb.createSheet("Lookup");
        	HSSFSheet lookupCodeSheet = wb.createSheet("Lookup Code");
        	
        	//Creating temporary rows and cells for copying and editing data
        	//into new sheets from original STATE sheet
        	HSSFRow stateCodeRow, actionRow, actionCodeRow, lookupRow, lookupCodeRow;
    		HSSFCell stateCodeCell, actionCell, actionCodeCell, lookupCell, lookupCodeCell;
    		Cell rowHeader;
    		
    		int overallPos = 0;
    		int statePos = 0;
    		//Iterates over every row round in the STATE sheet
    		for (Row row : stateSheet) {
    			//Setting the header for each row, i.e. what state it is
    			//and if it accepts anything
        		rowHeader = row.getCell(0);
        		stateCodeRow = stateCodeSheet.createRow(overallPos);
        		actionRow = actionSheet.createRow(overallPos);
        		actionCodeRow = actionCodeSheet.createRow(overallPos);
        		lookupRow = lookupSheet.createRow(overallPos);
        		lookupCodeRow = lookupCodeSheet.createRow(overallPos);
        		
        		//Setting all the header column types to STRING
        		stateCodeCell = stateCodeRow.createCell(0);
        		stateCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
        		stateCodeCell.setCellValue(rowHeader.toString());
        		
        		actionCell = actionRow.createCell(0);
        		actionCell.setCellType(HSSFCell.CELL_TYPE_STRING);
        		actionCell.setCellValue(rowHeader.toString());
        		
        		actionCodeCell = actionCodeRow.createCell(0);
        		actionCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
        		actionCodeCell.setCellValue(rowHeader.toString());
        		
        		lookupCell = lookupRow.createCell(0);
        		lookupCell.setCellType(HSSFCell.CELL_TYPE_STRING);
        		lookupCell.setCellValue(rowHeader.toString());
        		
        		lookupCodeCell = lookupCodeRow.createCell(0);
        		lookupCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
        		lookupCodeCell.setCellValue(rowHeader.toString());
        		
        		//This case is only for the main header row in the STATE sheet,
        		//containing the scanner alphabet
        		if(rowHeader.toString().compareTo("") != 0) {
        			//If the row header contains an accept state, enter this
        			//condition
        			if(rowHeader.toString().contains("accept")) {
	        			int cellPos = 1;
	        			int alphabetPos = 0;
	        			//Retrieve what the state accepts
	        			String[] acceptType = rowHeader.toString().split(" ");
	        			for(Cell cell : row) {
	        				if(cell.getColumnIndex() != 0) {
	        					//Setting each cell to STRING
	        					stateCodeCell = stateCodeRow.createCell(cellPos);
		                		stateCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        					
	        					actionCell = actionRow.createCell(cellPos);
		                		actionCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		                		
		                		actionCodeCell = actionCodeRow.createCell(cellPos);
		                		actionCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			        			
		                		lookupCell = lookupRow.createCell(cellPos);
		                		lookupCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		                		
		                		lookupCodeCell = lookupCodeRow.createCell(cellPos);
		                		lookupCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		                		
		                		//If the state can go anywhere on a particular
		                		//character we set the STATE CODE table to itself
		                		//ACTION table to MA and ACTION CODE to 1 and
		                		//LOOKUP table to N/A and LOOKUP CODE to 0
		                		if(cell.toString().compareTo("*") != 0) {
		                			stateCodeCell.setCellValue(cell.toString());
		                			state_table[statePos][alphabetPos] = (int) Double.parseDouble(cell.toString());
		                			
		                			actionCell.setCellValue("MA");
			        				actionCodeCell.setCellValue("1");
			        				action_table[statePos][alphabetPos] = 1;
			        				
			        				lookupCell.setCellValue("N/A");
			        				lookupCodeCell.setCellValue("0");
			        				lookup_table[statePos][alphabetPos] = 0;
		        				//else the state can't go anywhere on a particular
		                		//character we set the STATE CODE table to -1
		                		//ACTION table to HR and ACTION CODE to 2 and
		                		//LOOKUP table to its given type and LOOKUP CODE
			        			//to its according lookup value 
		                		} else {
			        				stateCodeCell.setCellValue("-1");
			        				state_table[statePos][alphabetPos] = -1;
			        				
			        				actionCell.setCellValue("HR");
			        				actionCodeCell.setCellValue("2");
			        				action_table[statePos][alphabetPos] = 2;
			        				
			        				lookupCell.setCellValue(acceptType[2]);
			        				
			        				String lookupValue;
			        				switch(acceptType[2]) {
			        					case "id": lookupValue = "1"; break;
			        					case "int": lookupValue = "2"; break;
			        					case "`": lookupValue = "3"; break;
			        					case "<": lookupValue = "3"; break;
			        					case ">": lookupValue = "3"; break;
			        					case "[": lookupValue = "3"; break;
			        					case "]": lookupValue = "3"; break;
			        					case "{": lookupValue = "3"; break;
			        					case "}": lookupValue = "3"; break;
			        					case "@": lookupValue = "3"; break;
			        					case "&": lookupValue = "3"; break;
			        					case "#": lookupValue = "3"; break;
			        					case "!": lookupValue = "3"; break;
			        					case "~": lookupValue = "3"; break;
			        					case "'": lookupValue = "3"; break;
			        					case "quote": lookupValue = "3"; break;
			        					case "$": lookupValue = "3"; break;
			        					case ":": lookupValue = "3"; break;
			        					case ";": lookupValue = "3"; break;
			        					case ".": lookupValue = "3"; break;
			        					case ",": lookupValue = "3"; break;
			        					case "+": lookupValue = "3"; break;
			        					case "-": lookupValue = "3"; break;
			        					case "/": lookupValue = "3"; break;
			        					case "*": lookupValue = "3"; break;
			        					case "=": lookupValue = "3"; break;
			        					case "^": lookupValue = "3"; break;
			        					case "(": lookupValue = "3"; break;
			        					case ")": lookupValue = "3"; break;
			        					case "real": lookupValue = "4"; break;
			        					case "<<": lookupValue = "5"; break;
			        					case "<>": lookupValue = "5"; break;
			        					case "<=": lookupValue = "5"; break;
			        					case ">>": lookupValue = "5"; break;
			        					case ">=": lookupValue = "5"; break;
			        					case "!!": lookupValue = "5"; break;
			        					case "!=": lookupValue = "5"; break;
			        					case "string": lookupValue = "6"; break;
			        					case ":=": lookupValue = "5"; break;
			        					case "++": lookupValue = "5"; break;
			        					case "+-": lookupValue = "5"; break;
			        					case "-+": lookupValue = "5"; break;
			        					case "--": lookupValue = "5"; break;
			        					case "/=": lookupValue = "5"; break;
			        					case "==": lookupValue = "5"; break;
			        					case "commentsingle": lookupValue = "7"; break;
			        					case "signedintorintcomma": lookupValue = "8"; break;
			        					case "commentblock": lookupValue = "9"; break;
			        					case "currency": lookupValue = "10"; break;
			        					case "signedreal": lookupValue = "11"; break;
			        					case "intcomma": lookupValue = "12"; break;
			        					case "device": lookupValue = "13"; break;
			        					case "scientific": lookupValue = "14"; break;
			        					case "realcomma": lookupValue = "15"; break;
			        					case "libraryangle": lookupValue = "16"; break;
			        					case "libraryquote": lookupValue = "17"; break;
			        					case "signedrealcomma": lookupValue = "18"; break;
			        					default: lookupValue = "19"; break;
			        				}
			        				lookupCodeCell.setCellValue(lookupValue);
			        				lookup_table[statePos][alphabetPos] = Integer.parseInt(lookupValue);
			        			}
			        			cellPos++;
			        			alphabetPos++;
	        				}
		        		}
	        		} else {
	        			//If we reach here we are at a non-accept state
	        			int cellPos = 1;
	        			int alphabetPos = 0;
	        			for(Cell cell : row) {
	        				if(cell.getColumnIndex() != 0) {
	        					//We set each column header here with type string
	        					stateCodeCell = stateCodeRow.createCell(cellPos);
		                		stateCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        					
	        					actionCell = actionRow.createCell(cellPos);
		                		actionCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		                		
		                		actionCodeCell = actionCodeRow.createCell(cellPos);
		                		actionCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		                		
		                		lookupCell = lookupRow.createCell(cellPos);
		                		lookupCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        				
		                		lookupCodeCell = lookupCodeRow.createCell(cellPos);
		                		lookupCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		                		
		                		//If the state can go anywhere on a particular
		                		//character we set the STATE CODE table to itself
		                		//ACTION table to MA and ACTION CODE to 1 and
		                		//LOOKUP table to N/A and LOOKUP CODE to 0
		                		if(cell.toString().compareTo("*") != 0) {
		                			stateCodeCell.setCellValue(cell.toString());
		                			state_table[statePos][alphabetPos] = (int) Double.parseDouble(cell.toString());
		                			
		                			actionCell.setCellValue("MA");
		        					actionCodeCell.setCellValue("1");
		        					action_table[statePos][alphabetPos] = 1;
		        					
		        					lookupCell.setCellValue("N/A");
		        					lookupCodeCell.setCellValue("0");
		        					lookup_table[statePos][alphabetPos] = 0;
	        					//else the state can't go anywhere on a particular
		                		//character we set the STATE CODE table to -1
		                		//ACTION table to ER and ACTION CODE to 0 and
		                		//LOOKUP table to N/A and LOOKUP CODE to 0 
		                		} else {
			        				stateCodeCell.setCellValue("-1");
			        				state_table[statePos][alphabetPos] = -1;
			        				
			        				actionCell.setCellValue("ER");
			        				actionCodeCell.setCellValue("0");
			        				action_table[statePos][alphabetPos] = 0;
			        				
			        				lookupCell.setCellValue("N/A");
			        				lookupCodeCell.setCellValue("0");
			        				lookup_table[statePos][alphabetPos] = 0;
			        			}
		        				cellPos++;
		        				alphabetPos++;
	        				}
	        			}
	        		}
        			
        			statePos++;
        		} else {
        			//This condition only exists to copy and create the original
        			//alphabet header
        			HSSFRow sCRow = stateCodeSheet.createRow(0);
        			HSSFRow aRow = actionSheet.createRow(0);        			
        			HSSFRow aCRow = actionCodeSheet.createRow(0);
        			HSSFRow lRow = lookupSheet.createRow(0);
        			HSSFRow lCRow = lookupCodeSheet.createRow(0);
        			
        			int pos = 0;
        			for(Cell cell : row) {
        				HSSFCell stateCCell = sCRow.createCell(pos);
	        			stateCCell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        			stateCCell.setCellValue(cell.toString());
        				
	        			HSSFCell aCCell = aRow.createCell(pos);
	        			aCCell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        			aCCell.setCellValue(cell.toString());
	        			
	        			HSSFCell actionCCell = aCRow.createCell(pos);
	        			actionCCell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        			actionCCell.setCellValue(cell.toString());
	        			
	        			HSSFCell lookCell = lRow.createCell(pos);
	        			lookCell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        			lookCell.setCellValue(cell.toString());
	        			
	        			HSSFCell lookupCCell = lCRow.createCell(pos);
	        			lookupCCell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        			lookupCCell.setCellValue(cell.toString());
	        			
	        			pos++;
	        		}
        		}
        		overallPos++;
        	}
    		//Write the new tables to the excel sheet
            fileOut = new FileOutputStream("allTables.xls");
            wb.write(fileOut);
        } finally {
            if (fileOut != null)
                fileOut.close();
            if (fileIn != null)
                fileIn.close();
        }
    }
}