import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
public class MainClass {
 public static Scanner scan= new Scanner(System.in);
 public static boolean agreeMENT = false;
  public static void mainPage () {
	    
		System.out.println("*****************************************************");
		System.out.println("**              Student Expense System             **");
		System.out.println("*****************************************************");
		System.out.println("*****************************************************");
		System.out.println("**                   [1] Sign Up                   **");
		System.out.println("**                   [2] Sign In                   **");
		System.out.println("**                   [3] Checkout                  **");
		System.out.println("**                   [4] Exit                      **");
		System.out.println("*****************************************************");
		
		
  }
  public static void signUp() throws IOException {
	    System.out.println("*******************Sign Up Page**********************");
	    System.out.print("Enter New Username: ");
	    String username = scan.next();
	    Path filePath = Paths.get("usernames.txt");
	    File us1 = new File("usernames.txt");
	    if(us1.exists()) {
    	System.out.println("+++++++++++++++++++++");
    		BufferedReader br = new BufferedReader(new FileReader("usernames.txt"));
			  String st; 
			  while (( st = br.readLine()) != null) {
				  if(username.equalsIgnoreCase(st)) {
					  System.out.println("username is taken, please sign up with a different Username");
					  signUp();
				  }
			  }
	    }
	    System.out.print("Enter New Password: ");
	    String password = scan.next();
	    if(!(username.equals("")) || !(password.equals("")) ) {
	    	String temp= "username.txt";
	    	String temp2= "password.txt";
	    	File dir= new File(username);
	    	String temp3= hash(password);
	    	FileWriter usernames = new FileWriter("usernames.txt",true);
	    	usernames.append(username+"\n");
	    	usernames.close();
	    	dir.mkdir();
	    	 FileWriter w = new FileWriter(username+"\\"+temp);
	    	 w.write(username);
	    	 w.close();
	    	 FileWriter pwd = new FileWriter(username+"\\"+temp2);
	    	 pwd.write(temp3);
	    	 pwd.close();
	    	 System.out.println("Account has been created");
	    	 mainPage();
	    	 int someone= scan.nextInt();
	 		if(someone==1) {
	 			signUp();
	 		}
	 		else if (someone==2) {
	 			signIn();
	 		}
	    	 
	    }
	    else {
	    	System.out.println("Please Enter a valid Username/Password");
	    	signUp();
	    	
	    }
	    
  }
  private static String hash(String input) {
	  try { 
		  
          MessageDigest md = MessageDigest.getInstance("MD5"); 
          byte[] messageDigest = md.digest(input.getBytes()); 
          BigInteger no = new BigInteger(1, messageDigest);
          String hashtext = no.toString(16); 
          while (hashtext.length() < 32) { 
              hashtext = "0" + hashtext; 
          } 
          return hashtext; 
      }  
      catch (NoSuchAlgorithmException e) { 
          throw new RuntimeException(e); 
      } 
  } 
  public static void signIn() throws IOException {
	  System.out.print("Enter Your Username: ");
	  String uname=scan.next();
	  System.out.print("Enter Your Password: ");
	  String pwd=scan.next();
	  String md5 = hash(pwd);
	  File f= new File(uname+"\\password.txt");
	  if(f.exists()) {
		  BufferedReader br = new BufferedReader(new FileReader(f));
		  String st; 
		  while (( st = br.readLine()) != null) 
		   if(st.equals(md5) ) {
			   System.out.println("You have now logged in as "+uname);
				   hasSignedIn(uname);
			   
		   }
		   else {
			   System.out.println("your username or password is Wrong, Please Enter a Valid uname/pwd");
			   signIn();
		   }
		  
	  }
	  else {
		  System.out.println("your username or password is Wrong, Please Enter a Valid uname/pwd");
		  signIn();
	  }
	  
  }
  public static void hasSignedIn(String uname) throws IOException {
	    System.out.println("*****************************************************");
		System.out.println("**                 Welcome: "+ uname+ "                   **");
		System.out.println("*****************************************************");
		System.out.println("*****************************************************");
		System.out.println("**                   [1] Add Item                  **");
		System.out.println("**                   [2] Delete Item               **");
		System.out.println("**                   [3] View My Bill              **");
		System.out.println("**                   [4] View All Bills            **");
		System.out.println("**                   [5] View Checkout             **");
		System.out.println("**                   [6] Return                    **");
		System.out.println("*****************************************************");
		File checker = new File("checks.txt");
		File checkUname= new File("checkout.txt");
		checker.createNewFile();
		checkUname.createNewFile();
		BufferedReader cc = new BufferedReader(new FileReader(checkUname));
		 BufferedReader br = new BufferedReader(new FileReader(checker));
		  String st; 
		  String st2;
		  while (( st = br.readLine()) != null ) {
			  st2=cc.readLine();
			  if(st2!=null) {
			  if(st.equals("true")){
			  if(!(uname.equals(st2))) {
				  System.out.println("IMPORTANT MESSAGE: "+st2+" wants to checkout");
				  System.out.println("Do you agree ?");
				  System.out.println("[1] Yes");
				  System.out.println("[2] No");
				  File agreement= new File(uname+"\\agreement.txt");
				  agreement.createNewFile();
				  int agree = scan.nextInt();
				  
				  if(agree==1) {
					  FileWriter agrees = new FileWriter(agreement);
					  agrees.write("true");
					  agrees.close();
				  }
				  else if(agree==2) {
					  FileWriter agrees = new FileWriter(agreement);
					  agrees.write("false");
					  agrees.close();
				  }
			  }
			  
		  }
			  }
			  }
		scan.nextLine();
		int chosen = scan.nextInt();
		scan.nextLine();
		if(chosen==1) {
			addItem(uname);
		}
		else if(chosen==2) {
			removeItem(uname);
		}
		else if(chosen==3) {
			viewMyBill(uname);
		}
		else if(chosen==4) {
			viewAllBills();
		}
		else if(chosen==5) {
			System.out.println("please before checking out. look who should pay and recieve in the main Board!");
			System.out.println("Are you sure you want to checkout? ");
			System.out.println("[1] Yes");
			System.out.println("[2] No");
			int check = scan.nextInt();
			if(check==1) {
				FileWriter agreement = new FileWriter(uname+"\\agreement.txt");
				agreement.write("true");
				agreement.close();
				FileWriter checks = new FileWriter("checks.txt");
				checks.write("true");
				checks.close();
				FileWriter check1 = new FileWriter("checkout.txt");
				check1.write(uname);
			    check1.close();
			}
		}
		
  }
    private static void viewAllBills() throws IOException {
    	try {
    	Path filePath = Paths.get("usernames.txt");
    	System.out.println("+++++++++++++++++++++");
    	List<String> users = Files.readAllLines(filePath, Charset.forName("UTF-8"));
	    for (int i = 0; i < users.size(); i++) {
			System.out.println("User: "+users.get(i));
			Path pathfile = Paths.get(users.get(i)+"\\allitemNames.txt");
			List<String> allItemNames = Files.readAllLines(pathfile, Charset.forName("UTF-8"));
			for (int j = 0; j < allItemNames.size(); j++) {
				File items = new File(users.get(i)+"\\items\\"+allItemNames.get(j));
				BufferedReader br = new BufferedReader(new FileReader(items));
			    String st; 
			    while (( st = br.readLine()) != null) {
				  System.out.println(st);
				  
			  }
			    System.out.println("--------------------");
			}
			System.out.println("+++++++++++++++++++++");
			
	    	
		}}
    	catch(Exception e) {
    		System.out.println("there might be no item for this user");
    	}
}
	private static void viewMyBill(String uname) throws IOException {
    	Path filePath = Paths.get(uname+"\\allitemNames.txt");
    	List<String> names = Files.readAllLines(filePath, Charset.forName("UTF-8"));
    	for (int i = 0; i < names.size(); i++) {
    		System.out.println("++++++++++++++++++++++++++++++");
			File items = new File(uname+"\\items\\"+names.get(i));
			 BufferedReader br = new BufferedReader(new FileReader(items));
			  String st; 
			  while (( st = br.readLine()) != null) {
				  System.out.println(st);
			  }
		}	
}
	private static void removeItem(String uname) throws IOException {
	
	Path filePath = Paths.get(uname+"\\allitemNames.txt");
	List<String> names = Files.readAllLines(filePath, Charset.forName("UTF-8"));
	for(int i=0;i<names.size();i++){
	    System.out.println(names.get(i).substring(0,names.get(i).indexOf(".txt")));
	} 
	System.out.print("Enter the name of your items that u want to delete: ");
	String willBeDeleted= scan.nextLine();
	willBeDeleted.trim();
	File deleted = new File(uname+"\\items\\"+willBeDeleted+".txt");
	
	if(deleted.exists()) {
		deleted.delete();
		System.out.println("deleted");
		
	}
	else {
		System.out.println("File is not exist");
	}
	File inputFile = new File(uname+"\\allitemNames.txt");
	File tempFile = new File(uname+"\\temp.txt");
	BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	String lineToRemove = willBeDeleted+".txt";
	String currentLine;
	while((currentLine = reader.readLine()) != null) {
	    String trimmedLine = currentLine.trim();
	    if(trimmedLine.equals(lineToRemove)) continue;
	    writer.write(currentLine + System.getProperty("line.separator"));
	}
	reader.close();
    writer.close();
    inputFile.delete();
    tempFile.renameTo(inputFile); 
}
	public static void addItem(String uname) throws IOException {
    	  System.out.print("Enter name of your Item: ");
		  String itemName = scan.nextLine();
		  System.out.println("Type number only.");
		  System.out.print("Enter the price: ");
		  int price =Integer.parseInt(scan.nextLine());
		  System.out.println("Date format:: DD-MM-YY ");
		  System.out.print("Enter Purchase date: ");
		  String date = scan.next();
		  System.out.print("Enter The Quantity: ");
		  int quantity = scan.nextInt();
		  scan.nextLine();
		  int saved = quantity*price;
		  File x = new File(uname+"\\items");
		  x.mkdir();
		  FileWriter allitemNames = new FileWriter(uname+"\\allItemNames.txt",true);
		  allitemNames.append(itemName+date+".txt"+"\n");
		  allitemNames.close();
		  FileWriter items = new FileWriter(uname+"\\items\\"+itemName+date+".txt");
		  items.write("Name: "+itemName+"\n"+"Price: "+price+"\n"+"Date: "+date+"\n"+"Quantity: "+quantity+"\n"+saved);
		  items.close();
		  hasSignedIn(uname);
	  }
	
	public static void main(String[] args) throws IOException {
		mainPage();
		int someone= scan.nextInt();
		if(someone==1) {
			signUp();
		}
		else if (someone==2) {
			signIn();
		}
		else if(someone==3) {
			checkout();
		}
		else if(someone==4) {
			System.exit(0);
		}
		Path filePath = Paths.get("usernames.txt");
    	List<String> names = Files.readAllLines(filePath, Charset.forName("UTF-8"));
    	
	}
	private static void checkout() throws IOException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Path filePath = Paths.get("usernames.txt");
		int average =0;
		int total = 0 ;
		int countUsers=0;
		int tempo=0;
    	List<String> users = Files.readAllLines(filePath, Charset.forName("UTF-8"));
    	for (int i = 0; i < users.size(); i++) {
    		countUsers=users.size();
    		Path filePath1 = Paths.get(users.get(i)+"\\allitemNames.txt");
    		List<String> items = Files.readAllLines(filePath1, Charset.forName("UTF-8"));
    		for (int j = 0; j < items.size(); j++) {
    			File itemz = new File(users.get(i)+"\\items\\"+items.get(j));
				BufferedReader br = new BufferedReader(new FileReader(itemz));
			    String st; 
			    br.readLine();br.readLine();br.readLine();br.readLine();
			    while (( st = br.readLine()) != null) {
			    	
			    	tempo=tempo+Integer.parseInt(st);
			    }
			    
			}
    		map.put(users.get(i), tempo);
    		tempo=0;
			}
    	      for (int i = 0; i < map.size(); i++) {
				total=total+map.get(users.get(i));
			}
			  int temp=0;
    	average = total/countUsers;	
        for (int j = 0; j < map.size(); j++) {
			temp=map.get(users.get(j))-average;
			if(temp>=0) {
				System.out.println(users.get(j)+" = "+"recieve: "+ Math.abs(temp));
			}
			 if(temp<0) {
				System.out.println(users.get(j)+" = "+"Pay: "+ Math.abs(temp));
			}
		}             
        for (int i = 0; i < users.size(); i++) {
			File agreement = new File(users.get(i)+"\\agreement.txt");
			if(agreement.exists()) {
			 BufferedReader br = new BufferedReader(new FileReader(agreement));
			  String st; 
			  while (( st = br.readLine()) != null) {
				  if(st.equals("true")) {
					  agreeMENT=true;
				  }
				  else if(st.equals("false")) {
					  agreeMENT=false;
				  }
			  }
	}}
    	if(agreeMENT==true) {
    		for (int i = 0; i < users.size(); i++) {
    			Path filePath1 = Paths.get(users.get(i)+"\\allitemNames.txt");
        		List<String> items = Files.readAllLines(filePath1, Charset.forName("UTF-8"));
				for (int j = 0; j < items.size(); j++) {
					File x = new File(users.get(i)+"\\items\\"+items.get(j));
					x.setWritable(true);
					x.delete();
				}
				
			
			}
    	}
		
	}

}
