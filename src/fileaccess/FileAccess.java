package fileaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import model.Admin;
import model.User;

public class FileAccess {
	public static ArrayList<User> getUsers(){
		ArrayList<User> al = new ArrayList<>();
		try {
			FileReader read = new FileReader("src/Files/user.txt");
			BufferedReader br = new BufferedReader(read);
			String accno;
			while((accno=br.readLine())!=null) {
				String fname = br.readLine();
				String lname = br.readLine();
				String email = br.readLine();
				String phone = br.readLine();
				double balance = Double.parseDouble(br.readLine());
				String passwordHash = br.readLine();
//				System.out.println(password);
//				br.readLine();
				User u = new User(UUID.fromString(accno), fname, lname, phone, email, passwordHash, balance);
				al.add(u);
				br.readLine();
			}
			
		}catch(Exception e) {
			System.out.println("Not able to read users...");
		}
		return al;
	}
	public static boolean updateUsers(ArrayList<User> users) {
		try {
			FileWriter writer = new FileWriter("src/Files/user.txt",false);
			for(User u:users) {
				writer.write(u.getAccno().toString()+"\n");
				writer.write(u.getFname()+"\n");
				writer.write(u.getLname()+"\n");
				writer.write(u.getEmail()+"\n");
				writer.write(u.getPhone()+"\n");
				writer.write(u.getBalance()+"\n");
				writer.write(u.getPasswordHash()+"\n");
				writer.write("----------------------------------------------------------------------------------------------------------------\n");
//				System.out.println(u);
			}
			writer.close();
			return true;
		}catch(Exception e) {
			System.out.println("Not able to update user...");
			System.out.println(e);
			return false;
		}
		
	}
	public static boolean appendUser(User u) {
		try {
			FileWriter writer = new FileWriter("src/Files/user.txt",true);
			writer.write(u.getAccno().toString()+"\n");
			writer.write(u.getFname()+"\n");
			writer.write(u.getLname()+"\n");
			writer.write(u.getEmail()+"\n");
			writer.write(u.getPhone()+"\n");
			writer.write(u.getBalance()+"\n");
			writer.write(u.getPasswordHash()+"\n");
			writer.write("----------------------------------------------------------------------------------------------------------------\n");
			writer.close();
			return true;
		} catch (Exception e) {
			System.out.println("Not able to append a user...");
			return false;
		}
	}
	public static ArrayList<Admin> getAdmins(){
		ArrayList<Admin> admins = new ArrayList<>();
		try {
			FileReader read = new FileReader("src/Files/admin.txt");
			BufferedReader br = new BufferedReader(read);
			String empid;
			while((empid=br.readLine())!=null) {
				String name = br.readLine();
				String phone = br.readLine();
				String email = br.readLine();
				String passwordHash = br.readLine();
				Admin admin = new Admin(UUID.fromString(empid), name, phone, email, passwordHash);
				admins.add(admin);
				br.readLine();
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return admins;
	}
	public static boolean updateAdmins(ArrayList<Admin> admins) {
		try {
			FileWriter writer = new FileWriter("src/Files/admin.txt",false);
			for(Admin a:admins) {
				writer.write(a.getEmpid().toString()+"\n");
				writer.write(a.getName()+"\n");
				writer.write(a.getPhone()+"\n");
				writer.write(a.getEmail()+"\n");
				writer.write(a.getPasswordHash()+"\n");
				writer.write("---------------------------------------------------------------------------------------------\n");
			}
			writer.close();
		}catch (Exception e) {
			System.out.println("Not able to write the update...");
			return false;
		}
		return false;
	}
	public static boolean appendAdmin(Admin a) {
		try {
			FileWriter writer = new FileWriter("src/Files/admin.txt",true);
			writer.write(a.getEmpid().toString()+"\n");
			writer.write(a.getName()+"\n");
			writer.write(a.getPhone()+"\n");
			writer.write(a.getEmail()+"\n");
			writer.write(a.getPasswordHash()+"\n");
			writer.write("---------------------------------------------------------------------------------------------\n");
			writer.close();
			return true;
		} catch (Exception e) {
			System.out.println("Not able to append a file...");
			return false;
		}
	}
	public static HashMap<String,String> getUserSecret(){
		HashMap<String,String> userSecrets = new HashMap<>();
		try {
			FileReader read = new FileReader("src/Files/usersecrets.txt");
			BufferedReader br = new BufferedReader(read);
			String accno;
			while((accno=br.readLine())!=null) {
				String secret = br.readLine().strip();
				userSecrets.put(accno, secret);
				br.readLine();
			}
			br.close();
		}catch(Exception e) {
			System.out.println("Not able to read the file");
		}
		return userSecrets;
	}
	public static boolean updateUserSecret(HashMap<String,String> userSecrets) {
		try {
			FileWriter writer = new FileWriter("src/Files/usersecrets.txt",false);
			for(Map.Entry<String, String> user:userSecrets.entrySet()) {
				String accno = user.getKey();
				String secret = user.getValue();
				writer.write(accno+"\n");
				writer.write(secret+"\n");
				writer.write("-------------------------------------------------------------------------------------------------\n");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Not able to write the file...");
		}
		
		return false;
	}
	public static boolean appendUserSecret(String accno,String secret) {
		try {
			FileWriter writer = new FileWriter("src/Files/usersecrets.txt",true);
			writer.write(accno+"\n");
			writer.write(secret+"\n");
			writer.write("-------------------------------------------------------------------------------------------------\n");
			writer.close();
			return true;
		}catch(Exception e) {
			System.out.println("Not able to append a user...");
			return false;
		}
	}
	public static HashMap<String, String> getAdminSecret(){
		HashMap<String,String> admins = new HashMap<>();
		try {
			FileReader reader = new FileReader("src/Files/adminsecrets.txt");
			BufferedReader br = new BufferedReader(reader);
			String empno;
			while((empno=br.readLine())!=null) {
				String secret = br.readLine();
				admins.put(empno, secret);
				br.readLine();
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Not able to read file...");
		}
		return admins;
	}
	public static boolean updateAdminSecret(HashMap<String,String> adminSecret) {
		try {
			FileWriter writer = new FileWriter("src/Files/adminsecrets.txt",false);
			for(Map.Entry<String, String> admin:adminSecret.entrySet()) {
				String empno = admin.getKey();
				String secret = admin.getValue();
				writer.write(empno+"\n");
				writer.write(secret+"\n");
				writer.write("----------------------------------------------------------------------------------------------------\n");
			}
			writer.close();
			return true;
		}catch(Exception e) {
			System.out.println("Not able to write the file");
			return false;
		}
	}
	public static boolean appendAdminSecret(String empno,String secret) {
		try {
			FileWriter writer = new FileWriter("src/Files/adminsecrets.txt",true);
			writer.write(empno+"\n");
			writer.write(secret+"\n");
			writer.write("----------------------------------------------------------------------------------------------------\n");
			writer.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
//	public static void main(String[] args) {
//		System.out.println(getUsers().toString());
//	}
}
