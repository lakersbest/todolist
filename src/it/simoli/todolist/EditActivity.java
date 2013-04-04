package it.simoli.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {

	private final String TAG = "EditActivity";	
	private ArrayList<ToDoRow> todoRows = null;	
	private Button cancelButton = null;
	private Button updateButton = null;
	private ToDoRow todoRow = null;
	private EditText myEditText = null;

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
		
		this.todoRow = MainActivity.getRowToEdit();
		myEditText.setText(todoRow.getTask());

		todoRows = MainActivity.getTodoRows();
	}

	private OnClickListener cancelListener = new OnClickListener() {

		public void onClick(View v) {

			Log.v(TAG, "Cancel clicked!");
			EditActivity.this.finish();
		}
	};
	
	private OnClickListener updateListener = new OnClickListener() {

		public void onClick(View v) {

			Log.v(TAG, "Update clicked!");
			
			// Update the entity
			todoRow.setTask(myEditText.getText().toString());
			
			EditActivity.this.finish();
		}
	};
}
