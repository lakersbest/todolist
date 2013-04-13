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
	private ToDoRow entity = null;

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
		entity = getIntent().getExtras().getParcelable(MainActivity.BUNDLE_KEY);
		
		// Set text to the entity text
		myEditText.setText(entity.getTask());		
	}

	private OnClickListener cancelListener = new OnClickListener() {

		public void onClick(View v) {

			Log.v(TAG, "Cancel button clicked!");
			
			// The action was canceled
			setResult(RESULT_CANCELED); // code: 0
			
      		// Kill this activity!
			finish();
		}
	};
	
	private OnClickListener updateListener = new OnClickListener() {

		public void onClick(View v) {

			Log.v(TAG, "Update button clicked!");

			// Read the modified text
			String modifiedText = myEditText.getText().toString();
			
			// Set the new text
			entity.setTask(modifiedText);
			
			// Put the row into the existing bundle
//			Bundle bundle = new Bundle();
			Intent intent = getIntent();
			intent.getExtras().putParcelable(MainActivity.BUNDLE_KEY, entity);
	//		intent.putExtra(MainActivity.BUNDLE_EDIT_KEY, modifiedText);
			
			// Return OK result
			setResult(RESULT_OK, intent); // code: -1
			
      		// Kill this activity!
			finish();
		}
	};
}
