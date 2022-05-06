package Views;

import java.util.ArrayList;
import java.util.Scanner;

import controller.Bank;
import controller.Otp;
import model.User;

public class Main {
	static Scanner in = null;
	static void showHome() {
		in = new Scanner(System.in);
		boolean flag = true;
		while(flag) {
		System.out.println("__________________________Welcome To Banking System_________________________");
		System.out.println("1. Admin Login");
		System.out.println("2. User SignUp");
		System.out.println("3. User Login");
		System.out.println("4. Exit");
		System.out.println("Enter Your Choice: ");
		int ch = in.nextInt();
		if(ch==1) {
			System.out.println("Enter Your Email: ");
			String email = in.next();
			in.nextLine();
			System.out.println("Enter Your Password: ");
			String password = in.next();
			if(Bank.adminLogin(email, password)) {
				showAdmin();
			}else {
				System.out.println("Try Again with proper Credentials :(");
			}
		}else if(ch==2) {
			in.nextLine();
			System.out.println("Enter Your First Name: ");
			String fname = in.nextLine();
			System.out.println("Enter Your Last Name: ");
			String lname = in.nextLine();
			System.out.println("Enter Your Phone Number: ");
			String phone = in.nextLine();
			System.out.println("Enter Your Email: ");
			String email = in.nextLine();
			System.out.println("Enter Your Password: ");
			String password = in.nextLine();
			System.out.println("Enter Your Opening Balance: ");
			double balance = in.nextDouble();
			System.out.println("Wait for OTP verification");
			if(Bank.signup(fname, lname, phone, email, password, balance)) {
				System.out.println("Signup successfull go ahed and login");
			}else {
				System.out.println("Signup failed");
			}
		}else if(ch==3) {
			System.out.println("Enter Email: ");
			String email = in.next();
			System.out.println("Enter Password: ");
			String password = in.next();
			if(Bank.userlogin(email, password)) {
				showUser();
			}else {
				System.out.println("Try Again with proper Credentials :(");
			}
		}else if(ch==4) {
			System.exit(0);
			return;
		}else {
			System.out.println("Enter a valid choice...");
		}
		}
		in.close();
	}
	static void showAdmin() {
		in = new Scanner(System.in);
		boolean flag = true;
		while(flag) {
		System.out.println("__________________________Welcome To Banking System_________________________");
		System.out.println("1. See All Users");
		System.out.println("2. Search for a User");
		System.out.println("3. Logout");
		System.out.println("Enter Your Choice: ");
		int ch = in.nextInt();
		if(ch==1) {
			ArrayList<User> al = Bank.getAllAccount();
			if(al == null) {
				System.out.println("No Accounts Available");
			}
			for(User u:al) {
				System.out.println(u);
				System.out.println();
			}
		}else if(ch==2) {
			System.out.println("Enter Account Number: ");
			in.nextLine();
			String accno = in.nextLine();
//			System.out.println(accno);
			User u = Bank.getAccDetails(accno);
			System.out.println(u);
		}else if(ch==3) {
			Bank.adminLogout();
			flag=false;
			showHome();
			return;
		}
		}
		in.close();
	}
	static void showUser() {
		in = new Scanner(System.in);
		boolean flag = true;
		while(flag) {
		System.out.println("__________________________Welcome To Banking System_________________________");
		System.out.println("1. Deposit Amount");
		System.out.println("2. Withdraw Amount");
		System.out.println("3. View Balance");
		System.out.println("4. Make Transaction");
		System.out.println("5. Logout");
		System.out.println("Enter Your choice: ");
		int ch = in.nextInt();
		if(ch == 1) {
			System.out.println("Enter the Amount to Deposit: ");
			double amount = in.nextDouble();
			boolean success = Bank.deposit(Bank.loggedInUser.getAccno(), amount);
			if(!success) {
				System.out.println("Transaction Failed...");
				System.out.println("Try Again...");
			}
		}else if(ch == 2) {
			System.out.println("Enter the Amout to Withdraw: ");
			double amount = in.nextDouble();
			boolean success = Bank.withdraw(Bank.loggedInUser.getAccno(), amount);
			if(!success) {
				System.out.println("Transaction Failed...");
				System.out.println("Try Again...");
			}
		}else if(ch == 3) {
			System.out.println(Bank.loggedInUser);
		}else if(ch == 4){
			in.nextLine();
			System.out.println("Enter the Account Number: ");
			String accno = in.nextLine();
			System.out.println("Enter the Amount: ");
			double amount = in.nextDouble();
			boolean success = Bank.makeTransaction(Bank.loggedInUser, accno, amount);
			if(!success) {
				System.out.println("Transaction Failed...");
			}
		}else if(ch==5) {
			Bank.userLogout();
			showHome();
			return;
		}else {
			System.out.println("Enter a Valid Choice");
		}
		}
		
	}
	public static void main(String[] args) {
		if(Bank.loggedInAdmin!=null) {
			showAdmin();
		}else if(Bank.loggedInUser!=null) {
			showUser();
		}else {
			showHome();
		}
	}
}
