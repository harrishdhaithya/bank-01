package model;

import java.util.UUID;

public class Admin {
	private UUID empid;
	private String name;
	private String phone;
	private String email;
	private String password;
	public Admin(String name, String phone, String email, String password) {
		this.empid = UUID.randomUUID();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}
	public UUID getEmpid() {
		return empid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public boolean evalPassword(String password) {
		return this.password.equals(password);
	}
	@Override
	public String toString() {
		return "Employee Id: "+empid+"\n"+
				"Name: "+name+"\n"+
				"Phone: "+phone+"\n"+
				"Email: "+email; 
	}
	
}
