package pie.edu.touristguide.Model.StepCounter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

public class DailyCounter {
    private int steps;
    private int goal;
    private int calories;
    private int stepsLeft;
    private Date date;

    public DailyCounter(int steps, int goal, int calories, int stepsLeft){
        this.steps = steps;
        this.goal = goal;
        this.calories = calories;
        this.stepsLeft = stepsLeft;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getGoal() {
        return this.goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
        this.stepsLeft = goal - steps;
    }

    public int getCalories() {
        return this.calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getStepsLeft() {
        return this.stepsLeft;
    }

    public void setStepsLeft(int stepsLeft) {
        this.stepsLeft = stepsLeft;
    }

    public Date getDate() {
        return date;
    }

    public void incrementStep(){
        this.steps += 1;
        this.calories = steps/20;
        this.stepsLeft = goal - steps;
    }

    public void resetProgress(){
        this.steps = 0;
        this.calories = 0;
        this.stepsLeft = goal - steps;
    }

    @Override
    public String toString() {
        return "DailyCounter{" +
                "steps=" + steps +
                ", goal=" + goal +
                ", calories=" + calories +
                ", stepsLeft=" + stepsLeft +
                ", date=" + date +
                '}';
    }
}
