package input;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ProducerChanges {
    @JsonProperty("id")
    private int id;
    @JsonProperty("energyPerDistributor")
    private int energyPerDistributor;

    public ProducerChanges() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
}
