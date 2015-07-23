package file.manipulation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import reserved.word.ReservedWords;
import token.Token;

public class FileOperations {
	
	public void writeToFile(String fileName, ArrayList<Token> sourceCode,String locationToSaveFile){
		String location =  locationToSaveFile + fileName;
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(location, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Token x : sourceCode){
			writer.println(x.getToken());
		}
		writer.close();
	}
	
	public void readFileFromSource(String source){
		Path file = Paths.get(source);
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		} catch (IOException x) {
		    System.err.println(x);
		}
		
	}
	
	public ArrayList<String> createReservedWordList(String source){
		ArrayList<String> words = new ArrayList<>();
		
		Path file = Paths.get(source);
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	words.add(line);
		    }
		} catch (IOException x) {
		    System.err.println(x);
		}
		return words;		
	}
	
	public String readFileToString(String source){
	    String sourceFile = null ;
		Path file = Paths.get(source);
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	sourceFile =sourceFile + line;
		    }
		} catch (IOException x) {
		    System.err.println(x);
		}
		return sourceFile;
	}
}
