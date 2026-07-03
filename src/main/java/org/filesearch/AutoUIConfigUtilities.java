package org.filesearch;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class AutoUIConfigUtilities {

    public static boolean predicateAND(File file, String contents, KeyWordItem[] keywordItems, boolean noCase) {

        if (keywordItems.length == 0) return true;

        boolean predicate = true;

        for (KeyWordItem keyWordItem : keywordItems) {

            String keyWord = keyWordItem.getKeyWord();

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

    public static  boolean predicateDYN(File file, String contents, KeyWordItem[] keywordItems, boolean noCase) {
        if (keywordItems.length == 0) {
            return true;
        }
//        new KeyWordItem("implements JavaDelegate", true),
//                new KeyWordItem("catch (Exception e)", true),
//                new KeyWordItem("catch (Throwable t)", true),
        return (
                (contents.toLowerCase().contains(keywordItems[0].getKeyWord().toLowerCase()) &&
                    ( contents.toLowerCase().contains(keywordItems[1].getKeyWord().toLowerCase()) ||
                      contents.toLowerCase().contains(keywordItems[2].getKeyWord().toLowerCase()) )
                )

        );
    }

    public static  boolean predicateOR(File file, String contents, KeyWordItem[] keywordItems, boolean noCase) {

        if (keywordItems.length == 0) {
            return true;
        }

        boolean predicate = false;
        for (KeyWordItem keyWordItem : keywordItems) {

            String keyWord = keyWordItem.getKeyWord();

            if(noCase){
                keyWord = keyWord.toLowerCase();
                contents = contents.toLowerCase();
            }

            String keyWordSearch = keyWordItem.getKeyWord().toLowerCase();
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
        String searchType) {

        if(!inclFileNames) {
            return "";
        }

//        String searchTypeModified = searchType.equals("DYN") ? " " : searchType;
//        String criteriaFileNameDesc = "file names has";
//        criteriaFileNameDesc +=  buildCriteriaDesc(searchTypeModified, qrys);
//        criteriaFileNameDesc += root.replace("\\","_").replace(":","") + " ";
        String criteriaFileNameDesc = createCriteriaDesc(
            searchType, qrys, root, "name").replaceAll("\"", " ");

        criteriaFileNameDesc += "_in_"+root.replace("\\","_").replace(":","") + " ";
        criteriaFileNameDesc = criteriaFileNameDesc.replace("/","_").replace(":","");

        return criteriaFileNameDesc;
    }

    public static String getCriteriaFileContentDesc(
        String root,
        boolean inclTxtFileContent,
        KeyWordItem[] keyWordItems,
        String txtFileTypes[],
        String searchType) {

        if(!inclTxtFileContent) {
            return "";
        }

//        String searchTypeModified = searchType.equals("DYN") ? " " : searchType;
//        String criteriaFileContentDesc = "file content has";
//        criteriaFileContentDesc += buildCriteriaDesc(searchTypeModified, keyWordItems);
//        criteriaFileContentDesc += root.replace("\\","_").replace(":","") + " ";
        String criteriaFileContentDesc = createCriteriaDesc(
            searchType, keyWordItems, root, "content").replaceAll("\"", " ");

        criteriaFileContentDesc += getfileTypes(txtFileTypes);

        criteriaFileContentDesc += "_in_"+root.replace("\\","_").replace(":","") + " ";
        criteriaFileContentDesc = criteriaFileContentDesc.replace("/","_").replace(":","");
System.out.println("criteriaFileContentDesc: " + criteriaFileContentDesc);
        return criteriaFileContentDesc;
    }

    private static  String createCriteriaDesc(
        String searchType,
        KeyWordItem[] keyWordItems,
        String root, String searchFileOrContent) {

        //String searchTypeModified = searchType.equals("DYN") ? " " : searchType;
        if (searchType.equals("DYN")) {
            // "Dynamic search criteria desc";
            // get from return predicate in predicateDYN(..) paste code as String below in predicateCode
            String predicateCode = "   (contents.toLowerCase().contains(keywordItems[0].getKeyWord().toLowerCase()) &&\n" +
                    "( contents.toLowerCase().contains(keywordItems[1].getKeyWord().toLowerCase()) ||\n" +
                    "   contents.toLowerCase().contains(keywordItems[2].getKeyWord().toLowerCase()) )";

            predicateCode = predicateCode.replace("\""," ");
            System.out.println(predicateCode);
            List<KeyWordItem> keywordItemsList = Arrays.asList(keyWordItems);

            return PredicateConverter.convertPredicateToDescription(predicateCode, keywordItemsList);
        }

        String criteriaFileContentDesc = "file "+searchFileOrContent+" has";
        criteriaFileContentDesc += buildCriteriaDesc(searchType, keyWordItems);
       // criteriaFileContentDesc += root.replace("\\","_").replace(":","") + " ";

        return criteriaFileContentDesc;

    }

    private static String buildCriteriaDesc(String searchType, KeyWordItem[] keyWordItems) {

        String criteriaDesc = "";

        int counter = 0;
        for(KeyWordItem q : keyWordItems) {
            String inclExclIndicator = q.isInclude() ? "" : "NOT ";
            if (counter == 0 ){
                criteriaDesc += " "+inclExclIndicator + q.getKeyWord();
            } else {
                criteriaDesc += " " + searchType + " " + inclExclIndicator + q.getKeyWord();
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
