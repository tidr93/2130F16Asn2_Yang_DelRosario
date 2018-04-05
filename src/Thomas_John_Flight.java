import java.text.DecimalFormat;
public class Thomas_John_Flight {
    
    DecimalFormat currency = new DecimalFormat("$###,###,###,###,###.00");
    
    private int number;
    private String source, destination;
    private double fare;
    private double price;
    private final double TAX = 1.13;
    
    public Thomas_John_Flight(int _number, String _source, String _destination, double _fare) {
        
        number = _number;
        source = _source;
        destination = _destination;
        fare = _fare;
        price = fare * TAX;
        
    }
    
    public Thomas_John_Flight() {    
        
    }
    
    public void setNumber(int _number) { number = _number; }
    public void setSource(String _source) { source = _source; }
    public void setDestination(String _destination) { destination = _destination; }
    public void setFare(double _fare) { fare = _fare; price = fare * TAX; }
    
    public int getNumber() { return number; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getFare() { return fare; }
    
    public String toString() {
        
        return "Flight Number: " + number + "\nSource: " + source + 
               "\nDestination: " + destination + "\nFare: " + currency.format(fare) +
               "\nTotal Ticket Price: " + currency.format(price);
        
    }
    
    public String toShortString() {
        
        return number + " - " + source + " -> " + destination;
        
    }
    
    
    
}
