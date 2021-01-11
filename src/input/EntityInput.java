package input;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class EntityInput {
    @JsonProperty("id")
    private int id;
    @JsonProperty("initialBudget")
    private int budget;
    @JsonProperty("initialInfrastructureCost")
    private int infrastructureCost;
    @JsonProperty("initialProductionCost")
    private int productionCost;
    @JsonProperty("monthlyIncome")
    private int income;
    @JsonProperty("contractLength")
    private int contractLength;

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getIncome() {
        return income;
    }

    public int getContractLength() {
        return contractLength;
    }

}
