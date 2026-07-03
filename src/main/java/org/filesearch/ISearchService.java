package org.filesearch;


@FunctionalInterface
public interface ISearchService {

    IResultContentObject  listAllDirectoryFiles(
            IRequestObject requestObject,
            IFormatString entryFormatter,
            IQueryBuilder queryBuilder);
}
