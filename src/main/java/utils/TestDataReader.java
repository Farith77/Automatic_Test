package utils;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TestDataReader {
    private static final String DATA_PATH = "src/test/resources/testdata/";
    
    public static JsonObject getTestData(String fileName, String testCase) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(DATA_PATH + fileName);
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            reader.close();
            return jsonObject.getAsJsonObject(testCase);
        } catch (IOException e) {
            throw new RuntimeException("Error reading test data: " + fileName, e);
        }
    }
    
    public static String getString(JsonObject data, String key) {
        return data.get(key).getAsString();
    }
    
    public static boolean getBoolean(JsonObject data, String key) {
        return data.get(key).getAsBoolean();
    }
}