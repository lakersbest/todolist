package it.simoli.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDoRow implements Parcelable {
	
	private String task;
	private boolean checked;
	private String creationDate;
	
    public static final Parcelable.Creator<ToDoRow> CREATOR
	  = new Parcelable.Creator<ToDoRow>() {
    	
		public ToDoRow createFromParcel(Parcel in) {
		    return new ToDoRow(in);
		}
		
		public ToDoRow[] newArray(int size) {
		    return new ToDoRow[size];
		}
	};

	
	/* Constructors */
	
	public ToDoRow(String aTask) {
		setTask(aTask);
		setChecked(false);
		setCreationDate(getCurrentDate());
	}

	public ToDoRow(String aTask, boolean isChecked) {
		setTask(aTask);
		setChecked(isChecked);
		setCreationDate(getCurrentDate());
	}
	
	public ToDoRow(String aTask, boolean isChecked, String createdAt) {
		setTask(aTask);
		setChecked(isChecked);		
		setCreationDate(createdAt);
	}

	private ToDoRow(Parcel in) {
        task = in.readString();
		// I did this because Parcel 
		// doesn't implement a writeBoolean() method.
        checked = in.readByte() == 1;
        creationDate = in.readString();
    }
	
	/* getters */
	
	public String getTask() {
		return task;
	}

	public boolean isChecked() {
		return checked;
	}

	public String getCreationDate() {
		return creationDate;
	}	
	
	/* setters */
	
	public void setChecked(boolean isChecked) {
		checked = isChecked;
	}
	
	public void setTask(String aTask) {
		task = aTask;
	}

	public void setCreationDate(String createdAt) {
		creationDate = createdAt;
	}
	
	/* private methods */
	
	private String getCurrentDate() {
		// TODO localization
		String italianDateFormat = "dd/MM/yyyy HH:mm";
		SimpleDateFormat dateFormat = new SimpleDateFormat(italianDateFormat);
		Date date = new Date();
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}
    
	/************************/
	/* Parcelable's methods */
	/************************/
	
	/**
	 * Describe the kinds of special objects contained in this Parcelable's marshalled representation.
	 */
	@Override
	public int describeContents() {
		
		return 0;
	}

	/** 
	 * Flatten this object in to a Parcel. 
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {

		out.writeString(task);
		// I did this because Parcel 
		// doesn't implement a writeBoolean() method.
		out.writeByte((byte) (checked ? 1 : 0));
		out.writeString(creationDate);
	}
	
}