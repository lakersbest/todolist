package it.simoli.todolist;

public class ToDoRow {
	
	private String task;
	private boolean checked;
	
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
	
	/* setters */
	
	public void setChecked(boolean isChecked) {
		checked = isChecked;
	}
	
	public void setTask(String aTask) {
		task = aTask;
	}
	
}