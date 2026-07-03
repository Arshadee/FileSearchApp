package org.filesearch;

import java.io.File;

@FunctionalInterface
public interface IQueryBuilder {
       boolean buildPredicate(File file, String contents, boolean nocase, KeyWordItem... keyWordItems);
}
