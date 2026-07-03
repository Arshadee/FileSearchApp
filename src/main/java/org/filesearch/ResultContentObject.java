package org.filesearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ResultContentObject is a class that holds the result of a search
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResultContentObject implements IResultContentObject{

    private List<String> resultContents; // raw search results - file paths that match the search criteria
    private long hitCount;
    private long start;
    private long end;
    private long fileCount;

    public long getTimeElapsed() {
        return end - start;
    }

    public double getTimeElapsedSeconds() {
        return getTimeElapsed()/1000;
    }

    public double getTimeElapsedMinutes() {
        return getTimeElapsedSeconds()/60;
    }
}
