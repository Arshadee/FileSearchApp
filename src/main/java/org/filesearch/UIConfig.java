package org.filesearch;


import org.filesearch.common.UIConfigInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is a configuration class for the FileSearchServiceExp
 * To run searches.
 *
 * To be, configured with search key words, dynamic java code for query,
 * display and code flow for more complex searches.
 *
 * This class is a configuration class for the FileSearchServiceExp
 */
public class UIConfig implements UIConfigInterface {

    public UIConfig() {}

    @Override
    public void executeConfig() {
        System.out.println("Running . . .");

        /* Exclusion list
            Items in exclusion List must be precise directory /file names to be excluded must completely match
            partial matches are ignored
        */
        String[] el = {
            "windows", "program files", "program files (x86)", "system", "programdata", ".m2", ".m2_bu", ".m2_bu2"
        //    "windows", "system", "programdata", ".m2", ".m2_bu", ".m2_bu2"
            , "target","biosite","outputs_changeCombo_perms","afrocastwebsite","biositefrontend","biositebackend",
        "oem","veolia","NascenceProj","scala", "dell"};

        List<String> exclusionList = (el == null) ? new ArrayList<>() : Arrays.asList(el);

        /* Set file extensions to exclude */
        String[] extentionsToExclude = {"windows",".zip", ".lnk"};

        /* Txt File Types for content searching */
//      String[] txtFileTypes = {".java",".xml",".properties",".txt",".json"};
//      String[] txtFileTypes = {".java",".xml",".properties",".txt",".json",".sql"};
//     String[] txtFileTypes = {".java",".xml",".properties",".txt",".json","log"};
//      String[] txtFileTypes = {".java",".xml",".properties",".txt",".json",".js"};
//      String[] txtFileTypes = {".sql",".txt"};
//     String[] txtFileTypes = {".txt"};
//      String[] txtFileTypes = {".java",".properties"};
//      String[] txtFileTypes = {".txt",".sh",".bat"};
//      String[] txtFileTypes = {".properties"};
      String[] txtFileTypes = {".java"};
//      String[] txtFileTypes = {".java",".txt"};
//      String[] txtFileTypes = {".html",".js"};
//      String[] txtFileTypes = {".java",".js"};
//      String[] txtFileTypes = {".java",".txt",".sql"};
//      String[] txtFileTypes = {".java", ".class", ".txt"};

        /* Search Root Folder */
//     String root = "C:\\Development\\Backend";
//     String root = "C:\\Development\\Backend\\common";
//     String root = "C:\\Development\\Frontend";
//     String root = "C:\\Users\\ArshadMayet\\Desktop";
//     String root = "C:\\Users\\ArshadMayet\\Desktop\\ICE_TECH_INFO";
//     String root = "C:\\Development";
//     String root = "C:\\Development\\Frontend";
//     String root = "C:\\Development\\Backend\\auth";
//     String root = "C:\\Development\\Backend\\engine";
//     String root = "C:\\Development\\Backend\\mlcs";
//     String root = "C:\\Development_Engine_Release_Master";
       String root = "C:\\Development_Engine_Release_Master_New";
//       String root = "C:\\Development_Engine_Release_Master_New\\Backend\\engine";
//       String root = "C:\\Development_Engine_Release_Master_New\\Backend\\mlcs";
//       String root = "C:\\Development_Engine_Release_Master_New\\Backend\\poe";
//     String root = "C:\\";
//     String root = "C:\\Users\\ArshadMayet\\MyProjects";
//     String root = "C:\\Users\\ArshadMayet\\Desktop";
//     String root = "C:\\Users\\ArshadMayet\\RnD_Projects";
//     String root = "E:\\Users\\Arshad\\Tracing";
//     String root = "E:\\MyBackUps\\ArshadHD\\M";
//     String root = "E:\\";

        /* Search File name key words & inclFileNames boolean flag */
        KeyWordItem[] qrys = {

          new KeyWordItem("BP98000014_QMMMaintain", true)

        };

       // List<String> qryList = (qrys == null) ? new ArrayList<>() : Arrays.asList(qrys);
       // List<KeyWordItem> qryList = (qrys == null) ? new ArrayList<>() : Arrays.asList(qrys);
//      boolean inclFileNames = true;
      boolean inclFileNames = false;

        // To update depending n kewwords and search criteria
        String criteriaFileNameDesc = (inclFileNames)? " file names has BP98000014_QMMMaintain in "
                + root.replace("\\","_").replace(":","") + " ": "";

        /* Search File content key words & inclTxtFileContent boolean flag */
        KeyWordItem[] keyWordItems = {

            new KeyWordItem("oauth", true),
            new KeyWordItem("jwk_set", true),

        };

      boolean inclTxtFileContent = true;
//      boolean inclTxtFileContent = false;

        // Build Query Description String (for display purposes only)

        // To update depending n kewwords and search criteria
        String criteriaFileContentDesc = (inclTxtFileContent)?
            " file content has all rest and oauth and jwk_set in java files in "
            + root.replace("\\","_").replace(":","") + " ": "";
        String criteriaDesc = criteriaFileNameDesc + criteriaFileContentDesc;
      //  String queryDescription = "Search "+ criteriaDesc +" "+ root +".";
        String queryDescription = "Search "+ criteriaDesc;
    //    Search file contentfile names brain or teaser or dollar brain OR teaser OR dollar E:\.

        /* Create Request Object */
        IRequestObject requestObject =
            RequestObject.builder()
                .dir(root)
                //.qrys(qryList)
                .qrys(qrys)
                .fileContentKeyWords(keyWordItems)
                .inclTxtFileContent(inclTxtFileContent)
                .txtFileTypes(txtFileTypes)
                .exclusionList(exclusionList)
                .inclFileNames(inclFileNames)
                .extentionsToExclude(extentionsToExclude)
                .queryDescription(queryDescription)
                .build();

        /* Call the file search method from fileSearchMT.listAllDirectoryFiles */
        //ISearchService fileSearchService = new FileSearchServiceExp();
        ISearchService fileSearchService = new FileSearchServiceMT2();
       // ISearchService fileSearchService = new FileSearchSvcConc();
        IResultContentObject resultContentObject = fileSearchService.listAllDirectoryFiles(
            requestObject,
                // ENTRY FORMATTER
                (result, isContent) -> { // For html entry result with keywords on tooltip

                    if(isContent) {
                           File file = new File(result);
                           SearchHelper search = new SearchHelper();
                           List<String> keyWords = search.getEntryKeysWordHis(file, keyWordItems);
                           return DisplayHelper.getFileEntry(result, keyWords);
                    } else {
                            return DisplayHelper.getFileEntry(result);
                       }
                },
             /*
             (result, isContent) -> { // For html entry result
                String entry = DisplayHelper.getFileEntry(result);
                return entry;
            },*/
            /*
            (result, isContent) -> result , // For raw entry result
            */
             /*
                     new KeyWordItem("build.", true),
           new KeyWordItem("git.build.", true),
           new KeyWordItem("application.", true),
           new KeyWordItem("spring-application.application-availability.", false),
              */
             // QUERY BUILDER
    /*   (file, contents,keywordItems) -> { // Dynamic Congfiguration
                 if (keywordItems.length == 0) return true;
                 // customize predicate
                 return (
                         contents.toLowerCase().contains(keywordItems[0].getKeyWord().toLowerCase()) &&
                            ( contents.toLowerCase().contains(keywordItems[1].getKeyWord().toLowerCase()) ||
                              contents.toLowerCase().contains(keywordItems[2].getKeyWord().toLowerCase()) )
                         );
             } */
         (file, contents,noCase, keywordItems) -> {   //ALL_AND

                if (keywordItems.length == 0) return true;

               boolean predicate = true;
                for (KeyWordItem keyWordItem : keywordItems) {
                    predicate = keyWordItem.isInclude() ? predicate &&
                            contents.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase())
                            : predicate && !contents.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase());

                    if (!predicate) {   // cuts off the loop if predicate is false
                        return predicate;
                    }
                }
                return predicate;
            }
/* (file, contents,keywordItems) -> { // ALL_OR   (default)

                // keywordItems = keyWordItems;
                if (keywordItems.length == 0) {
                    return true;
                }

                boolean predicate = false;
                for (KeyWordItem keyWordItem : keywordItems) {
                    predicate = keyWordItem.isInclude() ? predicate ||
                            contents.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase())
                            : predicate || !contents.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase());
                }

                if (predicate) {  // cuts off the loop if predicate is true
                        return predicate;
                }

                return predicate;
            } */

        );

        /* Setting and creating search results as html output file */
        String filename = root.substring(0,1) + criteriaDesc.replace(" ","_");
        String filePath = "src/main/resources/searchResultMT_wrk_"+filename+"mod.html";
        // call html Display Header and Footer
        List<String> resultsInHtml = DisplayHelper.displayResultsInHtml(requestObject, resultContentObject);

        FileSearchIO.writeToFile(resultsInHtml, filePath);
        System.out.println("Search results written to file : " + filePath);

        // for console output - for debugging
        System.out.println("Time taken for search : " +resultContentObject.getTimeElapsedMinutes()+" mins ("
                +resultContentObject.getTimeElapsedSeconds()+" secs)");
        System.out.println("Number of objects found : "+resultContentObject.getResultContents().size()
                +"  Hits Found : " + resultContentObject.getHitCount());
        System.out.println("Number of objects searched through : "+resultContentObject.getFileCount());
    } // end of executeConfig

    /*
        Possible to be used later and expanded on for more complex searches
        that require 2 or more search results to be combined in the search criteria.
    */
//    public IResultContentObject getSearchResults(IRequestObject requestObject) throws UnsupportedEncodingException {
//
//        ISearchService fileSearchService = new FileSearchService();
//        IResultContentObject resultContentObject = fileSearchService.listAllDirectoryFiles(
//                requestObject,
//                (result, isContent) -> { // For html entry result
//                    String entry = DisplayHelper.getFileEntry(result);
//                    return entry;
//                },
//            /*
//            (result, isContent) -> result , // For raw entry result
//            */
//          /*      (file, contents,keywordItems) -> {
//                    if (keywordItems.length == 0) return true;
//
//                    boolean predicate = true;
//                    for (KeyWordItem keyWordItem : keywordItems) {
//                        predicate = keyWordItem.isInclude() ? predicate &&
//                                contents.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase())
//                                : predicate && !contents.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase());
//
//                        if (!predicate) {   // cuts off the loop if predicate is false
//                            return predicate;
//                        }
//                    }
//                    return predicate;
//                } */
//                //
//                (file, contents,keywordItems) -> {
//                // keywordItems = keyWordItems;
//                if (keywordItems.length == 0) {
//                    return true;
//                }
//
//                boolean predicate = false;
//                for (KeyWordItem keyWordItem : keywordItems) {
//                    predicate = keyWordItem.isInclude() ? predicate ||
//                            contents.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase())
//                            : predicate || !contents.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase());
//                }
//
//                if (predicate) {  // cuts off the loop if predicate is true
//                        return predicate;
//                }
//
//                return predicate;
//            }
//        );
//
//        return resultContentObject;
//    }

}