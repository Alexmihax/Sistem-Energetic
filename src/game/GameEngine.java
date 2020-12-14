package game;

import input.Input;
import input.MonthlyInputData;

public class GameEngine {

//    public void setup(Input input) {
//        DistributorDatabase distributorDatabase = new DistributorDatabase(input);
//        ConsumerDatabase consumerDatabase = new ConsumerDatabase(input);
//    }

    public void update(Input input) {
        for (MonthlyInputData update : input.getUpdates()) {

        }
        // OutputWriter outputWriter = new OutputWriter();
        // outputWriter.displayOutput();
    }
    public void displayState() {

    }
}
