package ie.ulster.exam;
class Customer
{
    private String Room_Name;
    private String Capacity;
    private boolean Alcahol;
    private String Activity;

    Customer(String Room_name, String Capacity, boolean Alcahol, String Activity)
    {
        this.Room_Name = Room_Name;
        this.Capacity = Capacity;
        this.Alcahol = Alcahol;
        this.Activity = Activity;
    }
	
	public String getRoom_name() {
		return Room_Name;
	}
	
	public void setRoom_name(String Room_name) {
		this.Room_name = Room_name;
	}
	
	public String getCapacity() {
		return Capacity;
	}
	
	public void setCapacity(String Capacity) {
		this.Capacity = Capacity;
	}
	
	public boolean isAlcahol() {
		return Alcahol;
	}
	
	public void setAlcahol(boolean Alcahol) {
		this.Alcahol = Alcahol;
	}
	
	public String getActivity() {
		return Activity;
	}
	
	public void setActivity(String Activity) {
		this.Activity = Activity;
	}
}