package dev.anime.rpg.base.data;

/** Implement on an class that needs to be saved. **/
public interface ISaveable {
	
	/** Whether this data is linked to a save or not. **/
	public boolean isGlobal();
	
	/** Returns the actual files name. **/
	public String getFileName();
	
	/** Returns a relative path only containing folder names. **/
	public String getFileDir();
	
	/** Linked directly to getSaveData(). This determines the name of the saved data in the JSON. **/
	public String[] getSaveTags();
	
	/** Should return the data you want this object to save. **/
	public Object[] getSaveData();
	
	/** Warning Has null if not loaded. **/
	public void setDataFromLoad(Object[] newData);
	
}
