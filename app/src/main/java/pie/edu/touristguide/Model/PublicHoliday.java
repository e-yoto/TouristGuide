package pie.edu.touristguide.Model;

/**
 * @author Sebastien El-Hamaoui
 * Public Holiday POJO object.
 */

public class PublicHoliday {
    //Define the variables.
    private String holidayName;
    private String holidayDate;

    //Constructor accepting the 2 values for the cards.
    public PublicHoliday(String holidayName, String holidayDate) {
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
    }

    //Getters and Setters.
    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    //Returns a string with the values.
    @Override
    public String toString() {
        return "PublicHoliday{" +
                "title='" + holidayName + '\'' +
                ", holidayDate='" + holidayDate + '\'' +
                '}';
    }
}
