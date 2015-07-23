package reserved.word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import file.manipulation.FileOperations;

public class ReservedWords {
	
	ArrayList<String> MySqlKeyword;
	ArrayList<String> SQlServerKeyWord;
	ArrayList<String> CommonWords;
	
	
	public ReservedWords() {
		super();
		MySqlKeyword = new ArrayList<>();
		SQlServerKeyWord = new ArrayList<>();
		CommonWords = new ArrayList<String>();
	}
	
	
	public ArrayList<String> getCommonWords() {
		return CommonWords;
	}

	public void setCommonWords(ArrayList<String> commonWords) {
		CommonWords = commonWords;
	}


	public ArrayList<String> getMySqlKeyword() {
		return MySqlKeyword;
	}
	public ArrayList<String> getSQlServerKeyWord() {
		return SQlServerKeyWord;
	}
	
	public ArrayList<String> createCommonWords( ArrayList<String> listA, ArrayList<String> listB ){
		ArrayList<String> results = new ArrayList<String>();
	
		for(String valueA : listA ){
			for(String valueB : listB )
			{
				if(valueB.toLowerCase().equals(valueA.toLowerCase())){
					results.add(valueA);
					break;
				}
			}	
		}
		 java.util.Collections.sort(results);
		return  results;
	}
 
	// create unique word list 
	public ArrayList<String> subtractValuesFromList(ArrayList<String> list, ArrayList<String> values){
	  ArrayList<String> x = new ArrayList<>();
	  int found = 0;
		for( int i = 0 ; i < list.size(); i++){
			found = 0;
			for(String y : values){
				if(list.get(i).toLowerCase().equals(y.toLowerCase())){
				  found = -1;
				  break;
				}
			}
			if(found == 0 && !list.get(i).equals("") )
			{
				x.add(list.get(i));
			}			
		}
		return x;	
	}

	public  void initialization(FileOperations file, ReservedWords reservedWords, String pathMysqlKeyowrd,
			String pathSqlServerKeyowrd) {
		for (String x : file.createReservedWordList(pathMysqlKeyowrd)) {
			reservedWords.getMySqlKeyword().add(x.replace(" ", ""));
		}

		for (String x : file.createReservedWordList(pathSqlServerKeyowrd)) {
			reservedWords.getSQlServerKeyWord().add(x.replace(" ", ""));
		}
	}

	public  void keyWordsInformation(ReservedWords reservedWords, ArrayList<String> commonWords) {
		System.out.println("MYSQL Keywords  :" + reservedWords.getMySqlKeyword().size());
		ArrayList<String> mysqlUniqueWords = reservedWords.subtractValuesFromList(reservedWords.getMySqlKeyword(),commonWords);
		System.out.println("MYSQL unique Keywords :" + mysqlUniqueWords.size());

		System.out.println("SQL Server  Keywords :" + reservedWords.getSQlServerKeyWord().size());
		ArrayList<String> sqlServerUniqueWords = reservedWords.subtractValuesFromList(reservedWords.getSQlServerKeyWord(), commonWords);
		System.out.println("SQL Server unique Keywords:" + sqlServerUniqueWords.size());
		
		System.out.println("Common Keywords :" + reservedWords.getCommonWords().size());
	}
}
