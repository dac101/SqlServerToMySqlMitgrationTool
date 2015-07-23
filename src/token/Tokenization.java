package token;

import java.util.ArrayList;

public class Tokenization {

	public String[] tokenToSmallerToken(String sourceFile) {
		int tokenCounter = 0;
		String[] tokens = sourceFile.split(" ");
		return tokens;
	}

        //remove comments
	public ArrayList<String> removeSourceCodeComment(String sourceFile) {
		char[] code = sourceFile.toCharArray();
		String comments = "";
		ArrayList<String> results = new ArrayList<>();
		for (int i = 0; i < code.length - 1; i++) {
			if (code[i] == '/') {
				int x = i;
				comments = comments + "/";
				code[i] = '*';
				while (x != -1) {
                                    
					if (code[x] == '/') {
						code[x] = '*';
						comments = comments + "/";
						break;
					} else {
						comments = comments + code[x];
						code[x] = '*';
						x++;
					}

				}
			}
		}
		results.add(new String(code).replace("*",""));
		results.add(comments);
		
		return results;

	}

	public ArrayList<Token> createSourceCodeTokens(String sourceFile) {
		ArrayList<Token> tokens = new ArrayList<>();
		int tokenCounter = 0;
		String token = "";

		for (char x : sourceFile.toCharArray()) {
			if (String.valueOf(x).equals(";")) {
				token = token + String.valueOf(x);
				Token y = new Token();
				y.Token = token;
				y.Id = tokenCounter++;
				statementType(token, y);
				tokens.add(y);
				token = "";
			} else {
				token = token + String.valueOf(x);
			}
		}
		return tokens;
	}

	private void statementType(String token, Token y) {
		if (token.toLowerCase().contains("create")) {
			y.Type = "create";
		} else if (token.toLowerCase().contains("insert")) {
			y.Type = "insert";
		} else if (token.toLowerCase().contains("where")) {
			y.Type = "where";
		} else if (token.toLowerCase().contains("use")) {
			y.Type = "use";
		} else {
			y.Type = "function";
		}
	}
}
