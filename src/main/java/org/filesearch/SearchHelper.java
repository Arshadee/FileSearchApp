package org.filesearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is a helper class for the search service
 * To contain and abstract the search logic
 */
public class SearchHelper {

    public boolean isTxtFile(File file, String[] txtFileTypes){

        if(txtFileTypes == null || txtFileTypes.length == 0) return true;

        for(int i =0 ; i < txtFileTypes.length; i++){
            if(file.getName().toLowerCase().endsWith(txtFileTypes[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean addChildListCondition(File file, String ... ignoreList){
        boolean predicate = file != null && file.isDirectory();
        for(String ignore : ignoreList){
            predicate = predicate && !file.getName().toLowerCase().contains(ignore);
        }

        return predicate;
    }
    
    public boolean toInclude(File file, List<String> exclusionList) {
        return !exclusionList.stream().map(e->e.toLowerCase()).toList()
                .contains(file.getName().toLowerCase());
    }
    
    public boolean containsQueryStrings(List<String>qryStrings, File file) {
        if(qryStrings.isEmpty()) return true;
        for(String qry : qryStrings){
            if(file.getName().toLowerCase().contains(qry.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public boolean toSearchTxtFileContents(File file, String[] txtFileTypes,
        boolean localToInclTxtFileContent,
        List<String> exclusionList) {

       return  !file.isDirectory() && isTxtFile(file, txtFileTypes)
                && localToInclTxtFileContent && toInclude(file,exclusionList);
    }

    public boolean isValidChild(File file, List<String> exclusionList){
       return  file.isDirectory() && toInclude(file,exclusionList);
    }

    public List<String> getEntryKeysWordHis(File file,  KeyWordItem[] keyWordItems) {

        List<KeyWordItem> keyWordHits = new ArrayList<>(List.of(keyWordItems));
        List<String> keyWordHitsList = new ArrayList<>();

      try {
          byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
          String fileContent = new String(bytes);

        keyWordHitsList = keyWordHits.stream()
            //.filter(keyWordItem -> fileContent.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase()))
            .filter(keyWordItem -> {
                if(keyWordItem.isInclude())
                    return fileContent.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase());
                else
                    return !fileContent.toLowerCase().contains(keyWordItem.getKeyWord().toLowerCase());
            })
                .map(KeyWordItem::toString)
            .collect(Collectors.toList());
      } catch (IOException e) {
          e.printStackTrace();
      }
        return keyWordHitsList;

    }

//    public boolean  toSearchFileByName(File file, List<String> exclusionList, List<String> qrys, boolean inclFileNames){
//        return toInclude(file,exclusionList) && containsQueryStrings(qrys, file) && inclFileNames;
//    }
public boolean  toSearchFileByName(File file, List<String> exclusionList, boolean inclFileNames){
    return toInclude(file,exclusionList)  && inclFileNames;
}

}
