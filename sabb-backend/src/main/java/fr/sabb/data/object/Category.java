package fr.sabb.data.object;

public class Category extends SabbObject {
	
	private int id;
	private String name;
	private boolean autobind;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
	/**
	 * @return the autobind
	 */
	public boolean isAutobind() {
		return autobind;
	}
	/**
	 * @param autobind the autobind to set
	 */
	public void setAutobind(boolean autobind) {
		this.autobind = autobind;
	}
	
}
