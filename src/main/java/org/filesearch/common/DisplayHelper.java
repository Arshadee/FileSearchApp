package org.filesearch.common;


import org.filesearch.StringUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayHelper {
    
    public static void footer(long hitCount, long start, List<String> results, long fileCount){
        
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        double timeElapsedSeconds = timeElapsed/1000;
        double timeElapsedMinutes = timeElapsedSeconds/60;
        
        if(hitCount==0) {
            String noFilesMsg = "No files matching your search found";
            System.out.println(noFilesMsg);
            results.add(noFilesMsg);
        }
        
        List<String> resultStats = new ArrayList<>();
        resultStats.add("<h3 id=\"bottom\">");
        System.out.println();
        
        String resultStat = "Time taken for search : "+timeElapsedMinutes+" mins ("+timeElapsedSeconds+" secs)";
        System.out.println(resultStat);
        resultStats.add(resultStat+"<br>");
        
        resultStat = "Number of objects found "+hitCount+" resultset size "+ results.size();
        System.out.println(resultStat);
        resultStats.add(resultStat+"<br>");
        
        resultStat ="Number of objects searched through "+fileCount;
        System.out.println(resultStat);
        
        resultStats.add(resultStat+"<br>");
        
        results.add("<hr>");
        results.addAll(resultStats);
        results.add("<center><h3><a href=\"#top\">[Top]</a></h3></center>");
        
    }
    
    public static List<String> getHeader(String dir, List<String> qrys,
                                          boolean inclTxtFileContent, String[] txtFileTypes, List<String> exclusionList){
        List<String> results = new ArrayList<>();
        LocalDate dte = LocalDate.now();
        String title="<h2 id=\"top\">Search Results : "+dte+"</h2>";
        String searchDetails = "<h3>Directory Searched : "+"<FONT COLOR=\"#0000ff\">"+dir+"</FONT>"
                                   +"<br>Search String : "+"<FONT COLOR=\"#0000ff\">"+ StringUtils.encodeHtml(qrys+"")+"</FONT>"
                                   +"<br> Exclusion List : "+"<FONT COLOR=\"#0000ff\">"+StringUtils.encodeHtml(exclusionList+"")+"</FONT>"
                                   +"<br> InclTxtFileContent : "+"<FONT COLOR=\"#0000ff\">"+inclTxtFileContent+"</FONT>";
        if(inclTxtFileContent){
            searchDetails = searchDetails + "<br> Text File Types : "+"<FONT COLOR=\"#0000ff\">"+StringUtils.encodeHtml(Arrays.asList(txtFileTypes)+"")+"</FONT><h3>";
        }
        results.add(title);
        results.add("<hr>");
        results.add("<center><h3><a href=\"#bottom\">[Bottom]</a></h3></center>");
        results.add(searchDetails);
        results.add("<hr>");
        return results;
    }
    
    public static String getFileEntry(File file, List<String> qrys){
        String fileEntry = "<a href = '"+file.getAbsolutePath().replace("'","&#39;")+"' target='result'>";
        String filePath = file.getAbsolutePath();
        
        for(String qry : qrys){
            filePath = filePath.replaceAll(qry,"["+qry+"]");
            filePath = filePath.replaceAll(qry,"<FONT COLOR=\"#ff0000\">"+qry+"</FONT>");
        }
        
        fileEntry = fileEntry+filePath+"</a>&nbsp;<a href=\"#top\">[Top]</a>&nbsp;|&nbsp;<a href=\"#bottom\">[Bottom]</a><br>";
        
        return fileEntry;
    }
    
    public static String getFileEntry(File file, String qry) {
        
        String fileEntry = "<a href = '"+file.getAbsolutePath().replace("'","&#39;")+"' target='result'>";
        String filePath = file.getAbsolutePath();
        fileEntry = fileEntry+filePath+"<FONT COLOR=\"#ff0000\">- incl - "+qry+"</FONT>" +
                        "</a>&nbsp;<a href=\"#top\">[Top]</a>&nbsp;|&nbsp;<a href=\"#bottom\">[Bottom]</a><br>";
        return fileEntry;
    }


}
