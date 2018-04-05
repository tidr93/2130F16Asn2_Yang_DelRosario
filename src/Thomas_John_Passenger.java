public class Thomas_John_Passenger {
    
    private String firstName;
    private String lastName;
    private int age;
    
    public Thomas_John_Passenger(String _firstName, String _lastName, int _age) {
        
        firstName = _firstName;
        lastName = _lastName;
        age = _age;
        
    }
    
    public Thomas_John_Passenger() {
     
    }
    
    public void setFirstName(String _firstName) { firstName = _firstName; }
    public void setLastName(String _lastName) { lastName = _lastName; }
    public void setAge(int _age) { age = _age; }
    
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getName() { return firstName + " " + lastName; }
    public int getAge() { return age; }
    
    public String toString() {
        
        String output = "Passenger Details:\nFirst Name: " + firstName +
                        "\nLast Name: " + lastName + "\nAge: " + age;
        
        return output;
        
    }
    // Only prints full name
    public String toShortString() {
        
        return firstName + " " + lastName;
        
    }
    
}
