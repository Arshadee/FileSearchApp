package org.filesearch;


import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FileSearchServiceExp - service class to search files and file contents
 * The main search component of the application.
 * Is abstracted and contains just the search logic.
 *  - Search Algorithm and multi-threading
 *  Used for testing count of files scanned
 */
public class FileSearchServiceMT2 implements ISearchService {

    private final List<String> results = new ArrayList<>(); // Use synchronized list
    private long hitCount = 0;


    private ExecutorService executor = Executors.newCachedThreadPool(); // rem - to use virtual threads

    // DFS - Iterative Implementation
    @Override
    public  IResultContentObject  listAllDirectoryFiles(
            IRequestObject requestObject,
            IFormatString entryFormater,
            IQueryBuilder queryBuilder) {

        SearchHelper searchHelper = new SearchHelper();

        //results = new ArrayList<>();

        long start = System.currentTimeMillis();
        Stack<File> stack = new Stack();
        stack.push(new File(requestObject.getDir()));
        boolean fileOrDirExist =  new File(requestObject.getDir()).exists();
        long fileCount=  (fileOrDirExist)? 1 : 0;

        boolean localToInclTxtFileContent = requestObject.getQrys().length==0 ? false : requestObject.isInclTxtFileContent();
        //boolean localToInclTxtFileContent = true; //REMOVE

        while(!stack.isEmpty() && fileOrDirExist){
            File file = stack.pop();

           // if(searchHelper.toSearchFileByName(file, requestObject.getExclusionList(), requestObject.getQrys(), requestObject.isInclFileNames())) {
           // if(true){ //REMOVE
           if( searchHelper.toSearchFileByName(file, requestObject.getExclusionList(), requestObject.isInclFileNames()) &&
               //queryBuilder.buildPredicate(file.getAbsolutePath(), requestObject.getQrys())) {
               queryBuilder.buildPredicate(file,file.getAbsolutePath(), requestObject.isNoCase(), requestObject.getQrys())) {

                System.out.println("======>["+file.getAbsolutePath()+"]");

                synchronized (results) {
//                String fileEntry = DisplayHelper.getFileEntry(file, requestObject.getQrys());
//                results.add(fileEntry);

//                results.add(file.getAbsolutePath());
                    results.add(entryFormater.modify(file.getAbsolutePath(),false));
                    hitCount++;
                }

            } else if(searchHelper.toSearchTxtFileContents(file, requestObject.getTxtFileTypes(),
                   // localToInclTxtFileContent, requestObject.getExclusionList())) {
                   requestObject.isInclTxtFileContent(), requestObject.getExclusionList())) {

                /* Create a thread to search file contents  - same code for virtual threads */
                Runnable worker = new SearchTxtThread(
                        file,
                        requestObject.getFileContentKeyWords(),
                        queryBuilder,
                        entryFormater,
                        requestObject.isNoCase());
                executor.submit(worker);
            }

            Optional<List<File>> children = Optional.of(new ArrayList<>());
            if (searchHelper.addChildListCondition(file, requestObject.getExtentionsToExclude())){
                File[] files = file.listFiles();
                if(files!=null) {
                    children = Optional.of(Arrays.asList(files));
                }
            }

            for(int i = children.get().size()-1; searchHelper.isValidChild(file, requestObject.getExclusionList()) &&  i >=0; i--) {
                File child = children.get().get(i);
                stack.push(child);

                synchronized (this) {
                   fileCount++;
                }
            }

        }

        // cached threads convergence & termination
        executor.shutdown();

        /*  removed new  - to use virtual threads  */
        while(!executor.isTerminated()){
        // to converge all threads
        }
        // end cached threads convergence & termination

        long end = System.currentTimeMillis();
        Collections.sort(results);
        //long end = System.currentTimeMillis();
        ResultContentObject resultContentObject = new ResultContentObject(results, hitCount, start, end, fileCount);

        return resultContentObject;
    }

    class SearchTxtThread implements Runnable{

        private File file;
        private KeyWordItem[] fileContentKeyWords;
        private IQueryBuilder queryBuilder;
        private IFormatString entryPresenter;
        private boolean isNoCase;

        public SearchTxtThread(
                File file,
                KeyWordItem[] fileContentKeyWords,
                IQueryBuilder queryBuilder,
                IFormatString entryPresenter,
                boolean isNoCase){

            this.file = file;
            this.fileContentKeyWords = fileContentKeyWords;
            this.queryBuilder = queryBuilder;
            this.entryPresenter = entryPresenter;
            this.isNoCase = isNoCase;
        }

        @Override
        public void run() {
            try {
                processTxtFileContentSearch();
            }catch ( AccessDeniedException ade) {
                System.out.println("no access permission for file " + file.getName());
            }catch (OutOfMemoryError oom) {
                oom.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void processTxtFileContentSearch() throws OutOfMemoryError, IOException {

            if(!file.canExecute() || !file.canRead()) return;

            byte[] bytes;
            if(file.getName().toLowerCase().endsWith(".pdf")){
                PdfUtil.pdfToString(file.getAbsoluteFile());
                bytes = PdfUtil.pdfToString(file.getAbsoluteFile()).getBytes();
            }else {
                bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            }

            String fileContent = new String (bytes);

            //if(queryBuilder.buildPredicate(fileContent, fileContentKeyWords)) {
            if(queryBuilder.buildPredicate(file,fileContent, isNoCase, fileContentKeyWords)) {

                System.out.println("KeyWordItem in file contents ======>["+file.getAbsolutePath()+"]");

                synchronized (results) {
//                String fileEntry = DisplayHelper.getFileEntry(file);
//                results.add(fileEntry);

//                results.add(file.getAbsolutePath());
                    results.add(entryPresenter.modify(file.getAbsolutePath(),true));
                    hitCount++;
                }
            }
        }

        @Override
        public String toString(){
            return this.file.getName();
        }
    }
}

