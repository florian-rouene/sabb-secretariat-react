package fr.sabb.data.object;

public abstract class SabbObject {

	public abstract int getId();
	
	public boolean isPersisted() {
		return getId() != 0;
	}
}
