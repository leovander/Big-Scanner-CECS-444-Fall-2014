//package org.apache.poi.hssf.usermodel.examples;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class xmlToJava {
    static void createTables(int[][] state_table, int [][] action_table, int [][] lookup_table) throws IOException	{
    	FileInputStream fileIn = null;
        FileOutputStream fileOut = null;

        try
        {
            //Read in file
        	fileIn = new FileInputStream("stateTable.xls");
            //Create a new I/O for excel
        	POIFSFileSystem fs = new POIFSFileSystem(fileIn);
            //New notebook to read from
        	HSSFWorkbook wb = new HSSFWorkbook(fs);
            //Grab the first sheet
        	HSSFSheet stateSheet = wb.getSheetAt(0);
        	HSSFSheet stateCodeSheet = wb.createSheet("State Code");
        	HSSFSheet actionSheet = wb.createSheet("Action");
        	HSSFSheet actionCodeSheet = wb.createSheet("Action Code");
        	HSSFSheet lookupSheet = wb.createSheet("Lookup");
        	HSSFSheet lookupCodeSheet = wb.createSheet("Lookup Code");
        	
        	int overallPos = 0;
        	HSSFRow stateCodeRow, actionRow, actionCodeRow, lookupRow, lookupCodeRow;
    		HSSFCell stateCodeCell, actionCell, actionCodeCell, lookupCell, lookupCodeCell;
    		Cell rowHeader;
    		
    		//Go through each row of the State Sheet
    		int statePos = 0;
    		for (Row row : stateSheet) {
        		rowHeader = row.getCell(0);
        		
        		//Copies the first column and pastes it into the new sheet
        		stateCodeRow = stateCodeSheet.createRow(overallPos);
        		actionRow = actionSheet.createRow(overallPos);
        		actionCodeRow = actionCodeSheet.createRow(overallPos);
        		lookupRow = lookupSheet.createRow(overallPos);
        		lookupCodeRow = lookupCodeSheet.createRow(overallPos);
        		
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
        		
        		//Only checks for the first header row
        		if(rowHeader.toString().compareTo("") != 0) {
        			//ACCEPT STATE
        			if(rowHeader.toString().contains("accept")) {
	        			int cellPos = 1;
	        			int alphabetPos = 0;
	        			String[] acceptType = rowHeader.toString().split(" ");
	        			for(Cell cell : row) {
	        				if(cell.getColumnIndex() != 0) {
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
		                		
		                		if(cell.toString().compareTo("*") != 0) {
		                			stateCodeCell.setCellValue(cell.toString());
		                			state_table[statePos][alphabetPos] = (int) Double.parseDouble(cell.toString());
		                			
		                			actionCell.setCellValue("MA");
			        				actionCodeCell.setCellValue("1");
			        				action_table[statePos][alphabetPos] = 1;
			        				
			        				lookupCell.setCellValue("N/A");
			        				lookupCodeCell.setCellValue("0");
			        				lookup_table[statePos][alphabetPos] = 0;
			        			} else {
			        				stateCodeCell.setCellValue("-1");
			        				state_table[statePos][alphabetPos] = -1;
			        				
			        				actionCell.setCellValue("HR");
			        				actionCodeCell.setCellValue("2");
			        				action_table[statePos][alphabetPos] = 2;
			        				
			        				lookupCell.setCellValue(acceptType[2]);
			        				
			        				String lookupValue;
			        				switch(acceptType[2]) {
			        					case "id": lookupValue = "1";
			        						break;
			        					case "int": lookupValue = "2";
		        							break;
			        					case "`": lookupValue = "3";
		        							break;
			        					case "<": lookupValue = "3";
	        								break;
			        					case ">": lookupValue = "3";
        									break;
			        					case "[": lookupValue = "3";
        									break;
			        					case "]": lookupValue = "3";
        									break;
			        					case "{": lookupValue = "3";
    										break;
			        					case "}": lookupValue = "3";
											break;
			        					case "@": lookupValue = "3";
											break;
			        					case "&": lookupValue = "3";
											break;
			        					case "#": lookupValue = "3";
											break;
			        					case "!": lookupValue = "3";
											break;
			        					case "~": lookupValue = "3";
											break;
			        					case "'": lookupValue = "3";
											break;
			        					case "quote": lookupValue = "3";
											break;
			        					case "$": lookupValue = "3";
											break;
			        					case ":": lookupValue = "3";
											break;
			        					case ";": lookupValue = "3";
											break;
			        					case ".": lookupValue = "3";
											break;
			        					case ",": lookupValue = "3";
											break;
			        					case "+": lookupValue = "3";
											break;
			        					case "-": lookupValue = "3";
											break;
			        					case "/": lookupValue = "3";
											break;
			        					case "*": lookupValue = "3";
											break;
			        					case "=": lookupValue = "3";
											break;
			        					case "^": lookupValue = "3";
											break;
			        					case "(": lookupValue = "3";
											break;
			        					case ")": lookupValue = "3";
											break;
			        					case "real": lookupValue = "4";
											break;
			        					case "<<": lookupValue = "5";
											break;
			        					case "<>": lookupValue = "5";
											break;
			        					case "<=": lookupValue = "5";
											break;
			        					case ">>": lookupValue = "5";
											break;
			        					case ">=": lookupValue = "5";
											break;
			        					case "!!": lookupValue = "5";
											break;
			        					case "!=": lookupValue = "5";
											break;
			        					case "string": lookupValue = "6";
											break;
			        					case ":=": lookupValue = "5";
											break;
			        					case "++": lookupValue = "5";
											break;
			        					case "+-": lookupValue = "5";
											break;
			        					case "-+": lookupValue = "5";
											break;
			        					case "--": lookupValue = "5";
											break;
			        					case "/=": lookupValue = "5";
											break;
			        					case "==": lookupValue = "5";
											break;
			        					case "commentsingle": lookupValue = "7";
											break;
			        					case "signedintorintcomma": lookupValue = "8";
											break;
			        					case "commentblock": lookupValue = "9";
											break;
			        					case "currency": lookupValue = "10";
											break;
			        					case "signedreal": lookupValue = "11";
											break;
			        					case "intcomma": lookupValue = "12";
											break;
			        					case "device": lookupValue = "13";
											break;
			        					case "scientific": lookupValue = "14";
											break;
			        					case "realcomma": lookupValue = "15";
											break;
			        					case "libraryangle": lookupValue = "16";
											break;
			        					case "libraryquote": lookupValue = "17";
											break;
			        					case "signedrealcomma": lookupValue = "18";
											break;
			        					//library
			        					default: lookupValue = "19";
											break;
			        				}
			        				lookupCodeCell.setCellValue(lookupValue);
			        				lookup_table[statePos][alphabetPos] = Integer.parseInt(lookupValue);
			        			}
			        			cellPos++;
			        			alphabetPos++;
	        				}
		        		}
	        		} else {
	        			//NON-ACCEPT STATE
	        			int cellPos = 1;
	        			int alphabetPos = 0;
	        			for(Cell cell : row) {
	        				if(cell.getColumnIndex() != 0) {
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
		                		
		                		if(cell.toString().compareTo("*") != 0) {
		                			stateCodeCell.setCellValue(cell.toString());
		                			state_table[statePos][alphabetPos] = (int) Double.parseDouble(cell.toString());
		                			
		                			actionCell.setCellValue("MA");
		        					actionCodeCell.setCellValue("1");
		        					action_table[statePos][alphabetPos] = 1;
		        					
		        					lookupCell.setCellValue("N/A");
		        					lookupCodeCell.setCellValue("0");
		        					lookup_table[statePos][alphabetPos] = 0;
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
        			//Logic for main top header
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
    		
            // Write the output to a file
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