package org.filesearch;


import org.filesearch.common.UIConfigInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoUIConfig implements UIConfigInterface {

    /* Exclusion list
    Items in exclusion List must be precise directory /file names to be excluded must completely match
    partial matches are ignored
*/
    String[] el = {
           // "windows", "program files", "program files (x86)", "system", "programdata", ".m2", ".m2_bu", ".m2_bu2"
            "windows", "system", "programdata", ".m2", ".m2_bu", ".m2_bu2"
            , "target","biosite","outputs_changeCombo_perms","afrocastwebsite","biositefrontend","biositebackend",
            "oem","veolia","NascenceProj","scala", "dell"};

    List<String> exclusionList = (el == null) ? new ArrayList<>() : Arrays.asList(el);

    /* Set file extensions to exclude */
    String[] extentionsToExclude = {"windows",".zip", ".lnk"};

    /* Txt File Types for content searching */
// String[] txtFileTypes = {".java",".xml",".properties",".txt",".json","yml"};
//     String[] txtFileTypes = {".java",".xml",".properties",".txt",".json",".sql"};
//     String[] txtFileTypes = {".java",".xml",".properties",".txt",".json","log"};
//    String[] txtFileTypes = {".java",".xml",".properties",".txt",".json",".js"};
//     String[] txtFileTypes = {".sql",".txt"};
//   String[] txtFileTypes = {".txt"};
//    String[] txtFileTypes = {".txt",".sql"};
//     String[] txtFileTypes = {".txt","html"};
//     String[] txtFileTypes = {".html"};
//   String[] txtFileTypes = {".java",".properties"};
 String[] txtFileTypes = {".java"};
// String[] txtFileTypes = {".properties"};
//     String[] txtFileTypes = {".txt",".sh",".bat"};
//  String[] txtFileTypes = {".properties"};
//  String[] txtFileTypes = {".properties","xml"};
//   String[] txtFileTypes = {".properties", ".yml" };
//  String[] txtFileTypes = {".java"};
//    String[] txtFileTypes = {".xml"};
//   String[] txtFileTypes = {".java",".txt"};//  String[] txtFileTypes = {".html",".js"};
//     String[] txtFileTypes = {".java",".js"};
//     String[] txtFileTypes = {".java",".txt",".sql"};
//     String[] txtFileTypes = {".java", ".class", ".txt"};
//    String[] txtFileTypes = {".js"};

    /* Search Root Folder */
//  String root = "C:\\Development\\Backend";
//     String root = "C:\\Development\\Backend\\common";
//    String root = "C:\\Development\\Frontend";
//     String root = "C:\\Users\\ArshadMayet\\Desktop";
//     String root = "C:\\Users\\ArshadMayet\\Desktop\\ICE_TECH_INFO";
//     String root = "C:\\Development";
//     String root = "C:\\Development\\Backend\\auth";
//     String root = "C:\\Development\\Backend\\engine";
//     String root = "C:\\Development\\Backend\\mlcs";
//     String root = "C:\\Development\\Backend\\poe";
//        String root = "C:\\Development\\ice-s3-client";

//   String root="C:\\demos\\financial-freedom-bot";
// String root = "C:\\Development_Engine_Release_Master_New";
 //   String root = "C:\\Development_Engine_Release_Master_New\\Backend\\common";
 String root = "C:\\Development_Engine_Release_Master_New\\Backend\\engine";
//     String root = "C:\\Development_Engine_Release_Master_New\\Backend\\mlcs";
//     String root = "C:\\Development_Engine_Release_Master_New\\Backend\\poe";
//   String root = "C:\\Development_Engine_Release_Master_New\\Backend\\auth";

//   String root = "C:\\Development_FSS_BACKOFFICE_2023";
//   String root = "C:\\Development_FSS_BACKOFFICE_2023\\uaa-util-ee";

//String root = "C:\\";
//     String root = "C:\\Users\\ArshadMayet\\MyProjects";
//  String root = "C:\\Users\\ArshadMayet\\Desktop";
//    String root = "C:\\Users\\ArshadMayet\\Desktop\\R&D";
//     String root = "C:\\Users\\ArshadMayet\\Desktop\\ICE_TECH_INFO\\TicketsData";
//     String root = "C:\\Users\\ArshadMayet\\RnD_Projects";
//     String root = "E:\\Users\\Arshad\\Tracing";
//     String root = "E:\\MyBackUps\\ArshadHD\\M";
//     String root = "E:\\";
//String root ="\\\\wsl.localhost\\docker-desktop";
//    String root = "C:\\FileSearchPerformanceTest\\wsl";
//String root ="\\\\wsl.localhost\\docker-desktop\\mnt\\host\\wsl"; // \\wsl.localhost\docker-desktop\mnt\host\wsl

    /* Search File name key words & inclFileNames boolean flag */
    KeyWordItem[] qrys = {
           // new KeyWordItem("lens.exe", true)
//            new KeyWordItem(".mp4", true),
//            new KeyWordItem("croc", true),
//           new KeyWordItem("EngineV2.zip", true),

//           new KeyWordItem("GlobalControllerExceptionHandler", true),
//           new KeyWordItem("s3", true),
//           new KeyWordItem("pdf", true),
//           new KeyWordItem("passport", true),
//           new KeyWordItem(".mp4", true),
//           new KeyWordItem(".asci", true),

            //fin-trad-bot - to get proj Struct
//           new KeyWordItem(".java", true),
//           new KeyWordItem(".properties", true),
//           new KeyWordItem(".xml", true),
//           new KeyWordItem(".gradle", true),
//           new KeyWordItem(".db", true),
//           new KeyWordItem(".sql", true),

//           new KeyWordItem("freedom_bot.mv.db", true),
//           new KeyWordItem("token", true),
//           new KeyWordItem(".java", true),

 //          new KeyWordItem("BackMeUp", true),
//           new KeyWordItem("useful", true),
//           new KeyWordItem(".txt", true),

//           new KeyWordItem("zw-kong.yml", true),
//           new KeyWordItem("brd", true),
//           new KeyWordItem("reportorch", true),
//           new KeyWordItem("opt17.txt", true),

           new KeyWordItem(".java", true),

    };

//boolean inclFileNames = true;
boolean inclFileNames = false;

    /* Search File content key words & inclTxtFileContent boolean flag */
    KeyWordItem[] keyWordItems = new KeyWordItem[]{

 //          new KeyWordItem("ice.service.otp.require-otp-login", true),
//           new KeyWordItem("otpmethod", true),

//            new KeyWordItem("...", true),
//            new KeyWordItem("^(1/2)", true),
//            new KeyWordItem("inf", true),

//            new KeyWordItem("calculus", true),
 //           new KeyWordItem("word", true),
//            new KeyWordItem("problem", true),
//            new KeyWordItem("table", true),
 //           new KeyWordItem("TabletClass Math", true),

//           new KeyWordItem("R&D Links", true),
//           new KeyWordItem("!=", true),
//           new KeyWordItem("factorial", true),

//           new KeyWordItem("t^t", true),
//           new KeyWordItem("box", true),

//           new KeyWordItem("referenceN", true),

//           new KeyWordItem("FeedforwardNetwork", true),
//           new KeyWordItem("MatrixMath", true),
//           new KeyWordItem("GeneticAlgorithm", true),
//           new KeyWordItem("Chromosome", true),
//           new KeyWordItem("NeuralNetworkException", true),

//           new KeyWordItem("v2", true),
//           new KeyWordItem("WebMvcConfigurer", true),
//           new KeyWordItem("cfgTblModuleRepository.deleteByCfgTblNameAndCfgTblIds", true),
//           new KeyWordItem("deleteByCfgTblNameAndCfgTblIds", true),

//           new KeyWordItem("i18n", true),
//           new KeyWordItem("international", true),

 //          new KeyWordItem("IceConfigurationException", true),
 //         new KeyWordItem("@RestController", true),
//           new KeyWordItem("public String", true),

//           new KeyWordItem("otp", true),
//           new KeyWordItem("aslam", true),
//           new KeyWordItem("10/2025", true),

//           new KeyWordItem("extends RuntimeException", true),
//           new KeyWordItem("Invalid Base64", true),
//           new KeyWordItem("Invalid argument", true),

 // C:\\demos\\financial-freedom-bot

//           new KeyWordItem("forgot", true),
//           new KeyWordItem("password", true),
//           new KeyWordItem("aslam", true),
            
 //          new KeyWordItem("8583", true),   // ticket number

//            new KeyWordItem("50", true),
//            new KeyWordItem("chromo", true),

//            new KeyWordItem("ldap", true),
//            new KeyWordItem("password", true),
//            new KeyWordItem("policy", true),
//            new KeyWordItem("ldap", true),
//            new KeyWordItem("passwordPolicies", true),


//           new KeyWordItem("@Scheduled", true),
//           new KeyWordItem("@SchedulerLock", true),
//           new KeyWordItem("infra-kafka.icetech.local", true),

//           new KeyWordItem("@customSecurityService.verify", true),
//           new KeyWordItem("public User findUser(", true),

//           new KeyWordItem("reloadComponent", true),
//           new KeyWordItem("@rest", true),

//           new KeyWordItem("X-Agency-ID", true),

//          new KeyWordItem("sync", true),
//          new KeyWordItem("@scheduled", true),

//          new KeyWordItem("kong", true),
//          new KeyWordItem("gateway", true),
//          new KeyWordItem("api", true),
//          new KeyWordItem("v1", true),
//          new KeyWordItem("token", true),
//
//       new KeyWordItem("@RestController", true),
//         new KeyWordItem("/rest", true),
//          new KeyWordItem("submit", true),
//          new KeyWordItem("submitactivity", true),
//          new KeyWordItem("ext", true),

 //           new KeyWordItem("getAllTableNames", true),
  //        new KeyWordItem("findall", true),

  //        new KeyWordItem("user_detail_meta", true),
//          new KeyWordItem("system_user", true),
//          new KeyWordItem("anon", true),
//          new KeyWordItem("anonymous", true),

 //         new KeyWordItem("mail", true),


 //        new KeyWordItem("combinat", true),
 //        new KeyWordItem("graal", true),
 //        new KeyWordItem("ConfigMap", true),

//         new KeyWordItem("m-pesa", true),
//         new KeyWordItem("pesa", true),

//         new KeyWordItem("finger", true),
//         new KeyWordItem("legalentity", true),
//         new KeyWordItem("legal_entity", true),
//         new KeyWordItem("Biometric", true),
//         new KeyWordItem("s3", true),
//         new KeyWordItem("supporting", true),
//         new KeyWordItem("document", true),
//         new KeyWordItem("entity", true),
//            new KeyWordItem("supporting_document", true),
//            new KeyWordItem("ices3referenceId", true),
//            new KeyWordItem("main", true),
//            new KeyWordItem("condition", true),

 //           new KeyWordItem("argocd", true),
 //          new KeyWordItem("IED-8906", true),
//            new KeyWordItem("execution", true),
//            new KeyWordItem("order", true),

//            new KeyWordItem("find_one_user", true),
//            new KeyWordItem("findoneuser", true),
//            new KeyWordItem("user", true),

           new KeyWordItem("activityQuery", true),
            new KeyWordItem("@Rest", true),

    };

boolean inclTxtFileContent = true;
//boolean inclTxtFileContent = false;


    /* Specify Search Type */
//String searchType = "OR";  //  (default)
//  String searchType = "DYN";  // must update the predicate in AutoUIConfigUtilities.predicateDYN
String searchType = "AND";

boolean noCase = true; //default
//boolean noCase = false;



    public AutoUIConfig() {}

    @Override
    public void executeConfig() {
        System.out.println("Running . . .");

        // To update depending on kewwords and search criteria

        String criteriaFileNameDesc = AutoUIConfigUtilities.getCriteriaFileNameDesc(
                root, inclFileNames, qrys, searchType);

        String criteriaFileContentDesc = AutoUIConfigUtilities.getCriteriaFileContentDesc(
                root, inclTxtFileContent, keyWordItems, txtFileTypes, searchType);


        String criteriaDesc = criteriaFileNameDesc + criteriaFileContentDesc;

        String queryDescription = "Search "+ criteriaDesc;

        /* Create Request Object */
        IRequestObject requestObject =
                RequestObject.builder()
                        .dir(root)
                        .qrys(qrys)
                        .fileContentKeyWords(keyWordItems)
                        .inclTxtFileContent(inclTxtFileContent)
                        .txtFileTypes(txtFileTypes)
                        .exclusionList(exclusionList)
                        .inclFileNames(inclFileNames)
                        .extentionsToExclude(extentionsToExclude)
                        .queryDescription(queryDescription)
                        .noCase(noCase)
                        .build();

        /* Call the file search method from fileSearchMT.listAllDirectoryFiles */
        ISearchService fileSearchService = new FileSearchServiceMT2();
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

                // QUERY BUILDER
             (file, contents,nocase, keywordItems) -> {
               if (searchType.equals("OR")) { // ALL_OR   (default)
                   return AutoUIConfigUtilities.predicateOR(file, contents, keywordItems, nocase);
               }
               else if (searchType.equals("AND")) { //ALL_AND
                   return AutoUIConfigUtilities.predicateAND(file, contents, keywordItems, nocase);
               }
               else { // Dynamic Congfiguration
                   return AutoUIConfigUtilities.predicateDYN(file, contents, keywordItems, nocase);
             }
       });

        /* Setting and creating search results as html output file */
        String filename = root.substring(0,1)+ "_" + criteriaDesc.replace(" ","_");
        filename = AutoUIConfigUtilities.sanitizeFilename(filename); // Sanitize the filename
        String filePath = "src/main/resources/searchResultMT_wrk_"+filename+"mod.html";
       // filePath = AutoUIConfigUtilities.sanitizeFilename(filePath); // Sanitize the file path
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


}
