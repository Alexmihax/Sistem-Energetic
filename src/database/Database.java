package database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.Consumer;
import entities.Distributor;
import input.Input;

import java.util.List;

public final class Database {
    private final List<Consumer> consumers;
    private final List<Distributor> distributors;

    public Database(final Input input) {
        consumers = input.getConsumers();
        distributors = input.getDistributors();
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public Distributor getCheapestDistributor() {
        int cheapestPrice = Integer.MAX_VALUE;
        Distributor cheapestDistributor = null;
        for (Distributor distributor : distributors) {
            if (!distributor.getIsBankrupt()
                 && distributor.getBudget() > 0
                 && distributor.getContractCost() < cheapestPrice) {
                cheapestPrice = distributor.getContractCost();
                cheapestDistributor = distributor;
            }
        }
        return cheapestDistributor;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }
}
