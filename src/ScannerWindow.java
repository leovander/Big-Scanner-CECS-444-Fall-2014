import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;


public class ScannerWindow {
	private JFrame frmBigScanner;

	public static void main(String[] args) throws IOException {
		BigScanner scanner = new BigScanner();
		ScannerWindow window = new ScannerWindow(scanner);
		window.frmBigScanner.setVisible(true);
		
		//scanner.read_characters("source.txt");
	}
	
	/**
	 * @throws IOException 
	 * @wbp.parser.entryPoint
	 */
	public ScannerWindow(BigScanner scanner) throws IOException {
		frmBigScanner = new JFrame();
		frmBigScanner.setTitle("Big Scanner");
		frmBigScanner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBigScanner.setResizable(false);
		frmBigScanner.setBounds(0, 0, 1000, 600);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmBigScanner.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Main", null, panel, "Enter source file to scan");
		panel.setLayout(null);
		tabbedPane.setEnabledAt(0, true);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Reserved Words", null, panel_1, "List of words supported");
		tabbedPane.setEnabledAt(1, true);
		panel_1.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 10, 959, 337);
		panel_1.add(textArea);
		textArea.setEditable(false);
		
		//TODO: Fill reversed ArrayList<String>
		scanner.setReserved("reservedWord.txt", textArea);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Tables", null, panel_2, "Display of State, Action, and Lookup Tables");
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel_2.add(tabbedPane_1);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("State", null, panel_3, "State Table from XML");
		tabbedPane_1.setEnabledAt(0, true);
		panel_3.setLayout(null);
		
		TextArea textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(10, 10, 938, 466);
		panel_3.add(textArea_1);
		tabbedPane_1.setBackgroundAt(0, Color.BLUE);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Action", null, panel_4, "Action Table generated from XML");
		panel_4.setLayout(null);
		
		TextArea textArea_2 = new TextArea();
		textArea_2.setEditable(false);
		textArea_2.setBounds(10, 10, 938, 466);
		panel_4.add(textArea_2);
		tabbedPane_1.setBackgroundAt(1, Color.GREEN);
		
		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Lookup", null, panel_5, "Lookup Table generated from XML");
		panel_5.setLayout(null);
		
		TextArea textArea_3 = new TextArea();
		textArea_3.setEditable(false);
		textArea_3.setBounds(10, 10, 938, 466);
		panel_5.add(textArea_3);
		tabbedPane_1.setBackgroundAt(2, Color.PINK);
		
		//TODO: Fill State, Action, and Lookup Tables
		scanner.displayTables(textArea_1, textArea_2, textArea_3);
	}
}