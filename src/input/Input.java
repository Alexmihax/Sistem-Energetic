package input;

import entities.Consumer;
import entities.Distributor;

import java.util.List;

public class Input {
    private final int numberOfTurns;
    private final List<Consumer> consumers;
    private final List<Distributor> distributors;
    private final List<MonthlyInputData> updates;
    public Input(int numberOfTurns, List<Consumer> consumers, List<Distributor> distributors, List<MonthlyInputData> updates) {
        this.numberOfTurns = numberOfTurns;
        this.distributors = distributors;
        this.consumers = consumers;
        this.updates = updates;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public List<MonthlyInputData> getUpdates() {
        return updates;
    }
}
