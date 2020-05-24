/*I might have done things slightly differenly but I really wanted it to have the option of reading a file and prompting for names
 and I wanted to add a menu*/
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintStream;
public class PhoneDirectory {
    public static final Scanner INPUT = new Scanner(System.in);
    public static final int MAX_CONTACTS = 10; //The assignment said 10 people so I made it max out at 10.
    private static class Person { //I wanted to use a constructor so I could add additional values besides just phone numbers
        public String firstName;
        public String phoneNumber;
        public Person (String firstName, String phoneNumber) {
            this.firstName = firstName;
            this.phoneNumber = phoneNumber;
        }
        public String getPhoneNumber() {
            return this.phoneNumber;   
        }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        TreeMap<String, Person> directory = new TreeMap<String, Person>();  
        //At first I used hash map but I figured a tree map would be most appropriate because it automatically sorts it in order.
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
                    importFile(directory);
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
                default: //Just stop the program if the user types anything other than 1-5
                    System.out.println("End of program");
                    System.exit(0);
            }
        }
    }
    public static void addNewName(Map<String, Person> directory) {
        if(directory.size() >= MAX_CONTACTS) {
            System.out.println("Directory is full. Here is your complete directory:");
            printDirectory(directory);
        } else {
            System.out.println("Type the person's name");
            String firstName = INPUT.nextLine();
            System.out.println("Type the person's phone number");
            String phoneNumber = INPUT.nextLine();
            Person newPerson = new Person(firstName, phoneNumber);
            directory.put(firstName, newPerson);
        }
    }
    public static void importFile(Map<String, Person> directory) throws FileNotFoundException, IOException {
        System.out.println("Type the name of the file to import");
        String fileName = INPUT.nextLine();
        File textFile = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(textFile));
        String line;
        for(int i = 1; i <= 7; i++) { //I just used the old Directory.txt file and only take 7 names.
            line = reader.readLine();
            String[] tempArr = line.split(" ");
            Person newPerson = new Person(tempArr[0], tempArr[1]);
            directory.put(newPerson.firstName, newPerson);
        }
        printDirectory(directory);
    }
    public static void getPhoneNumber(Map<String, Person> directory) {
        if(directory.isEmpty()){
            System.out.println("Your directory is empty");
        } else {
            System.out.println("Enter name to search:");
            String nameSearch = INPUT.nextLine();
        
            boolean contains;
            if (directory.containsKey(nameSearch)) {
                Person search = directory.get(nameSearch);
                System.out.println(nameSearch + "'s number is " + search.phoneNumber);
            } else {
                System.out.println(nameSearch + " is not in your directory.");
                System.out.println("Add " + nameSearch + " to your directory? Y or N");
                String selection = INPUT.nextLine().toUpperCase();
                if(selection.equalsIgnoreCase("Y")) {
                    System.out.println("Type " + nameSearch + "'s phone number");
                    String phoneNumber = INPUT.nextLine();
                    directory.put(nameSearch, new Person(nameSearch, phoneNumber));
                } else {
                    System.out.println("Returning to main menu");
                    System.out.println();
                }
            }
        }
    }
    public static void printDirectory(Map<String, Person> directory) {
        Iterator<Map.Entry<String, Person>> itr = directory.entrySet().iterator(); 
        while(itr.hasNext()) {
             Map.Entry<String, Person> entry = itr.next(); 
             System.out.println(entry.getKey() +  
                                 ": " + entry.getValue().getPhoneNumber()); 
        } 
        System.out.println();
    }
    public static void createNewFile(Map<String, Person> directory) throws IOException {
        System.out.println();//I copied this method from last week's program and changed it a little to work for this assignment.
        System.out.println("Type the new file name");
        String fileName = INPUT.nextLine();
        System.out.println("Creating a file named " + fileName);
        PrintStream out = null;
        try {
            out = new PrintStream(new File(fileName));
            Iterator<Map.Entry<String, Person>> itr = directory.entrySet().iterator(); 
            while(itr.hasNext()) {
                Map.Entry<String, Person> entry = itr.next(); 
                out.println(entry.getKey() + ": " + entry.getValue().getPhoneNumber());    
            } 
        } finally {
            if (out!= null) {
                out.close();
            }
        }
    }
}
 