package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import documents.Contract;
import strategies.EnergyChoiceStrategyType;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public abstract class Distributor implements Observer {
    private final int id;
    private int budget;
    @JsonIgnore
    private final int contractLength;
    @JsonIgnore
    private int infrastructureCost;
    private final int energyNeededKW;
    private final EnergyChoiceStrategyType producerStrategy;
    public Distributor(final int id, final int budget, final int contractLength,
                       final int infrastructureCost, final int energyNeededKW,
                       final String producerStrategy) {
        this.id = id;
        this.budget = budget;
        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = Utils.stringToEnergyChoiceStrategyType(producerStrategy);
    }

    /**
     * Method to apply the strategy for choosing new producers
     * @param newProducerList with the new producers
     */
    public abstract void applyStrategy(ArrayList<Producer> newProducerList);

    /**
     * Method that pays the infrastructure and production costs
     */
    public abstract void monthlyPay();

    /**
     * Method to compute the contract cost
     */
    public abstract void computeContractCost();

    /**
     * Method that computes the production cost
     */
    public abstract void computeProductionCost();

    /**
     *
     * @return contract cost
     */
    public abstract int getContractCost();

    /**
     *
     * @return the update state
     */
    public abstract boolean getNeedsUpdate();

    /**
     *
     * @return the bankruptcy state
     */
    public abstract boolean getIsBankrupt();

    /**
     * Method that sets a Distributors to the bankrupt state
     */
    public abstract void setIsBankrupt();

    /**
     *
     * @return the contracts list
     */
    public abstract List<Contract> getContracts();

    /**
     *
     * @return the producers list
     */
    public abstract ArrayList<Producer> getProducerList();

    /**
     * @param money to get
     */
    @JsonIgnore
    public void getContractPayment(final int money) {
        budget += money;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     *
     *
     * @param budget new budget
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     *
     * @return contractLength
     */
    public int getContractLength() {
        return contractLength;
    }

    /**
     *
     * @return infrastructureCost
     */
    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    /**
     *
     * @param infrastructureCost new infrastructureCost
     */
    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     *
     * @return energy
     */
    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    /**
     *
     * @return the strategy for choosing producers
     */
    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

}
