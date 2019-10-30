package pie.edu.touristguide.Model;

public class Currency {
    String initials;
    String name;
    double rate;
    double amount;

    public Currency(String initials, String name){
        this.initials = initials;
        this.name = name;
    }

    public Currency(String initials, String name, double rate, double amount) {
        this.initials = initials;
        this.name = name;
        this.rate = rate;
        this.amount = amount;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
