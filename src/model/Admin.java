package model;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.google.common.hash.Hashing;

public class Admin {
	private UUID empid;
	private String name;
	private String phone;
	private String email;
//	private String password;
	private String passwordHash;
	public Admin(String name, String phone, String email, String password) {
		this.empid = UUID.randomUUID();
		this.name = name;
		this.phone = phone;
		this.email = email;
//		this.password = password;
		this.passwordHash=getHash(password);
	}
	public Admin(UUID empid, String name, String phone, String email, String passwordHash) {
		this.empid = empid;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.passwordHash = passwordHash;
	}
	public static String getHash(String password) {
		String hash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
		return hash;
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
//	public String getPassword() {
//		return password;
//	}
	public boolean evalPassword(String password) {
		return this.passwordHash.equals(getHash(password));
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	@Override
	public String toString() {
		return "Employee Id: "+empid+"\n"+
				"Name: "+name+"\n"+
				"Phone: "+phone+"\n"+
				"Email: "+email; 
	}
	
}
