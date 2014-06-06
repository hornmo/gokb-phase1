package com.k_int.gokb.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class GOKbService {
  public static final String SERVICE_DIR = "_gokb";
  
  private class ServiceSettings {
    
    private Map<String,JSONObject> cache = new HashMap<String,JSONObject> ();
    
    private File directory;
    private ServiceSettings(File parentDir) {
      
      // Set the settings directory within the supplied directory.
      directory = new File(parentDir, SERVICE_DIR + File.separatorChar);
      directory.mkdir();
    }
    
    /**
     * Get the settings for the name supplied. The name will be used both as a key for the cache,
     * and also for the filename when saving to disk.
     * 
     * @param url String URL of settings.
     * @return
     * @throws IOException 
     * @throws JSONException 
     */
    private JSONObject get(String url) throws JSONException, IOException {
      
      // Hash the URL for cache key.
      String key = DigestUtils.md5Hex(url);
      
      // Read from the cache.
      JSONObject settings = cache.get(key);

      // We now need to load from the disk.
      File jsonFile = new File(directory, key + ".json");
      
      // Not present in the cache.
      if (settings == null) {
        
        if (jsonFile.exists()) {
        
          // Read the file.
          settings = new JSONObject(
            FileUtils.readFileToString(jsonFile)
          );
        } else {
          
          // We create a new JSONObject with null data and save.
          settings = new JSONObject("{\"date\" : 0,\"data\" : {\"core\" : true}}");
          FileUtils.writeStringToFile(jsonFile, settings.toString());
        }
        
        // Store in the cache.
        cache.put(key, settings);
      }
      
      // Now we check if the service has an updated version.
      JSONObject update = getIfChanged (url, settings.getLong("date"));
      if (update != null) {
        // Then the server has responded with new data. We should use that instead.
        settings = update;
        
        // We should write the contents to the disk too.
        FileUtils.writeStringToFile(jsonFile, settings.toString());
        
        // Store in the cache.
        cache.put(key, settings);
      }
      
      // Only return the data element of the object.
      return settings.getJSONObject("data");
    }
    

    /**
     * Get the data from the web service if it has changed or just retrieve from the local storage.
     * @param url
     * @param since
     * @return
     * @throws IOException
     * @throws JSONException 
     */
    private JSONObject getIfChanged (String url, Long since) throws IOException, JSONException {
      return getIfChanged (url, since, null);
    }
    
    /**
     * Get the data from the web service if it has changed or just retrieve from the local storage.
     * 
     * @param url
     * @param since
     * @param etag
     * @return
     * @throws IOException
     * @throws JSONException 
     */
    private JSONObject getIfChanged (String url, Long since, String etag) throws IOException {
      
      // Get the URL.
      URL urlObj = new URL(url);
      
      HttpURLConnection connection = null;

      // Configure the connection properties.
      connection = (HttpURLConnection) urlObj.openConnection();
      connection.setDoOutput(true);
      connection.setConnectTimeout(GOKbModuleImpl.properties.getInt("timeout"));
      connection.setRequestProperty("Connection", "Keep-Alive");
      
      // Last modified date?
      if (since != null && since > 0) {
        connection.setIfModifiedSince(since);
      }

      // ETag compatibility
      if (etag != null) {
        connection.setRequestProperty("If-None-Match", etag);
      }
      
      try {
        // Connect to the service.
        connection.connect();
        
        // Get the response code.
        int code = connection.getResponseCode();
        
        // Check for '304 Not Modified' response code.
        if (code != 304 ) {
          
          // Try and read JSONObject form the server.
          BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          
          // The string builder.
          StringBuilder str = new StringBuilder();

          // Read into a string builder.
          String inputStr;
          while ((inputStr = in.readLine()) != null) {
            str.append(inputStr);
          }
          
          // Now create the JSON from the text.
          return new JSONObject ("{\"date\":" + connection.getLastModified() + ",\"data\": " + str.toString() + "}");
        }
      } catch (Exception e) {
        // Ignore and return null.
      }
      
      return null;
    }
  }
  private String URL;
  
  private ServiceSettings settings;

  private JSONObject capabilities;
  
  public JSONObject getCapabilities () {
    return capabilities;
  }

  public GOKbService (String URL, File directory) throws IOException, JSONException {
    this.URL = URL;
    this.settings = new ServiceSettings(directory);
    
    initialise();
  }
  
  /**
   * Get the settings for a particular named value.
   * @param name
   * @return
   * @throws JSONException
   * @throws IOException
   */
  private JSONObject getSettings(String name) throws IOException, JSONException {
    return settings.get( getURL() + name );
  }
  
  /**
   * @return The URL of the service.
   */
  public String getURL () {
    return URL;
  }
  
  /**
   * Initialise the service object using data from the server.
   * @throws IOException 
   * @throws JSONException 
   */
  private void initialise() throws IOException, JSONException {
    capabilities = getSettings("capabilities");
  }
  
  /**
   * Check if the user we are connected to the service as is allowed to perform the operation supplied.
   * 
   * @param of The operation name.
   * @return true if allowed
   */
  public boolean isAllowed(String to) {
    return isCabable (to);
  }

  /**
   * Check if the service we are connected to is capable of the operation supplied.
   * 
   * @param of The operation name.
   * @return true if capable
   * @throws JSONException 
   */
  public boolean isCabable(String of) {
    try {
      return capabilities.getBoolean(of) == true;
    } catch (JSONException e) {
      return false;
    }
  }
}