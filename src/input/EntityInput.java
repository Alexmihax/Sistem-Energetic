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
    @JsonProperty("energyType")
    private String energyType;
    @JsonProperty("maxDistributors")
    private int maxDistributors;
    @JsonProperty("priceKW")
    private float priceKW;
    @JsonProperty("energyPerDistributor")
    private int energyPerDistributor;
    @JsonProperty("energyNeededKW")
    private int energyNeededKW;
    @JsonProperty("producerStrategy")
    private String producerStrategy;

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

    public String getEnergyType() {
        return energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public float getPriceKW() {
        return priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

}
