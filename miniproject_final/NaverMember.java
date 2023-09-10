package miniproject_final;

import java.io.Serializable;

public class NaverMember implements Serializable{
	private String id;
	private String name;

	
	public NaverMember(String id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
		
	
	public String getID() {
		return id;
	}
	
	
}