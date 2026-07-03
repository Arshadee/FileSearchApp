package org.filesearch;

import java.util.List;

public interface IRequestObject {

    String getDir();

    void setDir(String dir);

    //List<KeyWordItem> getQrys();
    KeyWordItem[] getQrys();

    //void setQrys(List<String> qrys);
    void setQrys(KeyWordItem[] qrys);

    KeyWordItem[] getFileContentKeyWords();

    void setFileContentKeyWords(KeyWordItem[] fileContentKeyWords);

    boolean isInclTxtFileContent();

    void setInclTxtFileContent(boolean inclTxtFileContent);

    String[] getTxtFileTypes();

    void setTxtFileTypes(String[] txtFileTypes);

    List<String> getExclusionList();

    void setExclusionList(List<String> exclusionList);

    boolean isInclFileNames();

    void setInclFileNames(boolean inclFileNames);

    String[] getExtentionsToExclude();

    void setExtentionsToExclude(String[] extentionsToExclude);

    String getQueryDescription();

    void setQueryDescription(String describeQuery);

    void setNoCase(boolean noCase);

    boolean isNoCase();

}
