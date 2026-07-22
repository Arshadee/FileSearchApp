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
    private boolean found;

    public KeyWordItem(String value) {
        this.value = value;
        this.include = true;
        this.found = false;
    }

    public KeyWordItem(String value, boolean include) {
        this.value = value;
        this.include = include;
        this.found = false;
    }

    public KeyWordItem(KeyWordItem other) {
        this.value = other.value;
        this.include = other.include;
        this.found = false;
    }

    public String getKeyWord() {
        return this.value;
    }

    public String toString() {

        return include? "Includes - "+value+" " : "Excludes - "+value+" ";
    }

}
