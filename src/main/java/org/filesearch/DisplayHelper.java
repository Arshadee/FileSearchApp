package org.filesearch;


import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A helper class to display search results in html format
 *
 * Abstracts the display of search results in html format
 * Extends the DisplayHelper class from the cleanfilesearch package
 *
 */
public class DisplayHelper extends org.filesearch.common.DisplayHelper{

    private static String getSearchDetails(IRequestObject requestObject) {

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Or any other format
        String formattedDateTime = dateTime.format(formatter);

        String getFileNameQrys = (requestObject.isInclFileNames())? Arrays.toString(requestObject.getQrys()):"";
        String getFileContentKeyWords = (requestObject.isInclTxtFileContent())?
            Arrays.toString(requestObject.getFileContentKeyWords()):"";

        String searchDetails =
                "<center><table border=\"0\" width=\"100%\">"
                        +"<tr><td bgcolor='#D3D3D3'><b>Search Date : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+ formattedDateTime +"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Directory Searched : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+ requestObject.getDir() +"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Search String in File Name: </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\"><b>"
                       // + StringUtils.encodeHtml(Arrays.toString(requestObject.getQrys()))+"</b></FONT></td></tr>"
                        + StringUtils.encodeHtml(getFileNameQrys) +"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>InclFileNames : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+requestObject.isInclFileNames() +"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Search String in Files: </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        //+"<b>" + StringUtils.encodeHtml(Arrays.toString(requestObject.getFileContentKeyWords())+"")+"</FONT></td></tr>"
                        +"<b>" + StringUtils.encodeHtml(getFileContentKeyWords) +"</FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>InclTxtFileContent : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+ requestObject.isInclTxtFileContent() +"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Query Description in Files: </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+ StringUtils.encodeHtml(requestObject.getQueryDescription()+"") +"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Exclusion List : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+ StringUtils.encodeHtml(requestObject.getExclusionList()+"") +"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>No Case Sensitive : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+ StringUtils.encodeHtml(requestObject.isNoCase()+"") +"</b></FONT></td></tr>";

        if(requestObject.isInclTxtFileContent()){
            searchDetails = searchDetails + "<tr><td bgcolor='#D3D3D3'><b>Text File Types : </b></td><td bgcolor='#f6f6d8'>"
                    +"<FONT COLOR=\"#0000ff\"><b>"
                    +StringUtils.encodeHtml(Arrays.asList(requestObject.getTxtFileTypes())+"")+"</b></FONT></td></tr>";
        }
        searchDetails = searchDetails + "</table></center>";

        return searchDetails;
    }

    private static String getResultStatsStr(IResultContentObject resultContentObject){

        String resultStatsStr =
            "<table border=0 width = 100%>"
                +"<td bgcolor='#D3D3D3'><b> Time taken for search : </b></td>"
                +"<td bgcolor='#f6f6d8'><FONT COLOR='#0000ff'><b>"
                +resultContentObject.getTimeElapsedMinutes()+" mins ("
                +resultContentObject.getTimeElapsedSeconds()+" secs)</b></FONT></td>"

                +"<td bgcolor='#D3D3D3'><b> Number of objects found : </b></td>"
                +"<td bgcolor='#f6f6d8'><FONT COLOR='#0000ff'><b>"+resultContentObject.getResultContents().size()+"</b></FONT></td>"

                +"<td bgcolor='#D3D3D3'><b>Hits Found : </b></td>"
                +"<td bgcolor='#f6f6d8'><FONT COLOR='#0000ff'><b>"+resultContentObject.getHitCount()+"</b></FONT></td>"

                +"<td bgcolor='#D3D3D3'><b> Number of objects searched through : </b></td>"
                +"<td bgcolor='#f6f6d8'><FONT COLOR='#0000ff'><b>"+resultContentObject.getFileCount()+"</b></FONT></td>"
                +"</table>";

        return resultStatsStr;
    }

    private static List<String> getHeaderFooterBody(IRequestObject requestObject, IResultContentObject resultContentObject){

        List<String> results = new ArrayList<>();
        results.add("<table><tr><td>");
        results.add(getSearchDetails(requestObject));
        results.add("</td></tr><tr><td>");
        results.add(getResultStatsStr(resultContentObject));
        results.add("</td></tr></table>");
        results.add("<hr>");

        return results;
    }


    public static List<String> getHeaderHtml(IRequestObject requestObject, IResultContentObject resultContentObject) {

        List<String> results = new ArrayList<>();
        String style = style();
        results.add(style);
        String title="<h2 id=\"top\">Search Results </h2>";
        results.add(title);
        results.add("<center><h3><a href=\"#bottom\">[Bottom]</a></h3></center>");
        results.add("<hr>");
        results.addAll(getHeaderFooterBody(requestObject, resultContentObject));

        return results;
    }

    public static String getFileEntry(String filePath){

        String fileEntry = "<a href = '"+filePath.replace("'","&#39;")+"' target='result'>"
            +"<FONT COLOR='#0000ff' size = 4><b>"+filePath
            +"</a>&nbsp;<a href=\"#top\">[Top]</a>&nbsp;|&nbsp;<a href=\"#bottom\">[Bottom]</a><br></b></FONT>";
        return fileEntry;

    }

    public static String getFileEntry(String filePath, List<String> keyWords) {

        // 1. Intercept the thread-isolated keyword structures if they exist
        KeyWordItem[] threadIsolatedItems = org.filesearch.FileSearchServiceMT2.CURRENT_THREAD_KEYWORDS.get();

        if (threadIsolatedItems != null) {
            // 2. Build the list dynamically using only the matches marked true by this specific worker thread
            List<String> isolatedMatches = new ArrayList<>();
            for (KeyWordItem item : threadIsolatedItems) {
                if ((item.isInclude() && item.isFound()) || (!item.isInclude() && !item.isFound())) {
                    isolatedMatches.add(item.toString());
                }
            }
            keyWords = isolatedMatches;
        }

        String keywordString = "<b>MATCHES KEYWORDS</b><br>"+String.join(", <br>", keyWords);

        String fileEntry = "<div class=\"tooltip\"><a href = '"+filePath.replace("'","&#39;")
                +"' target='result'><FONT COLOR='#0000ff' size = 4><b>"+filePath+"</b></a>"
                +"<span class=\"tooltiptext\">"+keywordString+"</span></div>"
            +"&nbsp;<b><a href=\"#top\">[Top]</a>&nbsp;|&nbsp;<a href=\"#bottom\">[Bottom]</a><br></b></FONT>";
        return fileEntry;

    }

    public static String getFileEntry(String filePath, long fileSize){

        double fileSizeInMB = (double) fileSize / (1024 * 1024);
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedFileSize = df.format(fileSizeInMB);
        String fileEntry = "<div class=\"tooltip\"><a href = '"+filePath.replace("'","&#39;")
                +"' target='result'><FONT COLOR='#0000ff' size = 4><b>"+filePath+"</b></a>"
                +"&nbsp;<b>File size = "+formattedFileSize+"MB</b></FONT>"
                +"&nbsp;<b><a href=\"#top\">[Top]</a>&nbsp;|&nbsp;<a href=\"#bottom\">[Bottom]</a><br></b></FONT>";
        return fileEntry;

    }

    public static List<String> displayResultsInHtml(
        IRequestObject requestObject,
        IResultContentObject resultContentObject) {

        List<String> results = new ArrayList<>(resultContentObject.getResultContents());
//        List<String> results = resultContentObject.getResultContents()
//                .stream()
//                .map(e -> DisplayHelper.getFileEntry(e))
//                .collect(Collectors.toList());

        List<String> header = DisplayHelper.getHeaderHtml(requestObject, resultContentObject);
        results.addAll(0,header);

        List<String> footer = DisplayHelper.getFooterHtml(requestObject, resultContentObject);
        results.addAll(footer);

        return results;
    }

    public static List<String> getFooterHtml(IRequestObject requestObject, IResultContentObject resultContentObject) {

        List<String> resultStats = new ArrayList<>();

      //  if(resultContentObject.getHitCount()==0) {
        if(resultContentObject.getResultContents().size()==0) {
            String noFilesMsg = "No files matching your search found";
            System.out.println(noFilesMsg);
            resultStats.add(noFilesMsg);
        }

       // resultStats.add("<hr>");
        resultStats.add("<h3 id=\"bottom\">");
        resultStats.add("<hr>");
        resultStats.addAll(getHeaderFooterBody(requestObject, resultContentObject));
       // resultStats.add("<hr>");
        resultStats.add("<center><h3><a href=\"#top\">[Top]</a></h3></center>");
        resultStats.add("</body>");
        return resultStats;
    }



    private static String style(){
        return "<html>\n" +
                "<style>\n" +
                ".tooltip {\n" +
                "  position: relative;\n" +
                "  display: inline-block;\n" +
                "  border-bottom: 1px dotted black;\n" +
                "}\n" +
                "\n" +
                ".tooltip .tooltiptext {\n" +
                "  visibility: hidden;\n" +
                "  width: 300px;\n" +
                "  background-color: blue;\n" +
                "  color: #fff;\n" +
                "  text-align: left;\n" +
                "  border-radius: 6px;\n" +
                "  padding: 5px 0;\n" +
                "  \n" +
                "  /* Position the tooltip */\n" +
                "  position: absolute;\n" +
                "  z-index: 1;\n" +
                "  top: -5px;\n" +
                "  left: 105%;\n" +
                "}\n" +
                "\n" +
                ".tooltip:hover .tooltiptext {\n" +
                "  visibility: visible;\n" +
                "}\n" +
                "</style>\n" +
                "<body>";
    }

}
