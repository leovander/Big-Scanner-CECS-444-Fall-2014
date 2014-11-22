import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.TextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ScannerWindow {
	private JFrame frmBigScanner;
	private JTextField txtSourcetxt;
	private JTextField textField;

	public static void main(String[] args) throws IOException {
		BigScanner scanner = new BigScanner();
		ScannerWindow window = new ScannerWindow(scanner);
		window.frmBigScanner.setVisible(true);
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
		frmBigScanner.setBounds(0, 0, 1100, 600);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmBigScanner.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Scanner", null, panel, "Enter source file to scan");
		panel.setLayout(null);
		
		txtSourcetxt = new JTextField();
		txtSourcetxt.setText("source.txt");
		txtSourcetxt.setBounds(8, 6, 134, 28);
		panel.add(txtSourcetxt);
		txtSourcetxt.setColumns(10);
		
		TextArea textArea_4 = new TextArea();
		textArea_4.setEditable(false);
		textArea_4.setBounds(10, 58, 464, 217);
		textArea_4.setFont(new Font("Courier New", Font.PLAIN, 12));
		panel.add(textArea_4);
		
		JLabel lblNewLabel = new JLabel("Source File:");
		lblNewLabel.setBounds(10, 36, 76, 16);
		panel.add(lblNewLabel);
		
		TextArea textArea_5 = new TextArea();
		textArea_5.setEditable(false);
		textArea_5.setBounds(484, 58, 585, 464);
		textArea_5.setFont(new Font("Courier New", Font.PLAIN, 12));
		panel.add(textArea_5);
		
		JButton btnNewButton = new JButton("Run Scanner");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					scanner.read_characters(txtSourcetxt.getText(), textArea_4, textArea_5);
					textArea_4.setCaretPosition(0);
					textArea_5.setCaretPosition(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(156, 7, 117, 29);
		panel.add(btnNewButton);
		
		JLabel lblScannerOutput = new JLabel("Scanner Output");
		lblScannerOutput.setBounds(484, 36, 97, 16);
		panel.add(lblScannerOutput);
		
		tabbedPane.setEnabledAt(0, true);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Reserved Words", null, panel_1, "List of words supported");
		tabbedPane.setEnabledAt(1, true);
		panel_1.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 42, 1059, 480);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		panel_1.add(textArea);
		textArea.setEditable(false);
		
		scanner.setReserved("reservedWord.txt", textArea);
		
		textField = new JTextField();
		textField.setBounds(116, 6, 134, 28);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Search reserved:");
		lblNewLabel_1.setBounds(6, 12, 102, 16);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(391, 12, 261, 16);
		panel_1.add(lblNewLabel_2);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(scanner.reservedWords.contains(textField.getText())) {
					lblNewLabel_2.setText(textField.getText().toUpperCase() + " is a reserved word.");
				} else {
					lblNewLabel_2.setText(textField.getText().toUpperCase() + " is NOT a reserved word.");
				}
			}
		});
		btnSearch.setBounds(262, 5, 117, 29);
		panel_1.add(btnSearch);
		
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
		textArea_1.setBounds(10, 10, 1038, 466);
		textArea_1.setFont(new Font("Courier New", Font.PLAIN, 12));
		panel_3.add(textArea_1);
		tabbedPane_1.setBackgroundAt(0, Color.BLUE);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Action", null, panel_4, "Action Table generated from XML");
		panel_4.setLayout(null);
		
		TextArea textArea_2 = new TextArea();
		textArea_2.setEditable(false);
		textArea_2.setBounds(10, 10, 1038, 466);
		textArea_2.setFont(new Font("Courier New", Font.PLAIN, 12));
		panel_4.add(textArea_2);
		tabbedPane_1.setBackgroundAt(1, Color.GREEN);
		
		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Lookup", null, panel_5, "Lookup Table generated from XML");
		panel_5.setLayout(null);
		
		TextArea textArea_3 = new TextArea();
		textArea_3.setEditable(false);
		textArea_3.setBounds(10, 10, 1038, 466);
		textArea_3.setFont(new Font("Courier New", Font.PLAIN, 12));
		panel_5.add(textArea_3);
		tabbedPane_1.setBackgroundAt(2, Color.PINK);
		
		scanner.displayTables(textArea_1, textArea_2, textArea_3);
	}
}