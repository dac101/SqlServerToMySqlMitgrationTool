package generate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import token.Token;
import token.Tokenization;

public class SyntaxRule {

    public String databaseName;

    public String changeForiegnKey(Token token) {
        ArrayList<String> newToken = new ArrayList<>();
        String foriegnkeySyntax = "";
        String firstOrder = "";

        boolean checkMysqlForiegnFunction = token.getToken().toLowerCase().contains("foreign key(") || token.getToken().toLowerCase().contains("foreign key (");
        if (token.getToken().toLowerCase().contains("foreign") && !checkMysqlForiegnFunction) {
            String[] tokensplit = token.getToken().split(",");
            for (int i = 0; i < tokensplit.length; i++) {
                if (tokensplit[i].toLowerCase().contains("foreign")) {
                    System.out.println(tokensplit[i]);
                    String[] tokenSplitSplit = tokensplit[i].split(" ");
                    firstOrder = (tokenSplitSplit[0] + " " + tokenSplitSplit[1] + " " + tokenSplitSplit[2]).replace(",",
                            "") + ",";
                    foriegnkeySyntax = " FOREIGN KEY  " + " " + "(" + tokenSplitSplit[1] + ")" + " "
                            + tokenSplitSplit[tokenSplitSplit.length - 2] + "  "
                            + tokenSplitSplit[tokenSplitSplit.length - 1];
                    System.out.println("testing");
                }
            }

            for (int i = 0; i < tokensplit.length; i++) {
                if (tokensplit[i].toLowerCase().contains("foreign")) {
                    newToken.add(firstOrder.replace(",", "") + ",");

                    if (tokensplit.length == i) {
                        newToken.add(foriegnkeySyntax + ",");
                    } else {
                        newToken.add(foriegnkeySyntax);
                    }
                } else {
                    newToken.add(tokensplit[i].replace(",", "") + ',');
                }
            }
            String list = "";
            for (String x : newToken) {
                list += x;
            }
            list = list.toLowerCase().replace("primary key(", " ,primary key(");
            list = list.toLowerCase().replace(");,", ");");
            System.out.println(list);
            return list;

        }
        return token.getToken();
    }

    public ArrayList<Token> replaceSQLServerFunctions(ArrayList<Token> sourceCodeTokens) {
        Tokenization tokenization = new Tokenization();
        for (int i = 0; sourceCodeTokens.size() > i; i++) {

            replaceSyntax(sourceCodeTokens, i, "len(", "Character_length(");
            replaceSyntax(sourceCodeTokens, i, "getDate(", "now(");
            replaceSyntax(sourceCodeTokens, i, "newid", "UUID(");
            replaceSyntax(sourceCodeTokens, i, "identity(1,1)", "auto_increment");
            replaceSyntax(sourceCodeTokens, i, "identity", "auto_increment");
        }
        return sourceCodeTokens;
    }

    public ArrayList<Token> replaceSQLServerDataType(ArrayList<Token> sourceCodeTokens) {
        Tokenization tokenization = new Tokenization();
        for (int i = 0; sourceCodeTokens.size() > i; i++) {
            replaceSyntax(sourceCodeTokens, i, "bit", "TINYINT(1)");
            replaceSyntax(sourceCodeTokens, i, "NUMERIC", "DECIMAL");
            replaceSyntax(sourceCodeTokens, i, "DECIMAL", "DECIMAL");
            replaceSyntax(sourceCodeTokens, i, "MONEY", "DECIMAL(10,5)");
            replaceSyntax(sourceCodeTokens, i, "money", "DECIMAL(10,5)");
            replaceSyntax(sourceCodeTokens, i, "SMALLMONEY", "DECIMAL(10,5)");
            replaceSyntax(sourceCodeTokens, i, "CHAR", "CHAR");
            replaceSyntax(sourceCodeTokens, i, "NCHAR", "CHAR");
            replaceSyntax(sourceCodeTokens, i, "CHAR", "CHAR");
            replaceSyntax(sourceCodeTokens, i, "VARCHAR", "CHAR");
            replaceSyntax(sourceCodeTokens, i, "NVARCHAR", "VARCHAR");
            replaceSyntax(sourceCodeTokens, i, "DATETIME2", "DATETIME");
            replaceSyntax(sourceCodeTokens, i, "SMALLDATETIME", "DATETIME");
            replaceSyntax(sourceCodeTokens, i, "DATETIMEOFFSET", "DATETIME");
            replaceSyntax(sourceCodeTokens, i, "TIME", "TIME");
            replaceSyntax(sourceCodeTokens, i, "TIMESTAMP", "TIMESTAMP");
            replaceSyntax(sourceCodeTokens, i, "ROWVERSION", "TIMESTAMP");
            replaceSyntax(sourceCodeTokens, i, "BINARY", "BINARY");
            replaceSyntax(sourceCodeTokens, i, "VARBINARY", "VARBINARY");
            replaceSyntax(sourceCodeTokens, i, "NTEXT", "LONGTEXT");
            replaceSyntax(sourceCodeTokens, i, "IMAGE", "LONGBLOB");
            replaceSyntax(sourceCodeTokens, i, "UNIQUEIDENTIFIER", "VARCHAR(64)");
            replaceSyntax(sourceCodeTokens, i, "SYSNAME", "VARCHAR(160)");
            replaceSyntax(sourceCodeTokens, i, "XML", "TEXT");
            replaceSyntax(sourceCodeTokens, i, "READ", "'READ'");
            replaceSyntax(sourceCodeTokens, i, "XML", "TEXT");
            replaceSyntax(sourceCodeTokens, i, "read", "readed");
            replaceSyntax(sourceCodeTokens, i, "(max)", "(256)");
        }
        return sourceCodeTokens;

    }

    public String replaceSQLServerSymbols(String sourceFile) {

        sourceFile = removeOneWord(sourceFile);
        sourceFile = removePhrases(sourceFile);
        sourceFile = removeLongPhrases(sourceFile);

        sourceFile = removeWordsBetweenConstraintAndPrimary(sourceFile);

        sourceFile = removeIdentityAndInsertWords(sourceFile);
        sourceFile = changeSemicolon(sourceFile);

        sourceFile = addSemicolonToInsert(sourceFile);

        sourceFile = removePrimaryKeyWithNumber(sourceFile);

        return sourceFile;

    }

    public String removePrimaryKeyWithNumber(String sourceFile) {
        //PK__Crop__3213E83F09E8DC34
        if (sourceFile.contains("PK_")) {
            sourceFile = replaceMatching2(sourceFile, "PK_", "PRIMARY");
            sourceFile = sourceFile.replace("PK_", "");
        }
        return sourceFile;
    }

    public String addSemicolonToInsert(String sourceFile) {
        sourceFile = sourceFile.replace(")insert", ");insert");
        sourceFile = sourceFile.replace(")INSERT", ");insert");
        sourceFile = sourceFile.replace("SET ANSI_ S ON;SET QUOTED_IDENTIFIER ON;SET ANSI_PADDING ON;", "");
        sourceFile = sourceFile.replace("SET ANSI_ S ON;SET QUOTED_IDENTIFIER ON;", "");
        sourceFile = sourceFile.replace("ON;SET QUOTED_IDENTIFIER ON;SET ANSI_PADDING ON;", "");
        return sourceFile;
    }

    public String removeWordsBetweenConstraintAndPrimary(String sourceFile) {
        if (sourceFile.toLowerCase().contains("constraint") && sourceFile.contains("primary")) {
            sourceFile = replaceMatching2(sourceFile, "CONSTRAINT", " PRIMARY");
        }
        return sourceFile;
    }

    public String removeIdentityAndInsertWords(String sourceFile) {
        sourceFile = sourceFile.replace("CONSTRAINT", "");
        sourceFile = sourceFile.replace("TEXTIMAGE_ON  PRIMARY ;", "");
        sourceFile = sourceFile.replace(" USE  master ;", " ");
        sourceFile = sourceFile.replace("WITH (     ) ", " ");
        sourceFile = replaceMatching2(sourceFile, "SET IDENTITY_INSERT", "ON");
        sourceFile = sourceFile.replace("SET IDENTITY_INSERTON", "");
        sourceFile = replaceMatching2(sourceFile, "SET IDENTITY_INSERT", "master");
        sourceFile = sourceFile.replace("(	", "(");
        sourceFile = sourceFile.replace("SET IDENTITY_INSERTmaster", " ");
        return sourceFile;
    }

    public String removeLongPhrases(String sourceFile) {
        sourceFile = sourceFile.replace("SIZE = 3136KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB", "");
        sourceFile = sourceFile.replace(", SIZE = 784KB , MAXSIZE = 2048GB , FILEGROWTH = 10%", "");
        sourceFile = sourceFile.replace("USE master;", "");
        sourceFile = sourceFile.replace("ALTER DATABASE [crops] SET  READ_WRITE \n" + "GO", "");
        sourceFile = sourceFile.replace("IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))\n"
                + "begin\n"
                + "EXEC [crops].[dbo].[sp_fulltext_database] @action = 'enable'\n"
                + "end\n"
                + "GO", "");
        return sourceFile;
    }

    public String removePhrases(String sourceFile) {
        sourceFile = sourceFile.replace("SET ANSI_NULLS ON;", " ");
        sourceFile = sourceFile.replace("SET ANSI_PADDING OFF;", " ");
        sourceFile = sourceFile.replace("CONTAINMENT = NONE\n" + " ON  PRIMARY ", " ");
        sourceFile = sourceFile.replace("SET ANSI_PADDING OFF", " ");
        sourceFile = sourceFile.replace(" ON  PRIMARY ", " ");
        sourceFile = sourceFile.replace(" LOG ON ", " ");
        sourceFile = sourceFile.replace("CONTAINMENT = NONE\n" + " ON  PRIMARY ", "");
        sourceFile = sourceFile.replace("PAD_INDEX = OFF,", " ");
        sourceFile = sourceFile.replace(" STATISTICS_NORECOMPUTE = OFF,", " ");
        sourceFile = sourceFile.replace(" IGNORE_DUP_KEY = OFF,", " ");
        sourceFile = sourceFile.replace(" ALLOW_ROW_LOCKS = ON,", " ");
        sourceFile = sourceFile.replace(" ALLOW_PAGE_LOCKS = ON", " ");
        sourceFile = sourceFile.replace(" ON PRIMARY ", " ");
        sourceFile = sourceFile.replace(" ON PRIMARY TEXTIMAGE_ON  PRIMARY ", " ");
        sourceFile = sourceFile.replace("CLUSTERED", " ");
        sourceFile = sourceFile.replace("ASC", " ");
        return sourceFile;
    }

    public String removeOneWord(String sourceFile) {
        sourceFile = sourceFile.replace("not", " ");
        sourceFile = sourceFile.replace("null", " ");
        sourceFile = sourceFile.replace("Go", ";");
        sourceFile = sourceFile.replace("GO", ";");
        sourceFile = sourceFile.replace("NOT", " ");
        sourceFile = sourceFile.replace("NULL", " ");
        sourceFile = sourceFile.replace("[", " ");
        sourceFile = sourceFile.replace("]", " ");
        sourceFile = sourceFile.replace("dbo.", " ");
        sourceFile = sourceFile.replace("dbo .", " ");
        return sourceFile;
    }

    public String changeSemicolon(String sourceFile) {
        if (!sourceFile.contains(");") || sourceFile.contains(")  ; ;")) {
            sourceFile = sourceFile.replace("PRIMARY KEY   ( id   ) )", "PRIMARY KEY   ( id   ) );");
            sourceFile = sourceFile.replace("    ", "  ");
            sourceFile = sourceFile.replace("; ;", ";");
            sourceFile = sourceFile.replace(";  ;", ";");
            sourceFile = sourceFile.replace(")  ; ;", ");");
        }
        return sourceFile;
    }

    public static String replaceMatching2(String input, String lowerBound, String upperBound) {
        String result = input.replaceAll("(.*?" + lowerBound + ")" + "(.*?)" + "(" + upperBound + ".*)", "$1$3");
        return result;
    }

    private void replaceSyntax(ArrayList<Token> sourceCodeTokens, int i, String wordToFind, String wordToReplace) {
        if (sourceCodeTokens.get(i).getToken().toLowerCase().contains(wordToFind.toLowerCase())) {
            String changeToken = sourceCodeTokens.get(i).getToken().toLowerCase().replace(wordToFind, wordToReplace);
            sourceCodeTokens.get(i).setToken(changeToken);
        }
    }

    public String removeAlterDatabaseStatements(String sourceFile, String databaseName) {

        String setGoStatement = "SET";
        String alterStatement = "ALTER DATABASE " + databaseName;
        String itemToRemove = "";
        sourceFile = sourceFile.replace("  ", " ");

        for (String item : sqlServerWordsToRemove()) {
            itemToRemove = alterStatement + " " + setGoStatement + " " + item + ";";
            if (sourceFile.toLowerCase().contains(itemToRemove.toLowerCase())) {
                sourceFile = sourceFile.replace(itemToRemove, " ");
            } else {
                sourceFile = sourceFile.replace(alterStatement + " " + setGoStatement + " " + item, " ");
            }
        }

        //String test = " CREATE " + sourceFile.split("CREATE")[2];
        sourceFile = replaceMatching2(sourceFile, "GO/*", "CREATE TABLE");
        sourceFile = sourceFile.replace("USE [master]GO/", "");

        return sourceFile;
    }

    public String CreateDatabaseStatement(String sourceFile, String dataBaseName) {
        String createDatabaseStatement = "Create Database " + dataBaseName.replace("[", "").replace("]", "") + ";\n";
        String useStatement = "use " + dataBaseName.replace("[", "").replace("]", "") + ";\n";
        return createDatabaseStatement + useStatement + sourceFile;
    }

    public ArrayList<String> sqlServerWordsToRemove() {
        ArrayList<String> sqlServerWordsToRemove = new ArrayList<>();

        sqlServerWordsToRemove.add("ANSI_NULL_DEFAULT OFF");
        sqlServerWordsToRemove.add("ANSI_NULLS OFF ");
        sqlServerWordsToRemove.add("ANSI_PADDING OFF ");
        sqlServerWordsToRemove.add("ANSI_WARNINGS OFF ");
        sqlServerWordsToRemove.add("ARITHABORT OFF ");
        sqlServerWordsToRemove.add("AUTO_CLOSE ON ");
        sqlServerWordsToRemove.add("AUTO_UPDATE_STATISTICS ON ");
        sqlServerWordsToRemove.add("CURSOR_CLOSE_ON_COMMIT OFF ");
        sqlServerWordsToRemove.add("CURSOR_DEFAULT GLOBAL ");
        sqlServerWordsToRemove.add("CONCAT_NULL_YIELDS_NULL OFF ");
        sqlServerWordsToRemove.add("NUMERIC_ROUNDABORT OFF ");
        sqlServerWordsToRemove.add("QUOTED_IDENTIFIER OFF ");
        sqlServerWordsToRemove.add("RECURSIVE_TRIGGERS OFF ");
        sqlServerWordsToRemove.add("ENABLE_BROKER ");
        sqlServerWordsToRemove.add("AUTO_UPDATE_STATISTICS_ASYNC OFF ");
        sqlServerWordsToRemove.add("DATE_CORRELATION_OPTIMIZATION OFF ");
        sqlServerWordsToRemove.add("TRUSTWORTHY OFF ");
        sqlServerWordsToRemove.add("ALLOW_SNAPSHOT_ISOLATION OFF ");
        sqlServerWordsToRemove.add("PARAMETERIZATION SIMPLE ");
        sqlServerWordsToRemove.add("READ_COMMITTED_SNAPSHOT OFF ");
        sqlServerWordsToRemove.add("HONOR_BROKER_PRIORITY OFF ");
        sqlServerWordsToRemove.add("RECOVERY SIMPLE ");
        sqlServerWordsToRemove.add("MULTI_USER ");
        sqlServerWordsToRemove.add("PAGE_VERIFY CHECKSUM ");
        sqlServerWordsToRemove.add("DB_CHAINING OFF ");
        sqlServerWordsToRemove.add("FILESTREAM( NON_TRANSACTED_ACCESS = OFF )");
        sqlServerWordsToRemove.add("TARGET_RECOVERY_TIME = 0 SECONDS");
        sqlServerWordsToRemove.add("COMPATIBILITY_LEVEL = 110");
        sqlServerWordsToRemove.add("AUTO_SHRINK OFF ");
        sqlServerWordsToRemove.add("AUTO_UPDATE_STATISTICS ON ");
        sqlServerWordsToRemove.add("AUTO_CREATE_STATISTICS ON");
        sqlServerWordsToRemove.add("READ_WRITE ");

        return sqlServerWordsToRemove;

    }

    public String getDatabaseNameFromMySqlServerFile(String source) {

        String sourceFile = null;
        String databaseName = "";
        Path file = Paths.get(source);
        try (InputStream in = Files.newInputStream(file);
                BufferedReader reader
                = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains("create database")) {
                    String[] Arrline = line.split(" ");
                    for (String s : Arrline) {
                        if (!s.toLowerCase().equals("create") && !s.toLowerCase().equals("database")) {
                            databaseName = s;
                            return s;
                        }
                    }
                }
                sourceFile = sourceFile + line;
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return null;

    }

}
