package helper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestDataLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode loadTree(String resourcePath) {
        try (InputStream in = TestDataLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) throw new RuntimeException(resourcePath + " not found in classpath");
            return MAPPER.readTree(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + resourcePath, e);
        }
    }

    public static String getTestData(JsonNode node, String key) {
        return node.path(key).asText();
    }

    public static String[] getTestDataArray(JsonNode node, String key) {
        JsonNode arrayNode = node.path(key);

        String[] data = new String[arrayNode.size()];

        for (int i = 0; i < arrayNode.size(); i++) {
            data[i] = arrayNode.get(i).asText();
        }

        return data;
    }

    public static String[][] getTestDataArrayOfArray(JsonNode node, String key) {
        JsonNode outerArray = node.path(key);

        String[][] data = new String[outerArray.size()][];

        for (int i = 0; i < outerArray.size(); i++) {
            JsonNode innerArray = outerArray.get(i);

            data[i] = new String[innerArray.size()];

            for (int j = 0; j < innerArray.size(); j++) {
                data[i][j] = innerArray.get(j).asText();
            }
        }

        return data;
    }

    public static String getImgPathFromResource(String classpathPath) {
        URL url = Thread.currentThread()
                .getContextClassLoader()
                .getResource(classpathPath);

        if (url == null) {
            throw new RuntimeException("Resource not found: " + classpathPath);
        }

        try {
            Path p = Paths.get(url.toURI());
            return p.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve resource: " + classpathPath, e);
        }
    }
}
