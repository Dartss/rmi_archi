package rmi_archi;

import java.util.HashMap;
import java.io.Serializable;

public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private boolean isStupid;
	private static int idCounter = 0;
	private int id;
	private HashMap<Long, String> relationships;
	
	public Person(String name, int age, boolean isStupid) {
		this.name = name;
		this.age = age;
		this.isStupid = isStupid;
		this.id = idCounter;
		this.relationships = new HashMap<Long, String>();
		idCounter ++;
	}
	
	public Person(){}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isStupid() {
		return isStupid;
	}

	public void setStupid(boolean isStupid) {
		this.isStupid = isStupid;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void addRelationship(String relationshipName, long targetId) {
		this.relationships.put(targetId, relationshipName);
	}
	
	public HashMap<Long, String> getRelationships(){
		return this.relationships;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", isStupid=" + isStupid + ", uid = " + id + "]";
	}
}
