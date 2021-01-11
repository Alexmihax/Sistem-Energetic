package repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.Constants;
import entities.EntityFactory;
import entities.ConcreteConsumer;
import entities.ConcreteDistributor;
import entities.FactoryProvider;
import input.EntityInput;
import input.InputData;

import java.util.ArrayList;
import java.util.List;

public final class Repo {
    private final List<ConcreteConsumer> consumers = new ArrayList<>();
    private final List<ConcreteDistributor> distributors = new ArrayList<>();


    public Repo(final InputData input) {
        FactoryProvider factoryProvider = FactoryProvider.getInstance();
        EntityFactory<?> factory = factoryProvider.getFactory(Constants.CONSUMER);
        assert factory != null;
        for (EntityInput entityInput : input.getConsumers()) {
            consumers.add((ConcreteConsumer) factory.create(entityInput,
                    Constants.CONCRETECONSUMER));
        }
        factory = factoryProvider.getFactory(Constants.DISTRIBUTOR);
        for (EntityInput entityInput : input.getDistributors()) {
            distributors.add((ConcreteDistributor) factory.create(entityInput,
                    Constants.CONCRETEDISTRIBUTOR));
        }
    }

    public List<ConcreteConsumer> getConsumers() {
        return consumers;
    }

    /**
     * Method that computes the the Distributor with the cheapest
     * contract cost
     * @return a Distributor object
     */
    @JsonIgnore
    public ConcreteDistributor getCheapestDistributor() {
        int cheapestPrice = Integer.MAX_VALUE;
        ConcreteDistributor cheapestDistributor = null;
        for (ConcreteDistributor distributor : distributors) {
            if (!distributor.getIsBankrupt()
                 && distributor.getBudget() > 0
                 && distributor.getContractCost() < cheapestPrice) {
                cheapestPrice = distributor.getContractCost();
                cheapestDistributor = distributor;
            }
        }
        return cheapestDistributor;
    }

    public List<ConcreteDistributor> getDistributors() {
        return distributors;
    }
}
