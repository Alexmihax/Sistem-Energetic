package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import documents.Contract;

import java.util.ArrayList;
import java.util.List;

public final class ConcreteDistributor extends Distributor {
    private boolean isBankrupt = false;
    private final List<Contract> contracts = new ArrayList<>();

    public ConcreteDistributor(final int id, final int budget, final int contractLength,
                               final int infrastructureCost, final int productionCost) {
        super(id, budget, contractLength, infrastructureCost, productionCost);
    }


    /**
     * Method that pays the infrastructure and production costs
     */
    @JsonIgnore
    public void monthlyPay() {
        setBudget(getBudget() - getInfrastructureCost() - getProductionCost() * contracts.size());
    }

    /**
     * Method that computes the cost of the contract offered by the distributor
     * @return the cost of the contract
     */
    @JsonIgnore
    public int getContractCost() {
        if (contracts.size() != 0) {
            return (int) Math.round(Math.floor((double) getInfrastructureCost() / contracts.size())
                    + getProductionCost() + getProfit());
        }
        return getInfrastructureCost() + getProductionCost() + getProfit();
    }


    /**
     * Method that sets a Distributor to the bankrupt state
     */
    public void setIsBankrupt() {
        isBankrupt = true;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

}
