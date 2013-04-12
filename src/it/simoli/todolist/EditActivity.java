package it.simoli.todolist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {

	private final String TAG = "EditActivity";
	private static Context context = null;	
	private EditText myEditText = null;	
	private Button cancelButton = null;
	private Button updateButton = null;
	private ToDoRow todoRow = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.activity_edit);
		
		// Get references to UI
		updateButton = (Button) findViewById(R.id.updateButton);		
		cancelButton = (Button) findViewById(R.id.cancelButton);
		myEditText = (EditText) findViewById(R.id.myEditText2);
		
		// Set event listeners
		updateButton.setOnClickListener(updateListener);
		cancelButton.setOnClickListener(cancelListener);
		
		// Get current entity
//		todoRow = MainActivity.getRowToEdit();
		
		// Set text to the entity text
//		myEditText.setText(todoRow.getTask());
		
		Bundle bundle = this.getIntent().getExtras();
		bundle.setClassLoader(getClassLoader());
		this.todoRow = bundle.getParcelable(MainActivity.BUNDLE_EDIT_KEY);
	}

	private OnClickListener cancelListener = new OnClickListener() {

		public void onClick(View v) {

			Log.v(TAG, "Cancel button clicked!");
			
			// The action was canceled
			setResult(RESULT_CANCELED);
			
      		// Kill this activity!
			finish();
		}
	};
	
	private OnClickListener updateListener = new OnClickListener() {

		public void onClick(View v) {

			Log.v(TAG, "Update button clicked!");

			// Read the modified text
			String modifiedText = myEditText.getText().toString();
			
			// Put the new text back to the Bundle
			Intent intent = getIntent();
			intent.putExtra(MainActivity.BUNDLE_EDIT_KEY, modifiedText);
			
			// Return OK result
			setResult(RESULT_OK);
			
      		// Kill this activity!
			finish();
		}
	};
}
