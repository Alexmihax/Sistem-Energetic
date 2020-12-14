package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import documents.Contract;

import java.util.ArrayList;
import java.util.List;

public final class Distributor implements Entity {
    private final int id;
    private int budget;
    private boolean isBankrupt = false;
    private final List<Contract> contracts = new ArrayList<>();
    @JsonIgnore
    private int contractLength;
    @JsonIgnore
    private int infrastructureCost;
    @JsonIgnore
    private int productionCost;

    public Distributor(final int id, final int budget, final int contractLength,
                       final int infrastructureCost, final int productCost) {
        this.id = id;
        this.budget = budget;
        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productCost;
    }

    @JsonIgnore
    public int getProfit() {
        return (int) Math.round(Math.floor(0.2 * productionCost));
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public int getContractCost() {
        if (contracts.size() != 0) {
            return (int) Math.round(Math.floor((double) infrastructureCost / contracts.size())
                    + productionCost + getProfit());
        }
        return infrastructureCost + productionCost + getProfit();
    }

    /**
     *
     */
    @JsonIgnore
    public void pay() {
        budget -= infrastructureCost + productionCost * contracts.size();
    }

    /**
     *
     * @param money
     */
    @JsonIgnore
    public void getPayment(final int money) {
        budget += money;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    /**
     *
     */
    public void setIsBankrupt() {
        isBankrupt = true;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

}
