import java.io.IOException;


public class ScannerWindow {
	public static void main(String[] args) throws IOException {
		BigScanner scanner = new BigScanner();
		scanner.read_characters("source.txt");
	}
	
	public ScannerWindow() {
		
	}
}
