import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Author: Soruabh Jain, Parth Shah and Bhasker Gautam
 * Master students of Computer Science
 * National Institute of Technology, Karnataka
 */


public class ReadInputFile {
	FileReader reader;
	
	public ReadInputFile(String filename){
		try {
			reader = new FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ArrayList<Integer>> readLine(){
		ArrayList<ArrayList<Integer>> wordList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> alist = new ArrayList<Integer>();
		int c;
		
		try {
			while( (c=reader.read()) != -1 ){
				if(c=='\t'){
					wordList.add(alist);
					alist = new ArrayList<Integer>();
				}
				else if(c=='\n'){
					alist.remove(alist.size()-1);
					wordList.add(alist);
					break;
				}
				else
					alist.add(new Integer(c));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wordList;
	}
}