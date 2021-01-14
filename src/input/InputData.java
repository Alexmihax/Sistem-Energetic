package input;

import java.util.List;

public final class InputData {
    private final int numberOfTurns;
    private final List<EntityInput> consumers;
    private final List<EntityInput> distributors;
    private final List<EntityInput> producers;
    private final List<MonthlyInputData> updates;
    public InputData(final int numberOfTurns, final List<EntityInput> consumers,
                     final List<EntityInput> distributors, final List<EntityInput> producers,
                     final List<MonthlyInputData> updates) {
        this.numberOfTurns = numberOfTurns;
        this.distributors = distributors;
        this.consumers = consumers;
        this.producers = producers;
        this.updates = updates;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public List<EntityInput> getConsumers() {
        return consumers;
    }

    public List<EntityInput> getDistributors() {
        return distributors;
    }

    public List<EntityInput> getProducers() {
        return producers;
    }

    public List<MonthlyInputData> getUpdates() {
        return updates;
    }

}
