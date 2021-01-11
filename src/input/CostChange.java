package input;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CostChange {
    @JsonProperty("id")
    private int id;
    @JsonProperty("infrastructureCost")
    private int infrastructureCost;
    @JsonProperty("productionCost")
    private int productionCost;

    public CostChange() {

    }

    public int getId() {
        return id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }
}
