package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import fileaccess.FileAccess;
import model.Admin;
import model.User;

public class Bank {
//	private static ArrayList<User> users = new ArrayList<>();
//	private static ArrayList<Admin> admins = new ArrayList<>();
//	public static HashMap<Admin, String> adminSecrets = new HashMap<>();
//	public static HashMap<User, String> userSecrets = new HashMap<>();
	public static User loggedInUser=null;
	public static Admin loggedInAdmin=null;
//	static {
//		Admin a1 = new Admin("Harrish", "1234567891", "harrishdhaithya@gmail.com", "Harr1234");
//		admins.add(a1);
//		System.out.println(a1);
//		adminSecrets.put(a1, "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK");
//		User u1 = new User("Harrish", "Dhaithya", "9994695787", "harrishdhaithya2001@gmail.com", "1234", 5000);
//		users.add(u1);
//		userSecrets.put(u1, "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK");
//		User u2 = new User("Tharun", "Kumar", "123456789", "harrish.1902037@srit.org", "1234", 8000);
//		users.add(u2);
//		userSecrets.put(u2, "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK");
//	}
	public static boolean signup(String fname, String lname, String phone, String email, String password,double bal) {
		ArrayList<User> users = FileAccess.getUsers();
		HashMap<String,String> userSecrets = new HashMap<>();
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
			if(!FileAccess.appendUser(user)) {
				return false;
			}
			String tfa = TwoFAAuth.generateSecretKey();
			System.out.println("Your Secret key for Two Factor Authentication: "+tfa);
			userSecrets.put(user.getAccno().toString(), tfa);
			if(!FileAccess.appendUserSecret(user.getAccno().toString(), tfa)) {
				return false;
			}
			return true;
		}
		return false;
	}
//	public static ArrayList<User> getAllAccount(){
//		if(loggedInAdmin==null) {
//			return null;
//		}
//		return users;
//	}
	public static User getAccDetails(String userid) {
		if(loggedInUser==null&&loggedInAdmin==null) {
			return null;
		}
		ArrayList<User> users = FileAccess.getUsers();
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
		ArrayList<User> users = FileAccess.getUsers();
		for(User u:users) {
			if(u.getAccno().equals(userid)) {
				double newbal = u.getBalance()+amount;
				u.setBalance(newbal);
				return FileAccess.updateUsers(users);
			}
		}
		return false;
	}
	public static boolean withdraw(UUID userid,double amount) {
		if(loggedInUser==null) {
			return false;
		}
		ArrayList<User> users = FileAccess.getUsers();
		for(User u:users) {
			if(u.getAccno().equals(userid)) {
				if(amount<u.getBalance()) {
					double newbal = u.getBalance()-amount;
					u.setBalance(newbal);
					return FileAccess.updateUsers(users);
				}
				return false;
			}
		}
		return false;
	}
	public static boolean userlogin(String email,String password) {
		ArrayList<User> users = FileAccess.getUsers();
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
			ArrayList<Admin> admins = FileAccess.getAdmins();
			for(Admin a:admins) {
//				System.out.println(a.getEmail()+" "+email);
				if(a.getEmail().equals(email.strip())) {
					if(a.evalPassword(password)) {
//						System.out.println("Wait for OTP...");
						if(TwoFAAuth.twoFA(a)) {
							loggedInAdmin = a;
							return true;
						}
					}
				}
			}
		return false;
	}
	public static boolean makeTransaction(String from,String to,double amount) {
		ArrayList<User> users = FileAccess.getUsers();
		User fromUser = null;
		User toUser = null;
		for(User u:users) {
			if(u.getAccno().toString().equals(from)) {
				fromUser = u;
			}
			if(u.getAccno().toString().equals(to)) {
				toUser = u;
			}
		}
		if(fromUser==null||toUser==null) {
			return false;
		}
		if(loggedInUser==null) {
			System.out.println("You are not Authenticated properly");
			return false;
		}
		if(fromUser.getBalance()<amount) {
			System.out.println("Insufficient Balance...");
			return false;
		}
		if(toUser.getAccno().equals(fromUser.getAccno())) {
			System.out.println("Transaction cannot be done in same account");;
			return false;
		}
		double fromBal = fromUser.getBalance()-amount;
		double toBal = toUser.getBalance()+amount;
		fromUser.setBalance(fromBal);
		toUser.setBalance(toBal);
		
		return FileAccess.updateUsers(users);
	}
	public static void showBalance(String accno) {
		if(loggedInUser==null) {
			System.out.println("Illegal Access...");
			return;
		}
		ArrayList<User> users = FileAccess.getUsers();
		for(User u:users) {
			if(u.getAccno().toString().equals(accno)) {
				System.out.println(u);
				return;
			}
		}
		System.out.println("Something went wrong...");
	}
	public static void userLogout() {
		loggedInUser=null;
	}
	public static void adminLogout() {
		loggedInAdmin=null;
	}
}
