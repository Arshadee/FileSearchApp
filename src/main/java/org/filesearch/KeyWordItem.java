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

    private String value;
    private boolean include;

    public KeyWordItem(String value) {
        this.value = value;
        this.include = true;
    }

    public String getKeyWord() {
        return this.value;
    }

    public String toString() {

        return include? "Includes - "+value+" " : "Excludes - "+value+" ";
    }

}
