package org.filesearch;

@FunctionalInterface
public interface IFormatString {

    String modify(String str, boolean isContent);

}
