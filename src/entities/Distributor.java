package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.Constants;

public abstract class Distributor {
    private final int id;
    private int budget;
    @JsonIgnore
    private int contractLength;
    @JsonIgnore
    private int infrastructureCost;
    @JsonIgnore
    private int productionCost;
    public Distributor(final int id, final int budget, final int contractLength,
                               final int infrastructureCost, final int productionCost) {
        this.id = id;
        this.budget = budget;
        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
    }

    /**
     *
     * @return profit
     */
    @JsonIgnore
    public int getProfit() {
        return (int) Math.round(Math.floor(Constants.PROFIT * productionCost));
    }


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
     * @param budget new budget
     */
    public void setBudget(final int budget) {
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
     * @param contractLength new contractLength
     */
    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
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
     * @return productionCost
     */
    public int getProductionCost() {
        return productionCost;
    }

    /**
     *
     * @param productionCost new productionCost
     */
    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }
}
