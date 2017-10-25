import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Author: Soruabh Jain, Parth Shah and Bhasker Gautam
 * Master students of Computer Science
 * National Institute of Technology, Karnataka
 */

public class OutputFile {
	
	/*
	 * Copy input file to output file
	 * replace given input with predicted output.
	 * Input: Input filename, Output filename and result array list
	 * Output: File contains words with predicted output 
	 */
	public static void GenerateOutputFile(String file1, String file2, ArrayList<String> resultList) throws IOException {
		/*
		 * Input and output stream to read and write into files
		 */
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			/*
			 * Open file to read and write data byte by byte.
			 */
			in = new FileInputStream(file1);
			out = new FileOutputStream(file2);
		
			/*
			 * read byte from input file will stored in c
			 * temp is used access resultList.
			 */
			char c;
			int temp = 0;
			
			/*
			 * Read a byte from file.
			 * If it is either 0 or 1
			 * replace it with predicted output.
			 * else write byte as it in output file.
			 */
			while((c = (char)in.read()) != -1 && temp < resultList.size()) {
				if(c == '1' || c == '0') {
					if(resultList.get(temp).equals("0")) {
						out.write((byte)('0'));
						temp++;
					}else {
						out.write((byte)('1'));
						temp++;
					}
				}else {
					out.write((byte)c);	
				}
			}
		}finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
