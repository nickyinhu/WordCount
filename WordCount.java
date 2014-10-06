package wordCount;

import java.util.*;
import java.io.*;
/** The WordCount class implements word counting in text file using HashMap data structure
*/

//Implement Comparator interface for result sorting
class ValueComparator implements Comparator<String> {
	HashMap<String, Integer> base;
	ValueComparator (HashMap<String, Integer> base) {
		this.base = base;
	}
	@Override
	public int compare(String a, String b){
		if(base.get(a) >= base.get(b)){
			return -1;
		} else {
			return 1;
		}
	}	
}

public class WordCount {	
	public static void main(String[] args) {
		WordCount wc = new WordCount();
		wc.go();
	}

	/** Setup input and output
	*/
	public void go(){
		String fileName = "big.txt";
		HashMap<String, Integer> map = countWord(fileName);
		TreeMap<String, Integer> sortedMap = sortByValue(map);
		String output = "result.txt";
		PrintStream fileOut = null;
		int count = 0;
		try {
			fileOut = new PrintStream(output);
			System.out.println("---Word Count---");
			fileOut.println("---Word Count---");
			System.out.println("Total words: " + sortedMap.keySet().size());
			System.out.println("Top 20 length>5 words");
			fileOut.println("Total words " + sortedMap.keySet().size());
			for(String key : sortedMap.keySet()){
				if(count < 20 && key.length()>5){
					System.out.println("The word " + key + " appeared " + map.get(key) + " times");
					count++;
				}
				fileOut.println("The word " + key + " has appeared " + map.get(key) + " times");
			}
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			if(fileOut != null){
				fileOut.flush();
				fileOut.close();
			}
		}		
	} //end of setup
	
	/** Sort result by alphabetical order
	*/
	public static TreeMap<String, Integer> sortByValue 
		(HashMap<String, Integer> map) {
		ValueComparator vc =  new ValueComparator(map);
		TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}//end of sort
	
	/** Word counting and recording
	*/
	public static HashMap<String, Integer> countWord (String fileName) {		
		BufferedReader br = null;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			FileReader fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] set = line.trim().toLowerCase().split("\\W+");
				for(String s : set) {
					if(s.matches("[a-z]+")){
						if(map.containsKey(s)){
							map.put(s, map.get(s)+1);
						} else {
							map.put(s,1);
						}
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return map;
	} // end of countWord
}