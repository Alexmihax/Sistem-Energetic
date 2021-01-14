package input;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class DistributorChanges {
    @JsonProperty("id")
    private int id;
    @JsonProperty("infrastructureCost")
    private int infrastructureCost;

    public DistributorChanges() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
}
