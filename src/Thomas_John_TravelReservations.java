import java.util.Date;
import java.text.SimpleDateFormat;
public class Thomas_John_TravelReservations {
    
    private Date date;
    private Thomas_John_Flight flight;
    private Thomas_John_Passenger passenger;
    
    public void setDate(Date _date) { date = _date; }
    public Date getDate() { return date; }
    public Thomas_John_Flight getFlight() { return flight; }
    public Thomas_John_Passenger getPassenger() { return passenger; }
    // For regular passenger
    public Thomas_John_TravelReservations(Date _date, 
                              int _number, String _source, String _destination,
                              double _fare,
                              String _firstName, String _lastName, int _age) {
        
        date = _date;
        flight = new Thomas_John_Flight(_number, _source, _destination, _fare);
        passenger = new Thomas_John_Passenger(_firstName, _lastName, _age);

    }
    
    public Thomas_John_TravelReservations() {
        
        flight = new Thomas_John_Flight();
        passenger = new Thomas_John_Passenger();
        
    }
    
    public String toString(SimpleDateFormat format) {
        
        return "Travel Date: " + format.format(date) + "\n" + 
               flight.toString() + "\n" + passenger.toString() + "\n";
        
    }
    
    public String toShortString(SimpleDateFormat format) {
        
        return format.format(date) + " - " + passenger.toShortString() + " - " + flight.toShortString();
        
    }
    
    public String test() {
        
        return "Hello World!";
        
    }
    
}
