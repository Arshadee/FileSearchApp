package org.filesearch;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringJoiner;

public class AutoUIConfigUtilitiesMod {

    public static boolean predicateAND(File file, String contents, KeyWordItem[] keywordItems, boolean noCase) {

        if (keywordItems.length == 0) return true;

        boolean predicate = true;

        for (KeyWordItem keyWordItem : keywordItems) {

            String keyWord = keyWordItem.getValue();

            if(noCase){
                keyWord = keyWord.toLowerCase();
                contents = contents.toLowerCase();
            }

            predicate = keyWordItem.isInclude() ? predicate &&
                    contents.contains(keyWord)
                    : predicate && !contents.contains(keyWord);

            if (!predicate) {   // cuts off the loop if predicate is false
                return predicate;
            }
        }
        return predicate;
    }

    public static  boolean predicateDYN(File file, String contents, KeyWordItem[] keywordItems, boolean noCase, String formula) {

        if (keywordItems.length == 0) {
            return true;
        }

        String searchBody = noCase ? contents.toLowerCase() : contents;

        String expr = formula;
        for (int i = keywordItems.length - 1; i >= 0; i--) {
            String target = noCase ? keywordItems[i].getValue().toLowerCase() : keywordItems[i].getValue();
            boolean isPresent = searchBody.contains(target);
            boolean tokenTruthStatus = keywordItems[i].isInclude() ? isPresent : !isPresent;
            expr = expr.replace("$" + i, tokenTruthStatus ? "T" : "F");
        }

        return evaluateBooleanExpression(expr);
    }

    private static boolean evaluateBooleanExpression(String expression) {

        String expr = expression.replace(" ", "");
        Stack<Boolean> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (c == 'T' || c == 'F') {
                values.push(c == 'T');
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    executeTopOperator(values, operators);
                }
                if (!operators.isEmpty()) operators.pop();
            } else if (c == '!' || c == '&' || c == '|') {
                if (c == '&' && i + 1 < expr.length() && expr.charAt(i + 1) == '&') i++;
                if (c == '|' && i + 1 < expr.length() && expr.charAt(i + 1) == '|') i++;

                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    executeTopOperator(values, operators);
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            executeTopOperator(values, operators);
        }

        return values.isEmpty() ? false : values.pop();
    }

    private static int precedence(char operator) {
        if (operator == '!') return 3;
        if (operator == '&') return 2;
        if (operator == '|') return 1;
        return 0;
    }

    private static void executeTopOperator(Stack<Boolean> values, Stack<Character> operators) {
        if (operators.isEmpty()) return;
        char op = operators.pop();

        if (op == '!') {
            if (!values.isEmpty()) values.push(!values.pop());
            return;
        }

        if (values.size() < 2) return;
        boolean b = values.pop();
        boolean a = values.pop();

        if (op == '&') values.push(a && b);
        else if (op == '|') values.push(a || b);
    }

    public static  boolean predicateOR(File file, String contents, KeyWordItem[] keywordItems, boolean noCase) {

        if (keywordItems.length == 0) {
            return true;
        }

        boolean predicate = false;
        for (KeyWordItem keyWordItem : keywordItems) {

            String keyWord = keyWordItem.getValue();

            if(noCase){
                keyWord = keyWord.toLowerCase();
                contents = contents.toLowerCase();
            }

            String keyWordSearch = keyWordItem.getValue().toLowerCase();
            predicate = keyWordItem.isInclude() ? predicate ||
                    contents.contains(keyWord)
                    : predicate || !contents.contains(keyWord);
        }

        if (predicate) {  // cuts off the loop if predicate is true
            return predicate;
        }

        return predicate;
    }

    public static String getCriteriaFileNameDesc(
            String root,
            boolean inclFileNames,
            KeyWordItem[] qrys,
            String searchType,
            String formula) {

        if(!inclFileNames) {
            return "";
        }

        String criteriaFileNameDesc = createCriteriaDesc(
                searchType, qrys, root, "name",formula).replaceAll("\"", " ");

        criteriaFileNameDesc += "_in_"+root.replace("\\","_").replace(":","") + " ";
        criteriaFileNameDesc = criteriaFileNameDesc.replace("/","_").replace(":","");

        return criteriaFileNameDesc;
    }

    public static String getCriteriaFileContentDesc(
            String root,
            boolean inclTxtFileContent,
            KeyWordItem[] keyWordItems,
            String txtFileTypes[],
            String searchType,
            String formula) {

        if(!inclTxtFileContent) {
            return "";
        }

        String criteriaFileContentDesc = createCriteriaDesc(
            searchType, keyWordItems, root, "content",formula).replaceAll("\"", " ");

        criteriaFileContentDesc += getfileTypes(txtFileTypes);
        criteriaFileContentDesc += "_in_"+root.replace("\\","_").replace(":","") + " ";
        criteriaFileContentDesc = criteriaFileContentDesc.replace("/","_").replace(":","");
        System.out.println("criteriaFileContentDesc: " + criteriaFileContentDesc);

        return criteriaFileContentDesc.replace("/", "_").replace(":", "");
    }

    private static  String createCriteriaDesc(
            String searchType,
            KeyWordItem[] keyWordItems,
            String root,
            String searchFileOrContent,
            String formula) {

        //String searchTypeModified = searchType.equals("DYN") ? " " : searchType;
        if (searchType.equals("DYN")) {

            if (formula == null || formula.trim().isEmpty()) {
                return "Dynamic search criteria desc";
            }

            List<KeyWordItem> keywordItemsList = Arrays.asList(keyWordItems);
            return PredicateConverter.convertPredicateToDescription(formula, keywordItemsList);
        }

        String criteriaFileContentDesc = "file "+searchFileOrContent+" has";
        criteriaFileContentDesc += buildCriteriaDesc(searchType, keyWordItems);

        return criteriaFileContentDesc;

    }

    private static String buildCriteriaDesc(String searchType, KeyWordItem[] keyWordItems) {

        String criteriaDesc = "";

        int counter = 0;
        for(KeyWordItem q : keyWordItems) {
            String inclExclIndicator = q.isInclude() ? "" : "NOT ";
            if (counter == 0 ){
                criteriaDesc += " "+inclExclIndicator + q.getValue();
            } else {
                criteriaDesc += " " + searchType + " " + inclExclIndicator + q.getValue();
            }
            counter++;
        }

        return criteriaDesc;
    }

    public static String getfileTypes(String[] txtFileTypes) {

        // Using StringJoiner
        StringJoiner sj = new StringJoiner(" "); // Specify space as the delimiter
        for (String txtFileType : txtFileTypes) {
            sj.add(txtFileType);
        }
        String result = " in "+sj.toString()+" ";
        return result;
    }

    public static String sanitizeFilename(String originalFilename) {
        // Replace all problematic characters with an underscore
        // Characters: < > : " / \ | ? *
        // Note: \\ is for a literal backslash in regex, and \" for a literal double quote
        // return originalFilename.replaceAll("[<>:\"/\\\\|?*]"., "_");
        return originalFilename.replaceAll("[<>:\"/\\\\|?*.-]", "_");
    }
}
