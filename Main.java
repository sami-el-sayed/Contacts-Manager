
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static MobilePhone mobilePhone = new MobilePhone();
    private static ContactsLoader contactsLoader = new ContactsLoader(); 

    public static void main(String[] args) {
        // Create a program that implements a simple mobile phone with the following capabilities.
        // Able to store, modify, remove and query contact names.
        // You will want to create a separate class for Contacts (name and phone number).
        // Create a master class (MobilePhone) that holds the ArrayList of Contacts
        // The MobilePhone class has the functionality listed above.
        // Add a menu of options that are available.
        // Options:  Quit, print list of contacts, add new contact, update existing contact, remove contact
        // and search/find contact.
        // When adding or updating be sure to check if the contact already exists (use name)
        // Be sure not to expose the inner workings of the Arraylist to MobilePhone
        // e.g. no ints, no .get(i) etc
        // MobilePhone should do everything with Contact objects only.

        boolean quit = false;
        startPhone();
        var contactsArr = contactsLoader.LoadContactsFromFile();
        for (Contact contact : contactsArr) {
            mobilePhone.addNewContact(contact);
        }
        printActions();
        while(!quit) {
            System.out.println("\nEnter action: (6 to show available actions)");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.println("\nShutting down...");
                    quit = true;
                    break;

                case 1:
                    mobilePhone.printContacts();
                    break;

                case 2:
                    addNewContact();
                    break;

                case 3:
                    updateContact();
                    break;

                case 4:
                    removeContact();
                    break;

                case 5:
                    queryContact();
                    break;

                case 6:
                    printActions();
                    break;
            }

        }
    }

    private static void saveContracts()
    {
        var contactsArray = mobilePhone.GetContactsArray();
        contactsLoader.SaveContactsToFile(contactsArray);

    }

    private static void addNewContact() {

        var validPhoneNumber = false;
        String name = "";
        String phone = "";

        System.out.println("Enter new contact name: ");
        name = scanner.nextLine();

        while(!validPhoneNumber){
            System.out.println("Enter first phone number: ");
            phone = scanner.nextLine();
            if (mobilePhone.findPhoneNumber(phone)){
                System.out.println("Given phone number already exist!");
            }
            else{
                validPhoneNumber = true;
            }
        }

        Contact newContact = Contact.createContact(name, phone);
        
        boolean finish = false;


        while(!finish){
            System.out.println("1.Add another phone number");
            System.out.println("2.Finish adding contact");
    
            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    System.out.println("Please Enter another phone number: ");
                    Scanner innerScanner = new Scanner(System.in);
                    String newPhoneNumber = innerScanner.nextLine();
                    if (mobilePhone.findPhoneNumber(newPhoneNumber)){
                        System.out.println("Given phone number already exist!");
                    }else{
                        newContact.addAnotherPhoneNumber(newPhoneNumber);
                    }

                    break;
                case 2:
                    finish = true;
                    break;
            }
        }
        

        if(mobilePhone.addNewContact(newContact)) {
            System.out.println("New contact added:");
            newContact.printContact();
            saveContracts();
        } else {
            System.out.println("Cannot add, " + name + " already on file");
        }
    }

    private static void updateContact() {


        System.out.println("Enter existing contact name: ");
        String name = scanner.nextLine();
        Contact existingContactRecord = mobilePhone.queryContact(name);
        if(existingContactRecord == null) {
            System.out.println("Contact not found.");
            return;
        }


        System.out.println("Choose edit operatiion:");

        System.out.println("1.Edit Name");
        System.out.println("2.Add Phone Number");
        System.out.println("3.Delete Phone Number");


        int action = scanner.nextInt();
    
        switch (action) {

            case 1:    
                System.out.print("Enter new contact name: ");
                Scanner innerScanner = new Scanner(System.in);
                String newName = innerScanner.nextLine();
                if(mobilePhone.updateContactName(existingContactRecord, newName)) {
                    System.out.println("Successfully updated record");
                } else {
                    System.out.println("Error updating record.");
                }
                break;

            case 2:
                System.out.println("Please Enter the new phone number: ");
                innerScanner = new Scanner(System.in);
                String newPhoneNumber = innerScanner.nextLine();
                if (mobilePhone.findPhoneNumber(newPhoneNumber)){
                    System.out.println("Given phone number already exist!");
                }else{
                    existingContactRecord.addAnotherPhoneNumber(newPhoneNumber);
                    if(mobilePhone.updateContactPhones(existingContactRecord, existingContactRecord.getPhoneNumbers())){
                        System.out.println("Successfully updated record");
                    };
                }
                break;

            case 3:
                System.out.println("Here is the list of numbers for contact " + existingContactRecord.getName() + " :");
                existingContactRecord.printContact();
                System.out.println("Please type the phone number you want to delete");
                innerScanner = new Scanner(System.in);
                
                String phoneNumberToDelete = innerScanner.nextLine();
                existingContactRecord.deletePhoneNumber(phoneNumberToDelete);
                if(mobilePhone.updateContactPhones(existingContactRecord, existingContactRecord.getPhoneNumbers())){
                    System.out.println("Successfully updated record");
                };
               
            break;

        }

        saveContracts();




    }

    private static void removeContact() {
        System.out.println("Enter existing contact name: ");
        String name = scanner.nextLine();
        Contact existingContactRecord = mobilePhone.queryContact(name);
        if (existingContactRecord == null) {
            System.out.println("Contact not found.");
            return;
        }

        if(mobilePhone.removeContact(existingContactRecord)) {
            System.out.println("Successfully deleted");
            saveContracts();
        } else {
            System.out.println("Error deleting contact");
        }
    }

    private static void queryContact() {
        System.out.println("Enter existing contact name: ");
        String name = scanner.nextLine();
        Contact existingContactRecord = mobilePhone.queryContact(name);
        if (existingContactRecord == null) {
            System.out.println("Contact not found.");
            return;
        }
        System.out.println("Found record:");
        existingContactRecord.printContact();
    }

    private static void startPhone() {
        System.out.println("Starting phone...");
    }

    private static void printActions() {
        System.out.println("\nAvailable actions:\npress");
        System.out.println("0  - to shutdown\n" +
                "1  - to print contacts\n" +
                "2  - to add a new contact\n" +
                "3  - to update existing an existing contact\n" +
                "4  - to remove an existing contact\n" +
                "5  - query if an existing contact exists\n" +
                "6  - to print a list of available actions.");
        System.out.println("Choose your action: ");
    }

}

