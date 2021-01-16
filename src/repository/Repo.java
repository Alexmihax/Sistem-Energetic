package repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.Consumer;
import entities.Distributor;
import entities.EntityFactory;
import entities.FactoryProvider;
import entities.Producer;
import input.EntityInput;
import input.InputData;
import utils.Constants;

import java.util.ArrayList;

public final class Repo {
    private final ArrayList<Consumer> consumers = new ArrayList<>();
    private final ArrayList<Distributor> distributors = new ArrayList<>();
    private final ArrayList<Producer> energyProducers = new ArrayList<>();


    public Repo(final InputData input) {
        /* */
        FactoryProvider factoryProvider = FactoryProvider.getInstance();
        EntityFactory<?> factory = factoryProvider.getFactory(Constants.CONSUMER);
        for (EntityInput entityInput : input.getConsumers()) {
            consumers.add((Consumer) factory.create(entityInput,
                    Constants.CONCRETE_CONSUMER));
        }
        factory = factoryProvider.getFactory(Constants.DISTRIBUTOR);
        for (EntityInput entityInput : input.getDistributors()) {
            distributors.add((Distributor) factory.create(entityInput,
                    Constants.CONCRETE_DISTRIBUTOR));
        }
        factory = factoryProvider.getFactory(Constants.PRODUCER);
        for (EntityInput entityInput : input.getProducers()) {
            energyProducers.add((Producer) factory.create(entityInput,
                    Constants.CONCRETE_PRODUCER));
        }
    }

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    /**
     * Method that computes the the Distributor with the cheapest
     * contract cost
     * @return a Distributor object
     */
    @JsonIgnore
    public Distributor getCheapestDistributor() {
        int cheapestPrice = Integer.MAX_VALUE;
        Distributor cheapestDistributor = null;
        for (Distributor distributor : distributors) {
            if (!distributor.getIsBankrupt()
                 && distributor.getBudget() > 0) {
                distributor.computeProductionCost();
                distributor.computeContractCost();
                if (distributor.getContractCost() < cheapestPrice) {
                    cheapestPrice = distributor.getContractCost();
                    cheapestDistributor = distributor;
                }
            }
        }
        return cheapestDistributor;
    }

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public ArrayList<Producer> getEnergyProducers() {
        return energyProducers;
    }
}
