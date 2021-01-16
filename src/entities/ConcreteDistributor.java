package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import documents.Contract;
import strategies.EnergyChoiceStrategy;
import strategies.EnergyChoiceStrategyFactory;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public final class ConcreteDistributor extends Distributor {
    private boolean isBankrupt = false;
    private final List<Contract> contracts = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Producer> producerList = new ArrayList<>();
    private int contractCost;
    @JsonIgnore
    private int productionCost;
    @JsonIgnore
    private boolean needsUpdate = true;

    public ConcreteDistributor(final int id, final int budget, final int contractLength,
                       final int infrastructureCost, final int energyNeededKW,
                       final String producerStrategy) {
        super(id, budget, contractLength, infrastructureCost, energyNeededKW, producerStrategy);
    }


    /**
     * Method to apply the strategy for choosing new producers
     * @param newProducerList with the new producers
     */
    public void applyStrategy(ArrayList<Producer> newProducerList) {
        EnergyChoiceStrategyFactory factory = EnergyChoiceStrategyFactory.getInstance();
        EnergyChoiceStrategy strategy = factory.create(getProducerStrategy());
        producerList =  strategy.chooseProducers(newProducerList,
                getEnergyNeededKW());
        needsUpdate = false;
        computeProductionCost();
        for (Producer producer : producerList) {
            producer.getDistributorList().add(this);
            producer.addObserver(this);
        }
    }

    /**
     * Method to pay the energy producer
     */
    public void monthlyPay() {
        setBudget(getBudget() - getInfrastructureCost() - productionCost * contracts.size());
    }

    /**
     * Method to compute the contract cost
     */
    public void computeContractCost() {
        if (contracts.size() != 0) {
            contractCost = (int) Math.round(Math.floor((double) getInfrastructureCost()
                    / contracts.size())
                    + productionCost + getProfit());
        } else {
            contractCost = getInfrastructureCost() + productionCost + getProfit();
        }
    }

    /**
     * Method to compute the production cost
     */
    public void computeProductionCost() {
        float cost = 0;
        for (Producer producer : producerList) {
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
        }
        productionCost = (int) Math.round(Math.floor(cost / Constants.FACTOR));
    }

    /**
     *
     * @return profit
     */
    @JsonIgnore
    public int getProfit() {
        return (int) Math.round(Math.floor(Constants.PROFIT * productionCost));
    }

    public int getContractCost() {
        return contractCost;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    /**
     * Method that sets a Distributor to the bankrupt state
     */
    public void setIsBankrupt() {
        isBankrupt = true;
    }

    public boolean getNeedsUpdate() {
        return needsUpdate;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public ArrayList<Producer> getProducerList() {
        return producerList;
    }

    @Override
    public void update(Observable o, Object arg) {
        needsUpdate = true;
    }
}
