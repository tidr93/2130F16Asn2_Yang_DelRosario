import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 *
 * @author johnyang
 */
public class NewFXMain extends Application {
    
    // Global vars
    static List<Thomas_John_TravelReservations> masterList = new ArrayList<>();
    static String dateFormat = "dd/MM/yyyy";
    static DecimalFormat currency = new DecimalFormat("$###,###,###,###.00");
    static SimpleDateFormat format = new SimpleDateFormat(dateFormat);
    static List<Thomas_John_TravelReservations> tempResults = new ArrayList<>();
    static List<String> tempResultStrings = new ArrayList<>();
    
    // Main Menu elements
    protected GridPane mainMenuPane = new GridPane();
    protected Scene menuScene = new Scene(mainMenuPane, 250, 400);
    protected Button mainAdd = new Button("Add a Reservation");
    protected Button mainSearch = new Button("Search for a Reservation");
    protected Button mainExit = new Button("Exit");
    
    protected Button menuBack = new Button ("Back");
    
    // Add Reservation elements
    protected GridPane addPane = new GridPane();
    protected Scene addScene = new Scene (addPane, 500, 550);
    protected Label lblAdd = new Label("Enter Reservation Details");
    protected Label lblFirstName = new Label ("First Name:");
    protected Label lblLastName = new Label ("Last Name:");
    protected Label lblAge = new Label("Age:");
    protected Label lblFlightNum = new Label("Flight Number:");
    protected Label lblSource = new Label("Source Location:");
    protected Label lblDestination = new Label("Destination Location:");
    protected Label lblDate = new Label("Travel Date (" + dateFormat + ") only");
    protected Label lblFare = new Label("Fare Amount: $");
    protected TextField txtFirstName = new TextField();
    protected TextField txtLastName = new TextField();
    protected TextField txtAge = new TextField();
    protected TextField txtFlightNumber = new TextField();
    protected TextField txtSource = new TextField();
    protected TextField txtDestination = new TextField();
    protected TextField txtTravelDate = new TextField();
    protected TextField txtFare = new TextField();
    protected Button addSubmit = new Button ("Submit");
    
    
    // Search Reservation elements
    protected GridPane searchPane = new GridPane();
    protected Scene searchScene = new Scene (searchPane, 500, 550);
    protected Label searchLabel = new Label("Select search parameters and enter search term: ");
    protected ComboBox searchParams = new ComboBox();
    protected TextField searchSearchField = new TextField();
    protected Button searchSearchBtn = new Button("Search");
    protected Button searchDisplayAll = new Button("Display All");
    protected ListView<String> searchResults = new ListView<String>();
    protected Button searchView = new Button("Show Details");
    protected Button searchDelete = new Button("Delete Selected");
    
    // Edit Elements
    Thomas_Alert searchResult;
    protected GridPane editPane = new GridPane();
    protected ComboBox editParams = new ComboBox();
    protected TextField editField = new TextField();
    protected Button editBtn = new Button("Save Changes");
    
    // Helper functions and event handlers
    // Type Validation
    public boolean validateStr(String val) {
        
        if (val.equalsIgnoreCase("")) return false;
        return true;
        
    }
    public boolean validateInt(String val) {
        
        try {
            
            int temp = Integer.parseInt(val);
            
            if (temp > 0) return true;
            return false;
            
        } catch (NumberFormatException e) {
            
            return false;
            
        }
        
    }
    public boolean validateDouble(String val) {
        
        try {
            
            double temp = Double.parseDouble(val);
            
            if (temp > 0.0) return true;
            return false;
            
        } catch (NumberFormatException e) {
            
            return false;
            
        }
        
    }
    public boolean validateDate(String val) {
        
        try {
            
            Date temp = format.parse(val);
            Date present = new Date();
            if (temp.before(present)) {
            
                return false;
            
            }
            
            return true;
            
        } catch (ParseException e) {
            
            return false;
            
        }
        
    }
    // Initializers to reset form windows
    public void addInitialize() {
        
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAge.setText("");
        txtFlightNumber.setText("");
        txtSource.setText("");
        txtDestination.setText("");
        txtTravelDate.setText("");
        txtFare.setText("");
        
        try {
            
            addPane.add(menuBack, 0, 9);
            
        } catch (IllegalArgumentException e) { }
        
    }
    public void searchInitialize() {
        
        searchSearchField.setDisable(true);
        searchParams.setValue("Select a parameter...");
        searchSearchBtn.setDisable(true);
        searchResults.setDisable(true);
        searchView.setDisable(true);
        searchDelete.setDisable(true);
        
        // Populate output masterList with current masterList of reservations
        populateList();
        try {
            
            searchPane.add(menuBack, 0,2);
            
        } catch (IllegalArgumentException e) { }
    }
    
    public void populateList() {
        
        tempResults.clear();
        searchResults.getItems().clear();
        tempResultStrings.clear();
        
        if (masterList.size() > 0) {
            
            tempResults.addAll(masterList);
        
            for (int i = 0; i < masterList.size(); i++) {
            
                tempResultStrings.add(masterList.get(i).toShortString(format));
            
            } 
            
            searchResults.getItems().addAll(tempResultStrings);
            
            searchResults.setDisable(false);
            
        }
        
        if (masterList.size() == 0) {
            
            searchSearchField.setDisable(true);
            searchSearchField.setText("");
            searchView.setDisable(true);
            searchDelete.setDisable(true);
            searchSearchBtn.setDisable(true);
            
        }
        
    }
    
    public void searchHelperStart() {
        
        tempResults.clear();
        tempResultStrings.clear();
        
    }
    
    public void searchHelperMatchFound(int i) {
        
        tempResults.add(masterList.get(i));
        tempResultStrings.add(masterList.get(i).toShortString(format));
        
    }
    
    public void searchHelperEnd(String searchParam, String searchTerm) {
        
        if (tempResults.size() == 0) {
            
            Error("Could not find reservation matching " + searchParam + " of " + searchTerm + ".");
            populateList();
            return;
                                
        }
        searchResults.getItems().clear();
        searchResults.getItems().addAll(tempResultStrings);
        
    }
    
    public void addSuccess(String msg) {
        
        Alert addSuccess = new Alert(AlertType.INFORMATION);
        addSuccess.setTitle("Success!");
        addSuccess.setHeaderText(null);
        addSuccess.setContentText(msg);
        addSuccess.showAndWait();
        
    }
    
    public void Error(String msg) {
        
        Alert addError = new Alert(AlertType.INFORMATION);
        addError.setTitle("Error");
        addError.setHeaderText(null);
        addError.setContentText(msg);
        addError.showAndWait();
        
    }
    
    public void searchResultAlert(Thomas_John_TravelReservations obj) {
        
        editInitialize();
        searchResult = new Thomas_Alert(AlertType.INFORMATION, obj);
        searchResult.setTitle("Reservation Details");
        searchResult.setHeaderText(obj.toString(format));
        searchResult.getDialogPane().setContent(editPane);
        searchResult.showAndWait();
        
    }
    
    public void editInitialize() {
        
        editParams.setValue("Select a detail to edit...");
        editBtn.setDisable(true);
        editField.setText("");
        editField.setDisable(true);
        
    }
    
    public void editNotification(String editParam, String editTerm) {
        
        Alert editNotify = new Alert(AlertType.INFORMATION);
        editNotify.setTitle("Edit Success");
        editNotify.setHeaderText(null);
        editNotify.setContentText(editParam + " changed to " + editTerm + " successfully.");
        editNotify.showAndWait();
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        // Main Menu
        mainMenuPane.setHgap(5);
        mainMenuPane.setVgap(5);
        mainMenuPane.add(new Label("Travel Reservation System Alpha 0.1"), 0,0);
        mainMenuPane.add(mainAdd, 0,2);
        mainMenuPane.add(mainSearch, 0,3);
        mainMenuPane.add(mainExit,0,4);
        mainMenuPane.setAlignment(Pos.CENTER);
        
        
        // Add Reservation
        
        addPane.setHgap(5);
        addPane.setVgap(5);
        addPane.add(lblAdd, 0,0,2,1);
        addPane.add(lblFirstName, 0, 1);
        addPane.add(txtFirstName, 1, 1);
        addPane.add(lblLastName, 0,2);
        addPane.add(txtLastName, 1, 2);
        addPane.add(lblAge, 0,3);
        addPane.add(txtAge, 1, 3);
        addPane.add(lblFlightNum, 0,4);
        addPane.add(txtFlightNumber, 1, 4);
        addPane.add(lblSource, 0,5);
        addPane.add(txtSource, 1, 5);
        addPane.add(lblDestination, 0,6);
        addPane.add(txtDestination, 1, 6);
        addPane.add(lblDate, 0,7);
        addPane.add(txtTravelDate, 1, 7);
        addPane.add(lblFare, 0,8);
        addPane.add(txtFare, 1, 8);
        addPane.add(addSubmit, 1, 9);
        
        addPane.setAlignment (Pos.CENTER);
        
        // Search reservation
        searchPane.setHgap(3);
        searchPane.setVgap(5);
        searchPane.setAlignment(Pos.CENTER);
        
        searchParams.setEditable(false);
        searchParams.getItems().addAll(
                
                "Passenger Name", "Flight Number", "Flight Source", "Flight Destination"
        
        );
        

        searchPane.add(searchLabel, 0, 0, 4, 1);
        searchPane.add(searchParams, 0, 1, 2, 1);
        searchPane.add(searchSearchField, 2, 1, 2, 1);
        searchPane.add(searchDisplayAll, 3, 2, 1, 1);
        searchPane.add(searchSearchBtn, 2,2,1,1);
        searchPane.add(searchResults, 0, 3, 4, 1);
        searchPane.add(searchView, 0, 4, 1, 1);
        searchPane.add(searchDelete, 2, 4, 1, 1);
        
        editPane.add(new Label("Select properties to edit:"), 0, 0, 2, 2);
        editPane.setHgap(3);
        editPane.setVgap(5);
        editPane.add(editParams, 0, 1, 1, 1);
        editParams.getItems().addAll(
                
                "Travel Date", "Flight Number", "Source", "Destination",
                "Fare", "First Name", "Last Name", "Age"
        
        );
        editParams.setEditable(false);
        editPane.add(editField, 1,1,1,1);
        editPane.add(editBtn, 0,2,1,1);
        
        // Event handlers
        // Main Menu
        menuBack.setOnAction(
            new EventHandler<ActionEvent>() {
                
                public void handle(ActionEvent e) {
                    
                    primaryStage.setScene(menuScene);
                    
                }
                
            }
        
        );
        mainAdd.setOnAction(
            new EventHandler<ActionEvent>() {
            
                public void handle(ActionEvent e) {
                    addInitialize();
                    primaryStage.setScene(addScene);
                    
                }
                
            }     
        );
        mainSearch.setOnAction(new EventHandler<ActionEvent>() {
                
                public void handle(ActionEvent e) {
                    searchInitialize();
                    primaryStage.setScene(searchScene);
                    
                }
                
            }
        
        );
        mainExit.setOnAction(
            new EventHandler<ActionEvent>() {
                
                public void handle(ActionEvent e) {
                    
                    System.exit(0);
                    
                }
                
            }
        
        );
        
        // Add Reservation
        addSubmit.setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent e) {
                
                // Validate all user input
                if (!validateInt(txtAge.getText())) {
                    
                    Error("Age has invalid input of " + txtAge.getText() + ". Please enter a valid number.");
                    return;
                    
                }
                if (!validateInt(txtFlightNumber.getText())) {
                    
                    Error("Flight Number has invalid input of " + txtFlightNumber.getText() + ". Please enter a valid number.");
                    return;
                    
                }
                if (!validateDate(txtTravelDate.getText())) {
                    
                    Error("Date has invalid input or format of " + txtTravelDate.getText() + ". Please enter valid future date of format (" + dateFormat + ").");
                    return;
                    
                }
                if (!validateDouble(txtFare.getText())) {
                    
                    Error("Fare has invalid input of " + txtFare.getText() + ". Please enter a valid number.");
                    return;
                    
                }
                if (!(validateStr(txtFirstName.getText()) || validateStr(txtLastName.getText()) || validateStr(txtSource.getText()) || validateStr(txtDestination.getText()))) {
                    
                    Error("One or more fields are empty. Please correct your input.");
                    return;
                    
                }
                
                try {
                    
                    masterList.add(new Thomas_John_TravelReservations(
                            format.parse(txtTravelDate.getText()), Integer.parseInt(txtFlightNumber.getText()), 
                            txtSource.getText(), txtDestination.getText(), Double.parseDouble(txtFare.getText()),
                            txtFirstName.getText(), txtLastName.getText(), Integer.parseInt(txtAge.getText())));
                    
                    addSuccess("Successfully added new reservation. Returning to main menu.");
                    primaryStage.setScene(menuScene);
                    
                } catch (ParseException ex) {
                    
                    Error("Error inserting new reservation.");
                    
                }
                
            }
            
        }
        
        );
        
        
        // Search Reservation
        searchParams.getSelectionModel().selectedItemProperty().addListener(
        
                new ChangeListener<String>() {
                    
                    public void changed(ObservableValue<? extends String> ov,
                            String old_val, String new_val) {
                        
                        searchSearchField.setDisable(false);
                        searchSearchBtn.setDisable(false);
                        
                    }
                    
                }
        
        );
        
        // Search the masterList handler
        searchSearchBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent e) {
                
                String searchParam = searchParams.getSelectionModel().getSelectedItem().toString();
                String searchTerm = searchSearchField.getText();
                
                // Discern and validate input
                switch(searchParam) {
                    
                    case "Flight Number": 
                        
                        if (validateInt(searchTerm) == false) {
                            
                            Error("Invalid input for selected parameter.");
                            return;
                        
                        } else {
                            // Search and return all matching IDs to a display masterList
                            // Build a masterList and use indexOf to find result in larger masterList     
                            searchHelperStart();
                            int search = 0;
                            try { search = Integer.parseInt(searchTerm); } catch (NumberFormatException ex) {}
                            for (int i = 0; i < masterList.size(); i++) {
                                
                                if (search == masterList.get(i).getFlight().getNumber()) {
                                    
                                    searchHelperMatchFound(i);
                                    
                                    
                                }
                                
                            }
                            searchHelperEnd(searchParam, searchTerm);
                            
                        }
                        break;
                    case "Passenger Name": 
                        if (!validateStr(searchTerm)) {
                            
                            Error("Search term cannot be empty.");
                            return;
                            
                        }
                        searchHelperStart();
                        for (int i = 0; i < masterList.size(); i++) {
                            
                            if (searchTerm.equalsIgnoreCase(masterList.get(i).getPassenger().getName())) {
                                
                                searchHelperMatchFound(i);
                                
                            }
                            
                        }
                        searchHelperEnd(searchParam, searchTerm);
                        break;
                    case "Flight Source":
                        if (!validateStr(searchTerm)) {
                            
                            Error("Search term cannot be empty.");
                            return;
                            
                        }
                        searchHelperStart();
                        for (int i = 0; i < masterList.size(); i++) {
                            
                            if (searchTerm.equalsIgnoreCase(masterList.get(i).getFlight().getSource())) {
                                
                                searchHelperMatchFound(i);
                                
                            }
                            
                        }
                        searchHelperEnd(searchParam, searchTerm);
                        break;
                    case "Flight Destination":
                        if (!validateStr(searchTerm)) {
                            
                            Error("Search term cannot be empty.");
                            return;
                            
                        }
                        searchHelperStart();
                        for (int i = 0; i < masterList.size(); i++) {
                            
                            if (searchTerm.equalsIgnoreCase(masterList.get(i).getFlight().getDestination())) {
                                
                                searchHelperMatchFound(i);
                                
                            }
                            
                        }
                        searchHelperEnd(searchParam, searchTerm);
                        break;
                
                }
                
            }
            
        }
        );
        
        searchDisplayAll.setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent e) {
                
                populateList();
                
            }
            
        }
        
        
        );
        
        searchResults.getSelectionModel().selectedItemProperty().addListener(
        
                new ChangeListener<String>() {
                    
                    public void changed(ObservableValue<? extends String> ov,
                            String old_val, String new_val) {
                        
                        searchView.setDisable(false);
                        searchDelete.setDisable(false);
                        
                    }
                    
                }
        
        );
        
        searchView.setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent e) {
                
                try {
                    
                    String msg;
                
                    if (searchResults.getItems().size() != tempResults.size()) {
                        // DEBUG BLOCK
                        System.out.println("Logic error");
                        return;
                    
                    }
                
                    for (int i = 0; i < masterList.size(); i++) {
                    
                        if (searchResults.getSelectionModel().getSelectedItem().equals(masterList.get(i).toShortString(format))) {
                            
                            searchResultAlert(masterList.get(i));
                            return;
                        
                        }
                    
                    }
                    
                } catch (NullPointerException ex) {
                    
                    Error("No item selected.");
                    
                }
                
            }
            
        }
        
        
        );
        
        searchDelete.setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent e) {
                
                try {
                    
                    Thomas_John_TravelReservations temp;
                    for (int i = 0; i < searchResults.getItems().size(); i++) {
                        
                        if (searchResults.getSelectionModel().getSelectedItem().equals(tempResultStrings.get(i))) {
                            
                            temp = tempResults.get(i);
                            // Relate to master list and delete from there, and update the list
                            for (int j = 0; j < masterList.size(); j++) {
                                
                                if (temp.equals(masterList.get(i))) {
                                    
                                    masterList.remove(i);
                                    populateList();
                                    
                                }
                                
                            }
                            
                        }
                        
                    }
                    
                } catch (NullPointerException ex) {
                    
                    Error("No item selected.");
                    
                }
                
                
            }
            
        }
        
        );
        
        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent e) {
            
                String editParam = editParams.getSelectionModel().getSelectedItem().toString();
                String editTerm = editField.getText();
                
                switch(editParam) {
                    
                    case "Travel Date":
                        if (!validateDate(editTerm)) {
                            
                            Error("Invalid date specified. Please use a future date in format (" + dateFormat + ").");
                            return;
                            
                        }
                        Date _date = new Date();
                        try {
                            _date = format.parse(editTerm);
                        } catch (ParseException ex) {
                            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        searchResult.placeholder.setDate(_date);
                        break;
                    case "Flight Number":
                        if (!validateInt(editTerm)) {
                            
                            Error("Invalid input for selected parameter.");
                            return;
                            
                        }
                        int _number = 0;
                        try { _number = Integer.parseInt(editTerm); } catch (NumberFormatException ex) { }
                        searchResult.placeholder.getFlight().setNumber(_number);
                        break;
                    case "Source":
                        if (!validateStr(editTerm)) {
                            
                            Error("New " + editParam + " cannot be empty.");
                            return;
                            
                        }
                        searchResult.placeholder.getFlight().setSource(editTerm);
                        break;
                    case "Destination":
                        if (!validateStr(editTerm)) {
                            
                            Error("New " + editParam + " cannot be empty.");
                            return;
                            
                        }
                        searchResult.placeholder.getFlight().setDestination(editTerm);
                        break;
                    case "Fare":
                        if (!validateDouble(editTerm)) {
                            
                            Error("Invalid input for selected parameter.");
                            return;
                            
                        }
                        double _fare = 0;
                        try { _fare = Double.parseDouble(editTerm); } catch (NumberFormatException ex) { }
                        searchResult.placeholder.getFlight().setFare(_fare);
                        break;
                    case "First Name":
                        if (!validateStr(editTerm)) {
                            
                            Error("New " + editParam + " cannot be empty.");
                            return;
                            
                        }
                        searchResult.placeholder.getPassenger().setFirstName(editTerm);
                        break;
                    case "Last Name":
                        if (!validateStr(editTerm)) {
                            
                            Error("New " + editParam + " cannot be empty.");
                            return;
                            
                        }
                        searchResult.placeholder.getPassenger().setLastName(editTerm);
                        break;
                    case "Age":
                        if (!validateInt(editTerm)) {
                            
                            Error("Invalid input for selected parameter.");
                            return;
                            
                        }
                        int _age = 0;
                        try { _age = Integer.parseInt(editTerm); } catch (NumberFormatException ex) { }
                        searchResult.placeholder.getPassenger().setAge(_age);
                        break;
                    
                }
                editNotification(editParam, editTerm);
                searchResult.setHeaderText(searchResult.placeholder.toString(format));
            
            }
            
        }
                
        );
        
        editParams.getSelectionModel().selectedItemProperty().addListener(
        
                new ChangeListener<String>() {
                    
                    public void changed(ObservableValue<? extends String> ov,
                            String old_val, String new_val) {
                        
                        editField.setDisable(false);
                        editBtn.setDisable(false);
                        
                    }
                    
                }
        
        );
        
        try {
            // TESTING
            masterList.add(new Thomas_John_TravelReservations(format.parse("01/01/2016"), 12345, "here", "there", 213.34, "John", "Thomas", 34));
        } catch (ParseException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Show main
        primaryStage.setTitle("Travel Reservation System 0.1 alpha");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

// Dummy Alert class holds reference to reservation object for future reference in edits
class Thomas_Alert extends Alert {
    
    public Thomas_John_TravelReservations placeholder;
    
    public Thomas_Alert(AlertType alertType, Thomas_John_TravelReservations obj) {
        
        super(alertType);
        placeholder = obj;
        
    }
    
}