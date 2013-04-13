package it.simoli.todolist.storage;

/* This interface provides an easy way
 * to store your entities.
 */
public interface IStorageManager {
	public void saveData();
	public void loadData();
}
