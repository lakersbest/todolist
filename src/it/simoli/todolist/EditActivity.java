package it.simoli.todolist;

import it.simoli.todolist.entity.ToDoRow;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {

	private final String TAG = "EditActivity";
	private EditText myEditText = null;	
	private Button cancelButton = null;
	private Button updateButton = null;
	private ToDoRow entity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		// Get references to UI
		updateButton = (Button) findViewById(R.id.updateButton);		
		cancelButton = (Button) findViewById(R.id.cancelButton);
		myEditText = (EditText) findViewById(R.id.myEditText2);
		
		// Set event listeners
		updateButton.setOnClickListener(updateListener);
		cancelButton.setOnClickListener(cancelListener);
		
		// Get current entity
		entity = getIntent().getExtras().getParcelable(MainActivity.BUNDLE_KEY);
		
		// Set text to the entity text
		myEditText.setText(entity.getTask());		
	}

	private OnClickListener cancelListener = new OnClickListener() {

		public void onClick(View v) {

			Log.v(TAG, "Cancel button clicked!");
			
			// The action was canceled
			setResult(RESULT_CANCELED, getIntent()); // code: 0

      		// Kill this activity!
			finish();
		}
	};
	
	private OnClickListener updateListener = new OnClickListener() {

		public void onClick(View v) {

			// Debugging...
			Log.v(TAG, "Update button clicked!");
			Log.v(TAG, "ENTITY (before setTask()): " + entity.toString());
			
			// Read the modified text
			String modifiedText = myEditText.getText().toString();
			
			// Set the new text
			entity.setTask(modifiedText);
			Log.v(TAG, "ENTITY (after setTask()): " + entity.toString());			
			
			// Put the entity into the existing bundle
			getIntent().getExtras().putParcelable(MainActivity.BUNDLE_KEY, entity);
			
			// Return OK
			setResult(RESULT_OK, getIntent()); // code: -1
			
      		// Kill this activity!
			finish();
		}
	};
}
