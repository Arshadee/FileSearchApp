package org.filesearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * RequestObject is a class that holds the search criteria
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestObject implements IRequestObject{

    private String dir; // selected root
    //private List<String> qrys;
    private KeyWordItem[] qrys;
    private KeyWordItem[] fileContentKeyWords;
    private boolean inclTxtFileContent;
    private String[] txtFileTypes;
    private List<String> exclusionList;
    private boolean inclFileNames;
    private String[] extentionsToExclude;
    private String queryDescription;
    private boolean noCase;

}
