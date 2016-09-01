package dev.anime.rpg.base.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SaveManager {
	
	/** The list of objects that need to be Saved. **/
	private static Map<String, ISaveable> SAVEABLE_OBJECTS = new HashMap<String, ISaveable>();
	
	/** The current save slot. (Save 1, Save 2, etc.) **/
	public static String CURRENT_SAVE_SLOT = "saves/save 1";
	
	private static final String SAVE_TYPE = ".json";
	
	/**
	 * @param save The instance of ISaveable to be added to the list.
	 * @return If it was not already in the {@link SAVEABLE_OBJECTS saves}.
	 */
	public static boolean addObjectToBeSaved(ISaveable save) {
		SAVEABLE_OBJECTS.put(save.getFileDir() + "/" + save.getFileName(), save);
		return true;
	}
	
	/** Saves {@link SAVEABLE_OBJECTS saves} to their files. **/
	public static void saveSaveables() {
		for (Entry<String, ISaveable> entry : SAVEABLE_OBJECTS.entrySet()) {
			save(entry.getValue());
		}
	}
	
	/** Saves the ISaveable to its path. **/
	public static void save(ISaveable save) {
		String dir = (save.isGlobal() ? "" : CURRENT_SAVE_SLOT + "/") + save.getFileDir();
		File folders = new File(dir);
		File json = new File(dir, save.getFileName() + SAVE_TYPE);
		try {
			if (folders.mkdirs()) {
				if (json.createNewFile()) {
					json.setReadable(true);
					json.setWritable(true);
				} else {
					if (json.delete()) {
						if (json.createNewFile()) {
							json.setReadable(true);
							json.setWritable(true);
						}
					}
				}
			} else {
				if (json.delete()) {
					if (json.createNewFile()) {
						json.setReadable(true);
						json.setWritable(true);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeDataToJSON((save.isGlobal() ? "" : CURRENT_SAVE_SLOT + "/") + save.getFileDir() + save.getFileName(), save.getSaveTags(), save.getSaveData());
	}
	
	/** If the specified path exists it loads the file otherwise it loops into {@link save(ISaveable)}. **/
	public static void load(ISaveable save) {
		if (new File((save.isGlobal() ? "" : CURRENT_SAVE_SLOT + "/") + save.getFileDir() + save.getFileName() + SAVE_TYPE).exists()) {
			save.setDataFromLoad(readDataFromJSON((save.isGlobal() ? "" : CURRENT_SAVE_SLOT + "/") + save.getFileDir() + save.getFileName() + SAVE_TYPE, save.getSaveTags()));
		} else {
			save(save);
		}
	}
	
	private static Object[] readDataFromJSON(String filePath, String[] tags) {
		JSONParser parser = new JSONParser();
		Object[] returns = new Object[tags.length];
		try {
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			for (int i = 0; i < tags.length; i++) {
				Object loadedObj = jsonObject.get(tags[i]);
				if (loadedObj instanceof JSONArray) {
					JSONArray loadedList = (JSONArray) loadedObj;
					returns[i] = loadedList.toArray();
				}else if (loadedObj instanceof Long) {
					Integer integer = ((Long) loadedObj).intValue();
					returns[i] = integer;
					continue;
				} else returns[i] = jsonObject.get(tags[i]);
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returns;
	}
	
	@SuppressWarnings("unchecked")
	private static void writeDataToJSON(String filePath, String[] tags, Object[] data) {
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < tags.length; i++) {
			if (data[i] instanceof Object[]) {
				Object[] arrayData = (Object[]) data[i];
				JSONArray list = new JSONArray();
				for (Object obj : arrayData) {
					if (obj instanceof Object[]) {
						JSONArray arrayWithinArray = new JSONArray();
						for (Object entObj : (Object[]) obj) {
							if (entObj instanceof Long) {
								arrayWithinArray.add(((Long)entObj).intValue());
							} else if (entObj instanceof Integer) {
								arrayWithinArray.add(((Integer)entObj).intValue());
							} else if (entObj instanceof String) {
								arrayWithinArray.add(((String)entObj));
							} else arrayWithinArray.add(entObj);
						}
						list.add(arrayWithinArray);
					} else list.add(obj);
				}
				jsonObject.put(tags[i], list);
			} else jsonObject.put(tags[i], data[i]);
		}
		try {
			FileWriter writer = new FileWriter(filePath + SAVE_TYPE);
			writer.write(jsonObject.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
