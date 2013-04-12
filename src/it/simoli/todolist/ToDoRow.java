package it.simoli.todolist;

public class ToDoRow {
	
	private String task;
	private boolean checked;
	private String creationDate;
	
	/* Constructors */
	
	public ToDoRow(String aTask) {
		setTask(aTask);
		setChecked(false);
	}

	public ToDoRow(String aTask, boolean isChecked) {
		setTask(aTask);
		setChecked(isChecked);
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

	public void setCreationDate(String aCreationDate) {
		creationDate = aCreationDate;
	}
	
}