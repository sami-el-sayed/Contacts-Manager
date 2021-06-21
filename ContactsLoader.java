import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactsLoader {
    private String fileName = "contacts.txt";
    private String endOfContactLine = "-----";
    
    public ContactsLoader() {}


    public ArrayList<Contact> LoadContactsFromFile() {
        System.out.println("Loading contacts");
        var contactArr = new ArrayList<Contact>();
        boolean nameLine = true;
        try{
            var file = new File(fileName);
            Scanner myReader = new Scanner(file);
            String name = "";
            var numbersArr = new ArrayList<String>();

            while (myReader.hasNextLine()) {
              
              String line = myReader.nextLine();

              if(line.equals(endOfContactLine)){
                  nameLine = true;
                  var newContact = new Contact(name,numbersArr);
                  name = "";
                  numbersArr = new ArrayList<String>();
                  contactArr.add(newContact);
              }
              else if (nameLine==true) {
                name = line;
                nameLine = false;
              }
              else if (nameLine==false) {
                System.out.println(line);
                numbersArr.add(line);
              }

            }
            myReader.close();
    
        }
        catch(FileNotFoundException ex){}
  
        return contactArr;
    }


    public void SaveContactsToFile(ArrayList<Contact> contactsList ) {
        try {
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
                WriteContactsToFile(contactsList);
            } else {
                WriteContactsToFile(contactsList);
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

    }


    private void WriteContactsToFile(ArrayList<Contact> contactsList) {
        try{
            FileWriter myWriter = new FileWriter(fileName);
            for (Contact contact : contactsList) {
                //Write name of contact to file
                myWriter.write(contact.getName());
                myWriter.write(String.format("%n"));

                ArrayList<String> numbers = contact.getPhoneNumbers();
                //Write every phone number
                for (String phoneNumber : numbers) {
                    myWriter.write(phoneNumber);
                    myWriter.write(String.format("%n"));

                }
                //Write the end of contact line
                myWriter.write(endOfContactLine);
                myWriter.write(String.format("%n"));
            }
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

    }


}
