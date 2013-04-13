package it.simoli.todolist;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;


/* JSON, or JavaScript Object Notation, is a text-based open
 * standard designed for human-readable data interchange.
 * This class provides an implementation of the IStorageManager
 * interface. */
public class JSONStorageManager implements IStorageManager {

	private static final String TAG = "JSONStorageManager";
	private static final String FILENAME = "todolist.json";
	private Context context = null;
	private ArrayList<ToDoRow> entities = null; 
	
	public JSONStorageManager(Context theContext, ArrayList<ToDoRow> theEntities) {
		context = theContext;
		entities = theEntities;
	}

	@Override
	public void saveData() {

		try {
			
			write();
			
		} catch (IOException e) {
			Log.e(TAG, "saveData() failed.");
			e.printStackTrace();
		}		
	}

	@Override
	public void loadData() {
		
		try {
			
			restore();
			
		} catch (FileNotFoundException e) {
			Log.e(TAG, "loadData() failed. File not found exception.");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "loadData() failed. IOException.");		
			e.printStackTrace();
		}
	}
	
	
	/* Returns the JSON representation of the dataList,
	 * ready to be stored anywhere. */
	private String serialize() throws IOException {

		JSONArray json = new JSONArray();

		try {

			for (Object r : entities ) {
				
				ToDoRow row = (ToDoRow) r;
				
				JSONObject o = new JSONObject();
				o.put("task", 			row.getTask());
				o.put("checked", 		row.isChecked());
				o.put("creationDate", 	row.getCreationDate());
				json.put(o);
			}

			Log.v(TAG + " writeJSON()", json.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/* Returns a JSON representation of the dataList,
	 * read from an external file. */
	private String deserialize() throws FileNotFoundException, IOException {

		// Constructs a new StringBuffer containing an empty String
		StringBuffer fileContent = new StringBuffer("");

		try {

			FileInputStream fis = context.openFileInput(FILENAME);

			byte[] buffer = new byte[1024];
			/*
			 * Reads a single byte from this stream and returns it as an integer
			 * in the range from 0 to 255. Returns -1 if the end of the stream
			 * has been reached. Blocks until one byte has been read, the end of
			 * the source stream is detected or an exception is thrown.
			 */
			while (fis.read(buffer) != -1) {
				fileContent.append(new String(buffer));
			}
			fis.close();

		} catch (IOException e) {
			// could be IOException or FileNotFoundException
			e.printStackTrace();
		}
		return fileContent.toString();
	}	
	
	/* Writes the JSON string in a file
	 * in the internal storage. */
	private void write() throws IOException {

		try {

			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(serialize().getBytes());
			fos.close();

		} catch (IOException e) {

			Log.e(TAG + " writeJSON()", e.getMessage());
		}
	}

	/* Deserializes the JSON string and 
	 * repopulates the dataList. */
	private void restore() throws FileNotFoundException, IOException {
		
		entities.clear();

		try {

			JSONArray arr = new JSONArray(deserialize());

			for (int i = 0; i < arr.length(); i++) {

				JSONObject o = arr.getJSONObject(i);

				String task 		= o.getString("task");
				boolean checked 	= o.getBoolean("checked");
				String creationDate = o.getString("creationDate"); 

				ToDoRow row = new ToDoRow(task, checked, creationDate);
				entities.add(row);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
	}
}
