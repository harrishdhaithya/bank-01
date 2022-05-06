package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import model.Admin;
import model.User;
//6b5894bf-cce4-46fa-96c2-cbc9ea0abd16
public class Bank {
	private static ArrayList<User> users = new ArrayList<>();
	private static ArrayList<Admin> admins = new ArrayList<>();
	public static HashMap<Admin, String> adminSecrets = new HashMap<>();
	public static HashMap<User, String> userSecrets = new HashMap<>();
	public static User loggedInUser=null;
	public static Admin loggedInAdmin=null;
	static {
		Admin a1 = new Admin("Harrish", "1234567891", "harrishdhaithya@gmail.com", "Harr1234");
		admins.add(a1);
		adminSecrets.put(a1, "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK");
		User u1 = new User("Harrish", "Dhaithya", "9994695787", "harrishdhaithya2001@gmail.com", "1234", 5000);
		users.add(u1);
		userSecrets.put(u1, "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK");
		User u2 = new User("Tharun", "Kumar", "123456789", "harrish.1902037@srit.org", "1234", 8000);
		users.add(u2);
		userSecrets.put(u2, "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK");
	}
	public static boolean signup(String fname, String lname, String phone, String email, String password,double bal) {
		for(User u:users) {
			if(u.getPhone().equals(phone)||u.getEmail().equals(email)) {
				System.out.println("Email and Phone Number Should be unique");
				return false;
			}
		}
		if(phone.strip().length()!=10) {
			return false;
		}
		if(Otp.evalOtp(email)) {
			User user = new User(fname,lname,phone,email,password,bal);
			users.add(user);
			String tfa = TwoFAAuth.generateSecretKey();
			System.out.println("Your Secret key for Two Factor Authentication: "+tfa);
			userSecrets.put(user, tfa);
			return true;
		}
		return false;
	}
	public static ArrayList<User> getAllAccount(){
		if(loggedInAdmin==null) {
			return null;
		}
		return users;
	}
	public static User getAccDetails(String userid) {
		if(loggedInUser==null&&loggedInAdmin==null) {
			return null;
		}
		for(User u:users) {
//			System.out.println(u.getAccno()+" "+userid);
			if(u.getAccno().toString().equals(userid)) {
				return u;
			}
		}
		return null;
	}
	public static boolean deposit(UUID userid,double amount) {
		if(loggedInUser==null) {
			return false;
		}
		for(User u:users) {
			if(u.getAccno()==userid) {
				double newbal = u.getBalance()+amount;
				u.setBalance(newbal);
				return true;
			}
		}
		return false;
	}
	public static boolean withdraw(UUID userid,double amount) {
		if(loggedInUser==null) {
			return false;
		}
		for(User u:users) {
			if(u.getAccno()==userid) {
				if(amount<u.getBalance()) {
					double newbal = u.getBalance()-amount;
					u.setBalance(newbal);
					return true;
				}
				return false;
			}
		}
		return false;
	}
	public static boolean userlogin(String email,String password) {
		for(User u:users) {
			if(u.getEmail().equals(email.strip())) {
				if(u.evalPassword(password)) {
					if(TwoFAAuth.twoFA(u)) {
						loggedInUser=u;
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	public static boolean adminLogin(String email,String password) {
			for(Admin a:admins) {
//				System.out.println(a.getEmail()+" "+email);
				if(a.getEmail().equals(email.strip())) {
					if(a.evalPassword(password)) {
						System.out.println("Wait for OTP...");
						if(TwoFAAuth.twoFA(a)) {
							loggedInAdmin = a;
							return true;
						}
					}
				}
			}
		return false;
	}
	public static boolean makeTransaction(User from,String accno,double amount) {
		if(loggedInUser==null) {
			System.out.println("You are not Authenticated properly");
			return false;
		}
		if(from.getBalance()<amount) {
			System.out.println("Insufficient Balance...");
			return false;
		}
		if(accno.equals(from.getAccno().toString())) {
			System.out.println("Transaction cannot be done in same account");;
			return false;
		}
		for(User u:users) {
			if(u.getAccno().toString().equals(accno)) {
				double fromBal = from.getBalance()-amount;
				double toBal = u.getBalance()+amount;
				from.setBalance(fromBal);
				u.setBalance(toBal);
				return true;
			}
		}
		return false;
	}
	public static void userLogout() {
		loggedInUser=null;
	}
	public static void adminLogout() {
		loggedInAdmin=null;
	}
}
