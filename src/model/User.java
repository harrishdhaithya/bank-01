package model;

import java.util.UUID;

public class User {
	private UUID accno;
	private String fname;
	private String lname;
	private String phone;
	private String email;
	private String password;
	double balance;
	public User(String fname, String lname, String phone, String email, String password, double balance) {
		this.accno = UUID.randomUUID();
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.balance = balance;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UUID getAccno() {
		return accno;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean evalPassword(String password) {
		return this.password.equals(password);
	}
	@Override
	public String toString() {
		return "Account Number: "+accno+"\n"+
				"Name: "+fname+" "+lname+"\n"+
				"Email: "+email+"\n"+
				"Phone Number: "+phone+"\n"+
				"Balance: "+balance
				;
	}
}
