import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintStream;
public class PhoneDirectory {
    private String firstName;
    private String phoneNumber;
    private static int numberOfContacts;
    public static final Scanner INPUT = new Scanner(System.in);
    public static final int MAX_CONTACTS = 10;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        HashMap<String,String> directory = new HashMap<String,String>(MAX_CONTACTS);
        
        String selection;
        boolean menu = true;
        while(true) {
            System.out.println("Select from the following Directory options:");
            System.out.println("1.) Search for contact");
            System.out.println("2.) Import file to Directory");
            System.out.println("3.) Add new contact");
            System.out.println("4.) Display all contacts on screen");
            System.out.println("5.) Export Directory to new file");
            System.out.println("Type any other key to exit");
            System.out.println();
            
            selection = INPUT.nextLine();
            
            switch(selection) {
                case "1":
                    getPhoneNumber(directory); 
                    break;
                case "2":
                    importFile(numberOfContacts, directory);
                    break;
                case "3":
                    addNewName(directory);
                    break;
                case "4":
                    printDirectory(directory);
                    break;
                case "5":
                    createNewFile(directory);
                    break;
                default:
                    System.out.println("End of program");
                    System.exit(0);
            }
        }
    }
    public static void addNewName(HashMap directory) {
        if(directory.size() >= MAX_CONTACTS) {
            System.out.println("Directory is full. Here is your complete directory:");
            printDirectory(directory);
        } else {
            System.out.println("Type the person's name");
            String firstName = INPUT.nextLine();
            System.out.println("Type the person's phone number");
            String phoneNumber = INPUT.nextLine();
            directory.put(firstName, phoneNumber);
        }
    }
    public static void importFile(int numberOfContacts, HashMap directory) throws FileNotFoundException, IOException {
        System.out.println("Type the name of the file to import");
        String fileName = INPUT.nextLine();
        File textFile = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(textFile));
        String line;
        for(int i = 1; i <= 7; i++) {
            line = reader.readLine();
            String[] tempArr = line.split(" ");
            directory.put(tempArr[0], tempArr[1]);
            
        }
        printDirectory(directory);
    }
    public static void getPhoneNumber(HashMap directory) {
        if(directory.isEmpty()){
            System.out.println("Your directory is empty");
        } else {
            System.out.println("Enter name to search:");
            String nameSearch = INPUT.nextLine();
        
            boolean contains;
            if (directory.containsKey(nameSearch)) {
                String phoneNumber = (String)directory.get(nameSearch);
                System.out.println(nameSearch + "'s number is " + phoneNumber);
            } else {
                System.out.println(nameSearch + " is not in your directory.");
                System.out.println("Add " + nameSearch + " to your directory? Y or N");
                String selection = INPUT.nextLine().toUpperCase();
                if(selection.equalsIgnoreCase("Y")) {
                    System.out.println("Type " + nameSearch + "'s phone number");
                    String phoneNumber = INPUT.nextLine();
                    directory.put(nameSearch, phoneNumber);
                } else {
                    System.out.println("Returning to main menu");
                    System.out.println();
                }
            }
        }
    }
    public static void printDirectory(HashMap directory) {
        Iterator<Map.Entry<String, String>> itr = directory.entrySet().iterator(); 
          
        while(itr.hasNext()) 
        { 
             Map.Entry<String, String> entry = itr.next(); 
             System.out.println(entry.getKey() +  
                                 ": " + entry.getValue()); 
        } 
        System.out.println();
    }
    public static void createNewFile(HashMap directory) throws IOException {
        System.out.println();
        System.out.println("Type the new file name");
        String fileName = INPUT.nextLine();
        System.out.println("Creating a file named " + fileName);
        PrintStream out = null;
        try {
            out = new PrintStream(new File(fileName));
            Iterator<Map.Entry<String, String>> itr = directory.entrySet().iterator(); 
            while(itr.hasNext()) {
         
                Map.Entry<String, String> entry = itr.next(); 
                out.println(entry.getKey() + ": " + entry.getValue());  
                                 
            } 
        } finally {
            if (out!= null) {
                out.close();
            }
        }
    }
}