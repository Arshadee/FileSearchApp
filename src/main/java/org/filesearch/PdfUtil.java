package org.filesearch;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfUtil {
    
    public static String pdfToString(File file) throws IOException{
        String parsedText = "";
        //try {
            PDFParser parser;
            parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            if (cosDoc != null)  cosDoc.close();
            if (pdDoc != null)  pdDoc.close();
     //   }catch (IOException ioe){
      //      ioe.printStackTrace();
      //  }
        return parsedText;
    }

    public static void main (String[] args) throws IOException {
        String filename = "c:\\Users\\ArshadMayet\\Downloads\\Nasif-Mauthoor-Resume.pdf";
        File f = new File(filename);
        String parsedText;
        PDFParser parser;
        parser = new PDFParser(new RandomAccessFile(f, "r"));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);
        System.out.println(parsedText);
        
    }
  

    
}
