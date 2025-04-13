package com.group24;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

    /**
 * Loads level array from levels.json.
 */
public class LoadLevel {
    /**
 * Loads level array from levels.json.
 * @param level The level to load
 * @return An int 2D array
 */

    public int[][] loadLevel(int level) {
        int M = Constants.MAP_ROWS;
        int N = Constants.MAP_COLUMNS;
        InputStream levelsPath = getClass().getClassLoader().getResourceAsStream("levels.json");

    
        try (InputStreamReader levelReader = new InputStreamReader(levelsPath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(levelReader, JsonObject.class);
            String levelNum = String.valueOf(level); // Adjusted to match JSON key format
            JsonArray jsonArray = jsonObject.getAsJsonArray(levelNum);

            if (jsonArray == null) {
                System.err.println("Level not found: " + levelNum);
                // Returns empty array if error
                return new int[M][N];
            }

            int[][] levelData = new int[M][N];
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonArray row = jsonArray.get(i).getAsJsonArray();
                for (int j = 0; j < row.size(); j++) {
                    levelData[i][j] = row.get(j).getAsInt();
                }
            }

            return levelData;

        } catch (FileNotFoundException e) {
            System.err.println("JSON file not found");
        } catch (IOException e) {
            System.err.println("Error reading file");
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing JSON");
        }

        // Return an empty array in case of an error
        return new int[M][N];
    }
}