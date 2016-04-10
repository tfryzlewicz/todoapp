package todoapp;

public class Tasks {
	private String date;
	private String task;
	public Tasks(String date, String task){
		this.date = date;
		this.task = task;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
}
