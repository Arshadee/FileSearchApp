package org.filesearch.dto;

import org.filesearch.KeyWordItem;

import java.util.List;

public class SearchConfigData {
    private String root;
    private String searchType;
    private boolean inclFileNames;
    private boolean inclTxtFileContent;
    private boolean noCase;
    private String[] txtFileTypes;
    private List<String> exclusionList;
    private String[] extentionsToExclude;
    private KeyWordItem[] queries;
    private KeyWordItem[] keyWordItems;

    // Getters and Setters for Jackson mapping execution
    public String getRoot() { return root; }
    public void setRoot(String root) { this.root = root; }
    public String getSearchType() { return searchType; }
    public void setSearchType(String searchType) { this.searchType = searchType; }
    public boolean isInclFileNames() { return inclFileNames; }
    public void setInclFileNames(boolean inclFileNames) { this.inclFileNames = inclFileNames; }
    public boolean isInclTxtFileContent() { return inclTxtFileContent; }
    public void setInclTxtFileContent(boolean inclTxtFileContent) { this.inclTxtFileContent = inclTxtFileContent; }
    public boolean isNoCase() { return noCase; }
    public void setNoCase(boolean noCase) { this.noCase = noCase; }
    public String[] getTxtFileTypes() { return txtFileTypes; }
    public void setTxtFileTypes(String[] txtFileTypes) { this.txtFileTypes = txtFileTypes; }
    public List<String> getExclusionList() { return exclusionList; }
    public void setExclusionList(List<String> exclusionList) { this.exclusionList = exclusionList; }
    public String[] getExtentionsToExclude() { return extentionsToExclude; }
    public void setExtentionsToExclude(String[] extentionsToExclude) { this.extentionsToExclude = extentionsToExclude; }
    public KeyWordItem[] getQueries() { return queries; }
    public void setQueries(KeyWordItem[] queries) { this.queries = queries; }
    public KeyWordItem[] getKeyWordItems() { return keyWordItems; }
    public void setKeyWordItems(KeyWordItem[] keyWordItems) { this.keyWordItems = keyWordItems; }
}