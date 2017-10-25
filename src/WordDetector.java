import java.util.ArrayList;

/*
 * Author: Soruabh Jain, Parth Shah and Bhasker Gautam
 * Master students of Computer Science
 * National Institute of Technology, Karnataka
 */

import Transliteration.TransliterationEngine;

/*
 * Class contains functions related to manipulate or detect given words in input file. 
 */
public class WordDetector {
	
	/*
	 * Detect given word is in Hindi or not.
	 */
	public static boolean isHindi(ArrayList<Integer> alist){
		for(int i=0; i<alist.size(); i++){
			if(alist.get(i)>2309 && alist.get(i)<2392)
				return true;
		}
		return false;
	}
	
	public static StringBuilder array_to_string(ArrayList<Integer> alist){
		StringBuilder str = new StringBuilder();
		for(int i=0;i<alist.size();i++){
			str = str.append(Character.toChars(alist.get(i)));
		}
		return str;
	}
	
	/*
	 * Convert English word into hindi word.
	 */
	public static ArrayList<Integer> toHindi(ArrayList<Integer> wordList){
		TransliterationEngine te = new TransliterationEngine();
		String str = te.transliterate(array_to_string(wordList).toString());
		ArrayList<Integer> blist = new ArrayList<Integer>();
		
		for(int i=0;i<str.length();i++){
			blist.add((int) str.charAt(i));
		}
		return blist;
	}
	
	/*
	 * Determine the type given hindi word
	 * return 1 : Vowel
	 * return 2 : Consonants 
	 * return 3 : Matras
	 */
	public static int char_type(int asciivalue){
		if(asciivalue>=2309 && asciivalue<2325)return 1;
		else if(asciivalue>=2325 && asciivalue<=2361)return 2;
		return 3;
	}
	
	/*
	 * Take the hindi character ascii value
	 * return relative Ascii that relate to 3D table
	 * for example आ  return index value in table
	 */
	public static int get_relat_ascii(int asciivalue){
		if(char_type(asciivalue)==1){
			asciivalue = asciivalue-2309;
			if(asciivalue<0 || asciivalue>=Model.rows)
			return 0;
			return asciivalue;
		}
		else if(char_type(asciivalue)==2){
			asciivalue = asciivalue-2325;
			if(asciivalue<0 || asciivalue>=Model.rows)
				return 0;
			return asciivalue;
		}
		if(asciivalue<2308)return (asciivalue-2304)%Model.matras;
		asciivalue = asciivalue-2362;
		if(asciivalue<0 || asciivalue>=Model.matras)
			return 0;
		return asciivalue;
	}
	
	
	/*
	 * Maps the matras to correspoding vowels
	 * for example भा  to आ
	 */
	public static int convert_matras_to_vowels(int asciivalue){
		int ans=asciivalue;
		switch(asciivalue){
		case 2361:ans = 2350;break;
		case 2363: ans = 2310;break;
		case 2364:
		case 2365: ans = 2309;break;
		case 2366: ans = 2310;break;
		case 2367: ans = 2311;break;
		case 2368: ans = 2312;break;
		case 2369: ans = 2313;break;
		case 2370: ans = 2314;break;
		case 2371: ans = 2314;break;
		case 2372: ans = 2315;break;
		case 2373: ans = 2309; break;
		case 2374: ans = 2317; break;
		case 2375: ans = 2317; break;
		case 2376: ans = 2318; break;
		case 2377: ans = 2321; break;
		case 2378: ans = 2322; break;
		case 2379: ans = 2323; break;
		case 2380: ans = 2324; break;
		case 2389: ans = 2321; break;
		}
		return ans;
	}
}
