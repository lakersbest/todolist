package it.simoli.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoRow {
	
	private String task;
	private boolean checked;
	private String creationDate;
	
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
	
}