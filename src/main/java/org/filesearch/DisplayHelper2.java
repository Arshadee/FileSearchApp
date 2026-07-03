package org.filesearch;

import java.time.LocalDate;
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
public class DisplayHelper2 extends org.filesearch.common.DisplayHelper{

    private static String getSearchDetails(IRequestObject requestObject){
        LocalDate dte = LocalDate.now();

        String searchDetails =
                "<center><table border=\"0\" width=\"100%\">"
                        +"<tr><td bgcolor='#D3D3D3'><b>Search Date : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+dte+"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Directory Searched : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+requestObject.getDir()+"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Search String in File Name: </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\"><b>"
                        + StringUtils.encodeHtml(Arrays.toString(requestObject.getQrys()))+"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>InclFileNames : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+requestObject.isInclFileNames()+"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Search String in Files: </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>" + StringUtils.encodeHtml(Arrays.toString(requestObject.getFileContentKeyWords())+"")
                        +"</FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>InclTxtFileContent : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+requestObject.isInclTxtFileContent()+"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Query Description in Files: </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+ StringUtils.encodeHtml(requestObject.getQueryDescription()+"")+"</b></FONT></td></tr>"

                        +"<tr><td bgcolor='#D3D3D3'><b>Exclusion List : </b></td><td bgcolor='#f6f6d8'>"+"<FONT COLOR=\"#0000ff\">"
                        +"<b>"+ StringUtils.encodeHtml(requestObject.getExclusionList()+"")+"</b></FONT></td></tr>";

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
        String title="<h2 id=\"top\">Search Results : </h2>";

        results.add(title);
        results.add("<hr>");
        results.add("<center><h3><a href=\"#bottom\">[Bottom]</a></h3></center>");

        results.addAll(getHeaderFooterBody(requestObject, resultContentObject));

        return results;
    }

    public static String getFileEntry(String filePath){

        String fileEntry = "<a href = '"+filePath.replace("'","&#39;")+"' target='result'>"
            +"<FONT COLOR='#0000ff' size = 4><b>"+filePath
            +"</a>&nbsp;<a href=\"#top\">[Top]</a>&nbsp;|&nbsp;<a href=\"#bottom\">[Bottom]</a><br></b></FONT>"
                +" <button>Pop-Up</button>\n" +
                "   <div class='container-"+filePath+"' style=\"display: none;width: 6rem;padding: 0.5rem;"
                +   "margin: 0.5rem 0.8rem; box-shadow: 0 0 5px rgb(160, 158, 158);background-color: green;"
                +"color: white;text-align: center;border-radius: 5px;\">\n" +
                "      <img src ='"+filePath.replace("'","&#39;")+"'>\n" +
                "   </div>\n" +
                "   <script>\n" +
                "      $('button').mouseover(() => {\n" +
                "         $('.container-"+filePath+"').css(\"display\", \"block\")\n" +
                "      })\n" +
                "      $('button').mouseout(() => {\n" +
                "         $('.container-"+filePath+"').css(\"display\", \"none\")\n" +
                "      })\n" +
                "   </script>";

        return fileEntry;

    }

    public static String getFileEntry(String filePath, List<String> keyWords){

        String keywordString = "<b>MATCHES KEYWORDS</b><br>"+String.join(", <br>", keyWords);

        String fileEntry = "<div class=\"tooltip\"><a href = '"+filePath.replace("'","&#39;")
                +"' target='result'><FONT COLOR='#0000ff' size = 4><b>"+filePath+"</b></a>"
                +"<span class=\"tooltiptext\">"+keywordString+"</span></div>"
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

        List<String> header = DisplayHelper2.getHeaderHtml(requestObject, resultContentObject);
        results.addAll(0,header);

        List<String> footer = DisplayHelper2.getFooterHtml(requestObject, resultContentObject);
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

                "<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js'"+
                "integrity='sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ=='"+
                "crossorigin='anonymous' referrerpolicy='no-referrer'> </script>"+

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
