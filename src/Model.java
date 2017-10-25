import java.util.ArrayList;

/*
 * Author: Soruabh Jain, Parth Shah and Bhasker Gautam
 * Master students of Computer Science
 * National Institute of Technology, Karnataka
 */

class Pair{
	int first;
	int second;
	Pair(){
		first = 0;
		second = 0;
	}
}


public class Model {
	ReadInputFile readInputFile;
	static int rows = 37;
	static int matras = 16;
	double[][][] sparklers = new double[rows][rows][matras];
	static double THRESHOLD = 4.0;
	
	Model(){
		double[] value = {1, 2, 3, 4, 5,  3, 4, 5, 6, 7,  5, 6, 7, 8, 9,   7, 8, 9, 10, 11,11,   9, 10, 11, 12, 13,  8,10,10,12,12,12,14,   9,11,13,   6};
		double[] matras_value = {1, 0.9, 0.8, 0.78, 0.7, 0.68, 0.5, 0.49, 0.75, 0.75, 0.74, 0.74, 0.6, 0.59, 0.58, 0.55};
		double[] xcoord = {0,0,0,0,0,  1,1,1,1,1, 2,2,2,2,2, 3,3,3,3,3,3, 4,4,4,4,4, 1,2,2,3,3,3,4,  1,2,3, 0};
		double[] ycoord = {0,1,2,3,4,  0,1,2,3,4, 0,1,2,3,4, 0,1,2,3,4,4, 0,1,2,3,4, 5,5,5,5,5,5,5,  6,6,6, 7};
		
		for(int i=0;i<rows;i++){xcoord[i] = xcoord[i]*2;ycoord[i]=ycoord[i]*2;};
		
		for(int i=0;i<rows;i++){
			sparklers[i][0][0] = value[i];
			sparklers[0][i][0] = value[i];
		}
		
		for(int i=1;i<rows;i++){
			for(int j=1; j<rows; j++){
				sparklers[i][j][0] = Math.sqrt( Math.pow(xcoord[i]-xcoord[j], 2) + Math.pow(ycoord[i]-ycoord[j],2) ); 
			}
		}
		

		for(int i=0;i<rows; i++){
			for(int j=0;j<rows; j++){
				for(int k=1;k<matras;k++)
					sparklers[i][j][k] = sparklers[i][j][0]*matras_value[k];
			}
		}
		
//		Turn on to print a column of a dimension in matrix created.
//		for(int i=0;i<rows;i++)
//			System.out.println(sparklers[i][11][1]);
//		System.out.println();
	}
	
	
	public static ArrayList<Pair>  givePos(ArrayList<Integer> alist){
		ArrayList<Pair>charList = new ArrayList<Pair>();//store x ,z coord
		
		for(int i=0;i<alist.size();){
			Pair xz_coord = new Pair();
			int conPos = 0;
			int matPos = 0;
			
			conPos = WordDetector.get_relat_ascii(alist.get(i))%Model.rows;
			//ignore previous matras
			while(i < alist.size()){
				if(WordDetector.char_type(alist.get(i)) != 3) break;
				i++;
			}//switch to first character
			xz_coord.first = WordDetector.get_relat_ascii(alist.get(i));
			i++;
			
			//for matras
			if(i < alist.size() && WordDetector.char_type(alist.get(i)) == 3){
				matPos  = WordDetector.get_relat_ascii(alist.get(i))%Model.matras;
				i++;
			}
			
			//ignore more than one matras
			while(i < alist.size()){
				if(WordDetector.char_type(alist.get(i)) != 3) break;
				i++;
			}//switch to next character
			
			xz_coord.second = matPos;
			charList.add(xz_coord);
		}
		return charList;	
	}
	
	public boolean test(ArrayList<Integer> alist, ArrayList<Integer>blist){
		boolean flag=true;
		ArrayList<Pair> firstList = givePos(alist);
		ArrayList<Pair> secondList = givePos(blist);
		double sum = 0.0;
		
		for(int i=0;i<firstList.size()&&i<secondList.size();i++){
			int x,y,z;
			x = firstList.get(i).first;
			y = secondList.get(i).first;
			z = firstList.get(i).second;
			sum = sum + Math.abs(sparklers[x][y][z] - sparklers[secondList.get(i).first][x][secondList.get(i).second]);			
		}
		//System.out.println("sum of below string is "+sum);//*2
		if(sum>THRESHOLD)return false;
		
		return flag;
		
	}	
}