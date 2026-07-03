package org.filesearch;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonConfigParser {

    private final String json;

    public JsonConfigParser(String json) {
        this.json = json;
    }

    public String getString(String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1).replace("\\\\", "\\") : "";
    }

    public boolean getBoolean(String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*(true|false)");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() && Boolean.parseBoolean(matcher.group(1));
    }

    public String[] getArray(String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\\[([^\\]]*)\\]");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            String raw = matcher.group(1).trim();
            if (raw.isEmpty()) return new String[0];
            String[] split = raw.split(",");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].replace("\"", "").trim();
            }
            return split;
        }
        return new String[0];
    }

    public KeyWordItem[] getKeyWordItems(String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\\[([^\\]]*)\\]");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) return new KeyWordItem[0];

        String inner = matcher.group(1).trim();
        if (inner.isEmpty()) return new KeyWordItem[0];

        List<KeyWordItem> items = new ArrayList<>();
        Pattern objectPattern = Pattern.compile("\\{\\s*\"keyWord\"\\s*:\\s*\"([^\"]*)\"\\s*,\\s*\"include\"\\s*:\\s*(true|false)\\s*\\}");
        Matcher objMatcher = objectPattern.matcher(inner);

        while (objMatcher.find()) {
            items.add(new KeyWordItem(objMatcher.group(1), Boolean.parseBoolean(objMatcher.group(2))));
        }
        return items.toArray(new KeyWordItem[0]);
    }
}
