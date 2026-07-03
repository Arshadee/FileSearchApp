package org.filesearch;

public interface IKeyWordItem {

    String getValue();
    void setValue(String keyWord);

    boolean isInclude();

    void setInclude(boolean include);

}
