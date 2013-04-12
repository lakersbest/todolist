package it.simoli.todolist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {

	private static final String TAG = "ItemView";
	private Context context = null;	
	private ToDoRow rowEntity = null;

	public ItemView(Context context, AttributeSet attr) {
		
		super(context, attr);
		this.context = context;
		
		// Inflating XML
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater;
		inflater = (LayoutInflater) getContext().getSystemService(infService);
		inflater.inflate(R.layout.item_view, this, true);
		
		CheckBox c = (CheckBox) findViewById(R.id.checked);
		c.setOnClickListener(checkBoxListener);
		
		this.setOnLongClickListener(longClickListener);
	}	
	
	public ToDoRow getRowEntity() {
		return rowEntity;
	}
	
	public void loadEntity(ToDoRow entity) {
		
		rowEntity = entity;
		
		final TextView text = (TextView) findViewById(R.id.task);
		final CheckBox check = (CheckBox) findViewById(R.id.checked);
		final TextView date = (TextView) findViewById(R.id.creationdate);
		
		text.setText(rowEntity.getTask());
		check.setChecked(rowEntity.isChecked());
		date.setText(rowEntity.getCreationDate());
	}
	
	private OnClickListener checkBoxListener = new OnClickListener() {
		
	    public void onClick(View v) {
	    	
	        // do something when the check is clicked
		    Log.v(TAG, "CheckBox clicked!");
			rowEntity.setChecked(((CheckBox) v).isChecked());
	    }
	};
	
	private OnLongClickListener longClickListener = new OnLongClickListener() {
		
		public boolean onLongClick(View v) {
			
			// do something when the ItemView is longClicked
			Log.v(TAG, "ItemView has been longClicked!");
	        ItemViewDialogFragment dialog = new ItemViewDialogFragment(rowEntity);
	        dialog.show(((MainActivity) context).getSupportFragmentManager(), "dialog");
			
			return false;
		}
	};
}
