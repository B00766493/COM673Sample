package ie.ulster.exam;

import java.util.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.*;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.shared.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        String connectionString
    
        = "jdbc:sqlserver://b00766493.database.windows.net:1433;"+
        "database=B00766493-assignment;"+
        "user=B00766493@b00766493;"+
        "password={Eoin2018*};"+
        "encrypt=true;"+
        "trustServerCertificate=false;"+
        "hostNameInCertificate=*.database.windows.net;"+
        "loginTimeout=30;";

        Connection connection = null;
        final VerticalLayout layout = new VerticalLayout();
        
        try 
{
	// Connect with JDBC driver to a database
	connection = DriverManager.getConnection(connectionString);
	ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM customerTable;");
// Convert the resultset that comes back into a List - we need a Java class to represent the data (Customer.java in this case)
List<Customer> customers = new ArrayList<Customer>();
// While there are more records in the resultset
while(rs.next())
{   
	// Add a new Customer instantiated with the fields from the record (that we want, we might not want all the fields, note how I skip the id)
	customers.add(new Customer(rs.getString("Room_Name"), 
				rs.getString("Capacity"), 
				rs.getBoolean("Alcahol"), 
				rs.getString("Activity")));
}// Add a label to the web app with the message and name of the database we connected to 
	//layout.addComponent(new Label("Connected to database: " + connection.getCatalog()));
// Execute a query against the database and return rows (if any) to the ResultSet
// Add my component, grid is templated with Customer
Grid<Customer> myGrid = new Grid<>();
// Set the items (List)
myGrid.setItems(customers);
// Configure the order and the caption of the grid
myGrid.addColumn(Customer::getRoom_Name).setCaption("Room ");
myGrid.addColumn(Customer::getCapacity).setCaption("Capacity");
myGrid.addColumn(Customer::getActivity).setCaption("Alcahol Allowed");
myGrid.addColumn(Customer::isAlcahol).setCaption("Feature");
myGrid.setSelectionMode(SelectionMode.MULTI);
myGrid.setSizeFull();

// Add the grid to the list
layout.addComponent(myGrid);

} 
catch (Exception e) 
{
	// This will show an error message if something went wrong
	layout.addComponent(new Label(e.getMessage()));
}
setContent(layout);

HorizontalLayout v = new HorizontalLayout(); //sub layout 1
Label logo = new Label
("<H1>Marty Party Planners</H1> <p/> <h3>Please enter the details below and click Book</h3><br>", ContentMode.HTML);

//VerticalLayout v2 = new VerticalLayout();//sub layout 2
        final TextField name = new TextField();
        name.setCaption("Name of party:");

        Label label = new Label ("B00766493");

        final TextField value = new TextField();
        value.setCaption("How many people are invited to this party:");
        value.setPlaceholder(" ");
        
        //dropdown selections
        ComboBox<String> children = new ComboBox<String>("Children Attending");
        children.setItems("Yes","No"); // the options availible 
        children.setPlaceholder("No option selected"); //same as TextField placeholder

        Slider s = new Slider("How many people are invited to this party:", 1, 200);
        s.setValue(100.0);
        //s.setWidth(s.getMax()+"px");
        s.setWidth("500px");// setslider width to 500 PIXELS
        s.addValueChangeListener(e ->{
            double x = s.getValue();
            value.setValue(""+x);
        });

        value.addValueChangeListener(e ->{
            
            double x = Double.parseDouble(value.getValue());
            if (x>s.getMax()){
               s.setMax(x);
               s.setWidth(x+"px");
            }
            else if (x<s.getMin()){
                x = s.getMin();
            }
            s.setValue(x);
        });

        Button button = new Button("Book");
        button.addClickListener(e -> {
        //if(Customer.Room_Name.length ==0){
        //return ("<strong> Please select at least one room.<strong>",
        //ContentMode.HTML);}
            //if (value.length() ==0 ){ return"Please enter party name";}
            //if (children.length()==0){return "Please 
            //confirm if children attending your party";}
            //if ((Customer.Alcahol == false)&&(children.length()!=0)){
            //    return "You cannot select any rooms serving alcahol if 
            //    children are attending";}
            //if(s> sum.Capacity){return "You have selected rooms with a max 
            //capacity of "+ sum.Capacity+ "which is not enough to hold "+ s;}; 
            //else {return "Success! The party is booked now";};
            layout.addComponent(new Label("Your party " + name.getValue() 
                    + ", is now booked"));
                    Label notyet = new Label
("<h3>Your party is not booked yet</h3><br>", ContentMode.HTML);

        });
        
       // layout.addComponents(notyet);
        v.addComponents(name, s, children); //build sub layout
  //      v2.addComponents(button, button2); //build sub layout
        layout.addComponents(logo,v,button, label); // build master layout
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}