package org.filesearch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.filesearch.common.UIConfigInterface;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoUIConfigDyn implements UIConfigInterface {

    // Default workspace fields (fallback defaults)
    private List<String> exclusionList = Arrays.asList("windows", "system", "programdata", ".m2", ".m2_bu", ".m2_bu2", "target", "biosite", "outputs_changeCombo_perms", "afrocastwebsite", "biositefrontend", "biositebackend", "oem", "veolia", "NascenceProj", "scala", "dell");
    private String[] extentionsToExclude = {"windows", ".zip", ".lnk"};
    private String[] txtFileTypes = {".java", ".xml", ".properties", ".txt", ".json", ".sql"};
    private String root = "C:\\Users\\ArshadMayet\\Desktop";
    private KeyWordItem[] qrys = { new KeyWordItem(".java", true) };
    private KeyWordItem[] keyWordItems = { new KeyWordItem("IED-8906", true) };
    private boolean inclFileNames = false;
    private boolean inclTxtFileContent = true;
    private String searchType = "OR";
    private boolean noCase = true;

    // Track the directory path where the output file must be saved
    private String outputDir = "src/main/resources/";

    // Standard fallback constructor
    public AutoUIConfigDyn() {}

    // Dynamic configuration constructor powered by Jackson
    public AutoUIConfigDyn(String configPath) {
        if (configPath == null || configPath.trim().isEmpty()) return;

        try {
            File configFile = new File(configPath);
            if (!configFile.exists()) {
                System.out.println("Target JSON configuration file not found. Running defaults.");
                return;
            }

            // 1. Capture the input configuration file's directory path
            String parentPath = configFile.getAbsoluteFile().getParent();
            if (parentPath != null) {
                this.outputDir = parentPath + File.separator;
            }

            // 2. Parse JSON fields using standard Jackson object tree
            byte[] jsonData = Files.readAllBytes(Paths.get(configPath));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonData);

            System.out.println("Successfully parsed dynamic JSON target configuration.");

            if (rootNode.has("root")) this.root = rootNode.get("root").asText();
            if (rootNode.has("searchType")) this.searchType = rootNode.get("searchType").asText();
            if (rootNode.has("inclFileNames")) this.inclFileNames = rootNode.get("inclFileNames").asBoolean();
            if (rootNode.has("inclTxtFileContent")) this.inclTxtFileContent = rootNode.get("inclTxtFileContent").asBoolean();
            if (rootNode.has("noCase")) this.noCase = rootNode.get("noCase").asBoolean();

            if (rootNode.has("txtFileTypes") && rootNode.get("txtFileTypes").isArray()) {
                List<String> types = new ArrayList<>();
                for (JsonNode node : rootNode.get("txtFileTypes")) {
                    types.add(node.asText());
                }
                this.txtFileTypes = types.toArray(new String[0]);
            }

            if (rootNode.has("extentionsToExclude") && rootNode.get("extentionsToExclude").isArray()) {
                List<String> ext = new ArrayList<>();
                for (JsonNode node : rootNode.get("extentionsToExclude")) {
                    ext.add(node.asText());
                }
                this.extentionsToExclude = ext.toArray(new String[0]);
            }

            if (rootNode.has("exclusionList") && rootNode.get("exclusionList").isArray()) {
                List<String> exclusions = new ArrayList<>();
                for (JsonNode node : rootNode.get("exclusionList")) {
                    exclusions.add(node.asText());
                }
                this.exclusionList = exclusions;
            }

            if (rootNode.has("keyWordItems") && rootNode.get("keyWordItems").isArray()) {
                List<KeyWordItem> items = new ArrayList<>();
                for (JsonNode node : rootNode.get("keyWordItems")) {
                    String value = node.isObject() && node.has("value") ? node.get("value").asText() : node.asText();
                    if (!value.isEmpty()) {
                        items.add(new KeyWordItem(value, true));
                    }
                }
                if (!items.isEmpty()) {
                    this.keyWordItems = items.toArray(new KeyWordItem[0]);
                }
            }

        } catch (Exception e) {
            System.err.println("Jackson parsing exception, falling back to static defaults: " + e.getMessage());
        }
    }

    @Override
    public void executeConfig() {
        System.out.println("Running . . .");

        String criteriaFileNameDesc = AutoUIConfigUtilities.getCriteriaFileNameDesc(root, inclFileNames, qrys, searchType);
        String criteriaFileContentDesc = AutoUIConfigUtilities.getCriteriaFileContentDesc(root, inclTxtFileContent, keyWordItems, txtFileTypes, searchType);
        String criteriaDesc = criteriaFileNameDesc + criteriaFileContentDesc;
        String queryDescription = "Search " + criteriaDesc;

        IRequestObject requestObject = RequestObject.builder()
                .dir(root)
                .qrys(qrys)
                .fileContentKeyWords(keyWordItems)
                .inclTxtFileContent(inclTxtFileContent)
                .txtFileTypes(txtFileTypes)
                .exclusionList(exclusionList)
                .inclFileNames(inclFileNames)
                .extentionsToExclude(extentionsToExclude)
                .queryDescription(queryDescription)
                .noCase(noCase)
                .build();

        ISearchService fileSearchService = new FileSearchServiceMT2();
        IResultContentObject resultContentObject = fileSearchService.listAllDirectoryFiles(
                requestObject,
                (result, isContent) -> {
                    if (isContent) {
                        File file = new File(result);
                        SearchHelper search = new SearchHelper();
                        List<String> keyWords = search.getEntryKeysWordHis(file, keyWordItems);
                        return DisplayHelper.getFileEntry(result, keyWords);
                    } else {
                        return DisplayHelper.getFileEntry(result);
                    }
                },
                (file, contents, nocase, keywordItems) -> {
                    if (searchType.equals("OR")) {
                        return AutoUIConfigUtilities.predicateOR(file, contents, keywordItems, nocase);
                    } else if (searchType.equals("AND")) {
                        return AutoUIConfigUtilities.predicateAND(file, contents, keywordItems, nocase);
                    } else {
                        return AutoUIConfigUtilities.predicateDYN(file, contents, keywordItems, nocase);
                    }
                });

        String filename = root.substring(0, 1) + "_" + criteriaDesc.replace(" ", "_");
        filename = AutoUIConfigUtilities.sanitizeFilename(filename);

        // 3. Combined file output string using the mapped context folder variable
        String filePath = this.outputDir + "searchResultMT_wrk_" + filename + "mod.html";

        List<String> resultsInHtml = DisplayHelper.displayResultsInHtml(requestObject, resultContentObject);
        FileSearchIO.writeToFile(resultsInHtml, filePath);

        System.out.println("Search results written to file : " + filePath);
        System.out.println("Time taken for search : " + resultContentObject.getTimeElapsedMinutes() + " mins (" + resultContentObject.getTimeElapsedSeconds() + " secs)");
        System.out.println("Number of objects found : " + resultContentObject.getResultContents().size() + "  Hits Found : " + resultContentObject.getHitCount());
        System.out.println("Number of objects searched through : " + resultContentObject.getFileCount());
    }
}