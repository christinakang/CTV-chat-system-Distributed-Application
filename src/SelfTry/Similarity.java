package SelfTry;

import java.util.ArrayList;
import java.util.Scanner;

public class Similarity {
	public static void main(String[] args){
	    boolean check = checkSimilarity("hi","hi jq");
	    	System.out.println(check);
	}
	
	public static boolean checkSimilarity(String line1, String line2){
		boolean check = false;
		double count = 0;
		line1 = line1.toLowerCase();
		line2 = line2.toLowerCase();
		ArrayList<String> arr1 = new ArrayList<String>();
		ArrayList<String> arr2 = new ArrayList<String>();
		Scanner linecheck1 = new Scanner(line1);
        Scanner linecheck2 = new Scanner (line2);
		while(linecheck1.hasNext()){
			arr1.add(linecheck1.next());
		}
		while(linecheck2.hasNext()){
			arr2.add(linecheck2.next());
		}
		
		linecheck1.close();
		linecheck2.close();
		
		for(String s1: arr1){
			for(String s2: arr2){
				if(s1.equals(s2)){
					count++;
				}
			}
		}
		System.out.println("count: "+count);
		
		double size1 = arr1.size();
		double size2 = arr2.size();
		double size;
		
		if(size1>size2)
			size = size1;
		else 
			size = size2;
		System.out.println("size "+size);
		double similarity;
		similarity = (double)count/size;
		if(similarity >= 0.5)
			{
			    check = true;
			}
        System.out.println(similarity);
		return check;
	}
}
