package org.filesearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * KeyWordItem is a class that holds the search criteria
 */
@Builder
@Data
@AllArgsConstructor
public class KeyWordItem implements IKeyWordItem {

    private String keyWord;
    private boolean include;

    public KeyWordItem(String keyWord) {
        this.keyWord = keyWord;
        this.include = true;
    }

    public String toString() {

        return include? "Includes - "+keyWord+" " : "Excludes - "+keyWord+" ";
    }

}
