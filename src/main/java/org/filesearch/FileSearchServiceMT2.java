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

    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.OFF);
    }

    private final List<String> results = new ArrayList<>(); // Use synchronized list
    private long hitCount = 0;
    public static final ThreadLocal<KeyWordItem[]> CURRENT_THREAD_KEYWORDS = new ThreadLocal<>();


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

        while(!stack.isEmpty() && fileOrDirExist){
            File file = stack.pop();

           // if(searchHelper.toSearchFileByName(file, requestObject.getExclusionList(), requestObject.getQrys(), requestObject.isInclFileNames())) {
           if( searchHelper.toSearchFileByName(file, requestObject.getExclusionList(), requestObject.isInclFileNames()) &&
               //queryBuilder.buildPredicate(file.getAbsolutePath(), requestObject.getQrys())) {
               queryBuilder.buildPredicate(file,file.getAbsolutePath(), requestObject.isNoCase(), requestObject.getQrys())) {

                System.out.println("======>["+file.getAbsolutePath()+"]");

                synchronized (results) {
//                String fileEntry = DisplayHelper.getFileEntry(file, requestObject.getQrys());
//                results.add(fileEntry);

//                results.add(file.getAbsolutePath());
                  try {
                    CURRENT_THREAD_KEYWORDS.set(requestObject.getFileContentKeyWords());
                    results.add(entryFormater.modify(file.getAbsolutePath(), false));
                  } finally {
                    CURRENT_THREAD_KEYWORDS.remove();
                  }

                  hitCount++;
                }

            } else if(searchHelper.toSearchTxtFileContents(file, requestObject.getTxtFileTypes(),
                   // localToInclTxtFileContent, requestObject.getExclusionList())) {
                   requestObject.isInclTxtFileContent(), requestObject.getExclusionList())) {

                /* Create a thread to search file contents  - same code for virtual threads */
               // 1. Get the shared keywords array
               KeyWordItem[] sharedKeywords = requestObject.getFileContentKeyWords();

                // 2. Create a deep copy clone specifically for this worker thread's scope
               KeyWordItem[] threadSafeKeywords = new KeyWordItem[sharedKeywords.length];
               for (int i = 0; i < sharedKeywords.length; i++) {
                   threadSafeKeywords[i] = new KeyWordItem(sharedKeywords[i]);
               }
                Runnable worker = new SearchTxtThread(
                        file,
                        threadSafeKeywords,
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
        try {
            // Let the main thread sleep cleanly instead of pegging a CPU core at 100%
            if (!executor.awaitTermination(1, java.util.concurrent.TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

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
                CURRENT_THREAD_KEYWORDS.set(this.fileContentKeyWords);
                processTxtFileContentSearch();
            }catch ( AccessDeniedException ade) {
                System.out.println("no access permission for file " + file.getName());
            }catch (OutOfMemoryError oom) {
                oom.printStackTrace();
            } catch(RuntimeException re) {
                // Safe check for null messages to prevent a secondary NullPointerException
                String msg = re.getMessage() != null ? re.getMessage().toLowerCase() : "";
                if ((re.getCause() != null && re.getCause() instanceof org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException)
                        || msg.contains("password") || msg.contains("decrypt")) {
                    System.out.println("Skipping encrypted/password-protected file: " + file.getAbsolutePath());
                } else {
                    // Log it cleanly instead of throwing, keeping the pool thread alive
                    System.err.println("Runtime exception parsing file " + file.getName() + ": " + re.getMessage());
                }
            } catch (IOException e) {
                // Safe check for null messages
                String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
                if (msg.contains("password") || msg.contains("decrypt")) {
                    System.out.println("Skipping encrypted/password-protected file: " + file.getAbsolutePath());
                } else {
                    // Log it cleanly instead of re-throwing a wrapped RuntimeException
                    System.err.println("IO Error reading file " + file.getName() + ": " + e.getMessage());
                }
            } finally {
            // Always clean up the thread context to avoid memory leaks
            CURRENT_THREAD_KEYWORDS.remove();
         }
        }

        private void processTxtFileContentSearch() throws OutOfMemoryError, IOException {

            if(!file.canExecute() || !file.canRead()) return;

            String fileContent = "";
            if(file.getName().toLowerCase().endsWith(".pdf")){
                try {
                    // Fix the performance bug: Parse only ONCE directly to a string
                    fileContent = PdfUtil.pdfToString(file.getAbsoluteFile());
                    if (fileContent == null) fileContent = "";
                } catch (Exception e) {
                    // Trap the EOF and corruption crashes safely here
                    System.err.println("Skipping invalid or corrupted PDF: " + file.getAbsolutePath() + " (" + e.getMessage() + ")");
                    return; // Gracefully drop out of this thread worker
                }
            } else {
                byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                fileContent = new String(bytes);
            }

            // --- ADD THIS MATCH LOGIC --- to fix keyword tool tip bug
            if (fileContent != null && !fileContent.isEmpty()) {
                String searchContent = isNoCase ? fileContent.toLowerCase() : fileContent;
                for (KeyWordItem item : fileContentKeyWords) {
                    String target = isNoCase ? item.getKeyWord().toLowerCase() : item.getKeyWord();
                    if (searchContent.contains(target)) {
                        item.setFound(true); // Mark match on this thread's cloned item
                    }
                }
            }
            // ----------------------------

            if(queryBuilder.buildPredicate(file,fileContent, isNoCase, fileContentKeyWords)) {

                System.out.println("KeyWordItem in file contents ======>["+file.getAbsolutePath()+"]");

                synchronized (results) {
//                String fileEntry = DisplayHelper.getFileEntry(file);
//                results.add(fileEntry);

//                results.add(file.getAbsolutePath());
                  results.add(entryPresenter.modify(file.getAbsolutePath(), true));
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

