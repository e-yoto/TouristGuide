package pie.edu.touristguide.Model.StepCounter;

public class HistoryCounter {
    private int steps;
    private int kilometers;
    private int floors;
    private int miles;

    public HistoryCounter(int steps, int kilometers, int floors, int miles) {
        this.steps = steps;
        this.kilometers = kilometers;
        this.floors = floors;
        this.miles = miles;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public void incrementStep(){
        this.steps += 1;
        this.kilometers = steps/1250;
        this.floors = steps/18;
        this.miles = steps/2000;
    }

    public void resetProgress(){
        this.steps = 0;
        this.kilometers = 0;
        this.floors = 0;
        this.miles = 0;
    }
}
