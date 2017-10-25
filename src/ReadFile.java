import java.io.IOException;
import java.util.ArrayList;

/*
 * Author: Soruabh Jain, Parth Shah and Bhasker Gautam
 * Master students of Computer Science
 * National Institute of Technology, Karnataka
 */

public class ReadFile {
	/*
	 * Read the input file line by line.
	 * convert English word to Hindi if any.
	 * Calculate the distance between using Model.
	 * compare the result distance with THRESHOLD and decide whether given words are equal or not.
	 */
	public static void start(double threshold) throws IOException{
		String inputFile = "aa.txt";
		String outputFile = "output.txt";
		ReadInputFile rif = new ReadInputFile(inputFile);
		ArrayList<ArrayList<Integer>> wordList;
		ArrayList<String> resultList = new ArrayList<String>();
		wordList = rif.readLine();
		
		while(wordList.size()>1){
			if(WordDetector.isHindi(wordList.get(1))==false){
				wordList.set(1, WordDetector.toHindi(wordList.get(1)) );
			}
			Model m = new Model();
			boolean b = m.test(wordList.get(0), wordList.get(1));
			if(b) {
				resultList.add("1");
			}else {
				resultList.add("0");
			}
			/*
			 * Uncomment below line if want to use Levenshtein distance model
			 */
//			SparkersModel lh = new SparkersModel();
//			double d = lh.distance(WordDetector.array_to_string(wordList.get(0)).toString(), WordDetector.array_to_string(wordList.get(1)).toString());
			wordList = rif.readLine();
		}
		
        System.out.println(resultList.toString());
        
		OutputFile.GenerateOutputFile(inputFile, outputFile, resultList);
		rif.stop();
	}
	/*
	 * Stating point of project
	 */
	public static void main(String[] args) throws IOException{	
		start(Model.THRESHOLD);
	}
}
