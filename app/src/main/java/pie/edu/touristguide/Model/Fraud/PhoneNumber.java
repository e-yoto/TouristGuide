package pie.edu.touristguide.Model.Fraud;

public class PhoneNumber {
    private boolean isValid;
    private String callingCode;
    private String lineType;
    private String carrier;
    private String name;
    private String age;
    private String gender;
    private String streetOne;
    private String streetTwo;
    private String city;
    private String zip;
    private String state;
    private String country;

    public PhoneNumber (){ }


    public PhoneNumber(boolean isValid, String callingCode, String lineType, String carrier,
                       String name, String age, String gender, String streetOne, String streetTwo,
                       String city, String zip, String state, String country) {
        this.isValid = isValid;
        this.callingCode = callingCode;
        this.lineType = lineType;
        this.carrier = carrier;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.streetOne = streetOne;
        this.streetTwo = streetTwo;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.country = country;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStreetOne() {
        return streetOne;
    }

    public void setStreetOne(String streetOne) {
        this.streetOne = streetOne;
    }

    public String getStreetTwo() {
        return streetTwo;
    }

    public void setStreetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "isValid=" + isValid +
                ", callingCode='" + callingCode + '\'' +
                ", lineType='" + lineType + '\'' +
                ", carrier='" + carrier + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", streetOne='" + streetOne + '\'' +
                ", streetTwo='" + streetTwo + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
