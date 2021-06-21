import java.util.ArrayList;

public class Contact {

    private String name;
    private ArrayList<String> phoneNumbers;

    public Contact(String name, ArrayList<String> phoneNumbers) {
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public static Contact createContact(String name, String phoneNumber) {
        ArrayList<String> newPhoneNumbers = new ArrayList<String>();
        if (phoneNumber.trim() !=""){
            newPhoneNumbers.add(phoneNumber);
        }
        return new Contact(name, newPhoneNumbers);
    }

    public String getName() {
        return name;
    }

    public void changeName(String newName) {
        this.name = newName;
    }


    public ArrayList<String> getPhoneNumbers(){
        return phoneNumbers;
    }

    
    public void changePhoneNumbers(ArrayList<String> newPhoneNumbers) {
        this.phoneNumbers = newPhoneNumbers;
    }

    public void addAnotherPhoneNumber(String newPhoneNumber){
        if (newPhoneNumber.trim() == ""){
            return;
        }
        this.phoneNumbers.add(newPhoneNumber);
    }

    public void deletePhoneNumber(String phoneNumberToDelete){
        if (phoneNumberToDelete.trim() == ""){
            return;
        }

        for (int i = 0; i < this.phoneNumbers.size(); i++) {
            if(phoneNumberToDelete.equals(this.phoneNumbers.get(i))){
                this.phoneNumbers.remove(i);
            }
        } 
    }


    public void printContact() {
        System.out.println(this.name);
        for (String phone : phoneNumbers) {
            System.out.println(phone);
        }
    }

}
