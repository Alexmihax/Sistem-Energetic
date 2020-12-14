package input;

import entities.Consumer;

import java.util.ArrayList;
import java.util.List;

public class MonthlyInputData {
    private final List<Consumer> newConsumers;
    private final List<CostChange> costChanges;

    public MonthlyInputData(List<Consumer> consumers, List<CostChange> costChange) {
        this.newConsumers = consumers;
        this.costChanges = costChange;
    }

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public List<CostChange> getCostChanges() {
        return costChanges;
    }
}
