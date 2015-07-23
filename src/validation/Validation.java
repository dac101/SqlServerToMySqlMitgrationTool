package validation;

import java.util.ArrayList;

import token.Token;
import token.Tokenization;

public class Validation {

    public static boolean validationTypeOfSQL(String sourceFile, Tokenization tokenization, ArrayList<String> uniqueKeywordList) {
        sourceFile = sourceFile.replace(" IDENTITY(1,1)", "IDENTITY");
        ArrayList<Token> createSourceCodeTokens = tokenization.createSourceCodeTokens(sourceFile);

        for (Token x : createSourceCodeTokens) {
            String smallerTokens[] = tokenization.tokenToSmallerToken(x.getToken());
            for (String smallerTOkenValue : smallerTokens) {
                Boolean result = smallerTOkenValue.contains(",");
                if (result == true) {
                    String[] tokenSplit = smallerTOkenValue.split(",");
                    for (String ts : tokenSplit) {
                        for (String t : uniqueKeywordList) {
                            if (t.toLowerCase().equals(ts.toLowerCase().replace("(", "").replace(",", "").replace(")", "").replace("\t", ""))) {
                                // System.out.println("This is a mysql file Mysql");
                                return true;
                            }
                        }
                    }
                } else {
                    for (String uniqueKeyword : uniqueKeywordList) {
                        if (uniqueKeyword.toLowerCase().equals(smallerTOkenValue.toLowerCase().replace("(", "").replace(",", "").replace(")", ""))) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public static boolean tokenValidation(ArrayList<Token> createSourceCodeTokens) {
        for (Token x : createSourceCodeTokens) {
            boolean checkSemicolon = x.getToken().contains(";") && x.getToken().contains("(") && x.getToken().contains(")");
            if (x.getToken().contains(";") || checkSemicolon) {
                //System.out.println("Token is valid");
            } else {
                return false;
            }
        }
        return true;
    }

    //TODO Check to see the exact Same number opening () semicolons
    public boolean checkIfEqualAmountOfbraces(String sourceCode) {
        ArrayList<SymbolCount> symbolCounts = new ArrayList<>();
        symbolCounts.add(new SymbolCount(0, '('));
        symbolCounts.add(new SymbolCount(0, ')'));

        for (char item : sourceCode.toCharArray()) {
            if (item == '(') {
                symbolCounts.get(0).counter++;
            } else if (item == ')') {
                symbolCounts.get(1).counter++;
            }
        }

        return symbolCounts.get(0).counter == symbolCounts.get(1).counter && symbolCounts.get(0).counter > 0;
    }

    //TODO Check to see the same amount of insert and values statement
    public boolean checkIfEqualInsertValues(String sourceCode) {

        int insertCounter = 0;
        int valueCounter = 0;
        String[] age = sourceCode.split(" ");

        for (String item : sourceCode.split(" ")) {
            if (item.toLowerCase().contains("insert")) {
                insertCounter++;
            } else if (item.toLowerCase().contains("values")) {
                valueCounter++;
            }
        }

        return valueCounter == insertCounter;
    }

    //TODO Check to enure the right amount of values
    public boolean checkIfInsertToken(ArrayList<Token> sourceCodeTokens) {
        String[] strToVadilate;
        for (Token x : sourceCodeTokens) {
            boolean name = x.getToken().toLowerCase().contains("insert") && x.getToken().toLowerCase().contains("values") && x.getToken().contains(";");
            if (name) {
                strToVadilate = x.getToken().toLowerCase().split("values");

                if (!(strToVadilate[0].split(",").length == strToVadilate[1].split(",").length)) {
                    System.out.println("yes");
                    return false;
                }

            }
        }
        return true;
    }

    //check vadilation
    public boolean checkIfFileExtensionIsSql(String filePath, String fileType) {
        if (filePath.toLowerCase().contains(fileType)) {
            return true;
        } else {
            return false;
        }
    }

    public class SymbolCount {

        public SymbolCount(int counter, char symbol) {
            this.counter = counter;
            this.symbol = symbol;
        }

        public int counter;
        public char symbol;
    }

    public static String replaceMatching2(String input, String lowerBound, String upperBound) {
        String result = input.replaceAll("(.*?" + lowerBound + ")" + "(.*?)" + "(" + upperBound + ".*)", "$1$3");
        return result;
    }
}
