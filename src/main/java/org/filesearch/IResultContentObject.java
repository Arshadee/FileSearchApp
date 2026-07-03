package org.filesearch;

import java.util.List;

public interface IResultContentObject {

    List<String> getResultContents();

    void setResultContents(List<String> resultContents);

    long getHitCount();

    void setHitCount(long hitCount);

    long getStart();

    void setStart(long start);

    long getEnd();

    void setEnd(long end);

    long getFileCount();

    void setFileCount(long fileCount);

    long getTimeElapsed();

    double getTimeElapsedSeconds();

    double getTimeElapsedMinutes();

}
