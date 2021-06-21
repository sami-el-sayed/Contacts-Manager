import java.util.ArrayList;
import java.util.HashMap;

public class MobilePhone {
    private HashMap<String,Contact> myContacts;

    public MobilePhone() {
        this.myContacts = new HashMap<String,Contact>();
    }


    public ArrayList<Contact> GetContactsArray() {

        var contactList = new ArrayList<Contact>();

        for ( String key : myContacts.keySet() ) {
            var contact = myContacts.get(key);
            contactList.add(contact);
        }

        return contactList;
    }

    public boolean addNewContact(Contact contact) {

        int phoneNumCount = 0;

        for (String phoneNum : contact.getPhoneNumbers()) {
            if (phoneNum.trim()!=""){
                phoneNumCount = phoneNumCount + 1;
            }
        }

        if(phoneNumCount==0){
            System.out.println("Empty phone list");
            return false;
        }

        if(findContact(contact) != null) {
            System.out.println("Contact is already on file");
            return false;
        }

        myContacts.put(contact.getName(),contact);
        return true;
    }


    public boolean findPhoneNumber(String phoneNumberToFind) {


        phoneNumberToFind = phoneNumberToFind.trim();
        boolean found = false;

        for ( String key : this.myContacts.keySet() ) {
            ArrayList<String>phoneNumbers = this.myContacts.get(key).getPhoneNumbers();

            for (String number : phoneNumbers) {
                if (number.trim().equals(phoneNumberToFind)) {
                    found = true;
                }
            }

            if(found){
                break;
            }
        }
        
        return found;
    }

    public boolean updateContactName(Contact oldContact, String newName) {
        Contact foundContact = findContact(oldContact);
        if(foundContact == null) {
            System.out.println(oldContact.getName() +", was not found.");
            return false;
        }

        ArrayList<String> phoneNumbers = oldContact.getPhoneNumbers(); 

        Contact newContact = Contact.createContact(newName, phoneNumbers.get(0));
        newContact.changePhoneNumbers(phoneNumbers);
        
        this.removeContact(oldContact);
        this.myContacts.put(newContact.getName(), newContact);

        System.out.println(oldContact.getName() + ", was replaced with " + newName);
        return true;
    }

    public boolean updateContactPhones(Contact oldContact, ArrayList<String> newPhoneNumbers) {

        if (newPhoneNumbers.size() == 0) {
            System.out.println("Can't delete all phone numbers!");
            return false;
        }

        Contact foundContact = findContact(oldContact);
        if(foundContact == null) {
            System.out.println(oldContact.getName() +", was not found.");
            return false;
        }

        Contact newContact = Contact.createContact(oldContact.getName(), newPhoneNumbers.get(0));
        newContact.changePhoneNumbers(newPhoneNumbers);
        
        this.removeContact(oldContact);
        this.myContacts.put(newContact.getName(), newContact);

        System.out.println("Phone numbers for " + oldContact.getName() + " edited succesfully!");
        return true;
    }

    public boolean removeContact(Contact contact) {
        Contact foundContact = findContact(contact);
        if(foundContact == null) {
            System.out.println(contact.getName() +", was not found.");
            return false;
        }

        this.myContacts.remove(contact.getName());
        System.out.println(contact.getName() + ", was deleted.");
        return true;
    }

    private Contact findContact(Contact contact) {
        return myContacts.get(contact.getName());
    }


    public Contact queryContact(String name) {
        return this.myContacts.get(name);
    }

    public void printContacts() {
        System.out.println("Contact List");
        for ( String key : this.myContacts.keySet() ) {
            this.myContacts.get(key).printContact();
        }
    }

}

