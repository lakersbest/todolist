package it.simoli.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements ItemViewDialogFragment.ItemViewDialogListener {

	public static final String BUNDLE_KEY = "0xPippo";
	private static final int REQUEST_CODE_EDIT_ROW = 1337;
	private static final String TAG = "MainActivity";
	private static ArrayList<ToDoRow> todoRows = null;	
	private static Context context = null;
	private MyAdapter adapter = null;
	private ListView myListView = null;
	private EditText myEditText = null;
	private Button myButton = null;
	private IStorageManager storageManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.activity_main);

		// Get references to UI widgets
		myListView = (ListView) findViewById(R.id.myListView);
		myEditText = (EditText) findViewById(R.id.myEditText);
		myButton   = (Button)   findViewById(R.id.myButton);

		// Create the array list of to do items
		todoRows = new ArrayList<ToDoRow>();
		
		// Create the adapter
		adapter = new MyAdapter(this, todoRows);

		// Bind the array adapter to the ListView
		myListView.setAdapter(adapter);

		// Bind the event handler to the button
		myButton.setOnClickListener(buttonListener);
		
		// Initialize the storage manager
		storageManager = new JSONStorageManager(this, todoRows);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		Log.v(TAG, "onDestroy called!");

		// Save data into the internal storage
		storageManager.saveData();
	}

	@Override
	protected void onPause() {

		super.onPause();
		Log.v(TAG, "onPause called!");
	}

	@Override
	protected void onResume() {

		super.onResume();
		Log.v(TAG, "onResume called!");

		// Restore data from internal storage
		storageManager.loadData();
	}

	@Override
	protected void onStop() {

		super.onStop();
		Log.v(TAG, "onStop called!");
		
		// Save data into the internal storage
		storageManager.saveData();
	}
	
	@Override
	public void onDialogEditClick(DialogFragment dialog) {

		Log.v(TAG, "onDialogEditClick");
		
		// get row entity
		ToDoRow row = ((ItemViewDialogFragment) dialog).getEntity();
		
		// start EditActivity
		editTask(row);
	}

	@Override
	public void onDialogDeleteClick(DialogFragment dialog) {

		Log.v(TAG, "onDialogDeleteClick");
		ToDoRow row = ((ItemViewDialogFragment) dialog).getEntity();
		
		// Delete!
		todoRows.remove(row);
		
		/* Notifies the attached observers that the 
		 * underlying data has been changed and any 
		 * View reflecting the data set should refresh itself. 
		 */
		adapter.notifyDataSetChanged();
		
		// Save data into the internal storage
		storageManager.saveData();
		
        // We display a toast to the user
		// informing him/her that we just deleted the task.
		String message = getResources().getString(R.string.task_deleted_successfully);
  		Util.showToast(context, message);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if (data.getExtras().containsKey(BUNDLE_KEY)) {
			
			// Get the row that was edited in the EditActivity
			ToDoRow editedRow = data.getExtras().getParcelable(BUNDLE_KEY);
			
			// FIXME is this the same row we passed to EditActivity?
			for (ToDoRow row : todoRows) {
			  Log.v(TAG, row.equals(editedRow) ? "YES!" : "no");
			}
			
			String editedTask = editedRow.getTask();
			
			if (Util.isNullOrEmpty(editedTask)) {
				
				// The edited row is no more valid.
				// We can delete it.
				todoRows.remove(editedRow); // FIXME doesn't work - maybe because the reference just changed.

	            // We display a toast to the user
				// informing him/her that we just deleted the task.
				String message = getResources().getString(R.string.task_deleted_successfully);
	      		Util.showToast(context, message);
				
			} else {

	            // A task was updated. Here we will just display
	            // a toast to the user.
				String message = getResources().getString(R.string.task_updated_successfully);
	      		Util.showToast(context, message);   
			}
			
			/* Notifies the attached observers that the 
			 * underlying data has been changed and any 
			 * View reflecting the data set should refresh itself. 
			 */
			adapter.notifyDataSetChanged();
			
			// Save data into the internal storage
			storageManager.saveData();	
		}
	}

	private OnClickListener buttonListener = new OnClickListener() {

		public void onClick(View v) {

			Log.v(TAG, "Button clicked!");
			addTask();
		}
	};

	private boolean addTask() {

		String text = myEditText.getText().toString().trim();

		if (Util.isNullOrEmpty(text)) {

			Util.showToast(context, getResources().getString(R.string.no_empty_task_allowed));
			return false;

		} else {
			
			createAndSaveTask(text);
			
			// Delete the old text
			myEditText.setText("");
			
			return true;
		}
	}
	
	private void createAndSaveTask(String text) {
		
		ToDoRow row = new ToDoRow(text);
		
		// Add row in the top
		int index = 0;
		todoRows.add(index, row);
		
		/* Notifies the attached observers that the 
		 * underlying data has been changed and any 
		 * View reflecting the data set should refresh itself. 
		 */
		adapter.notifyDataSetChanged();
		
		// Save data into the internal storage
		storageManager.saveData();
	}
	
	private void editTask(ToDoRow row) {

		Intent intent = new Intent(this, EditActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(BUNDLE_KEY, row);
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_CODE_EDIT_ROW);
	}
}
