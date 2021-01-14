package input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class MonthlyInputData {
    @JsonProperty("newConsumers")
    private List<EntityInput> newEntities;
    @JsonProperty("distributorChanges")
    private List<DistributorChanges> distributorChanges;
    @JsonProperty("producerChanges")
    private List<ProducerChanges> producerChanges;


    public MonthlyInputData() {

    }

    public List<EntityInput> getNewConsumers() {
        return newEntities;
    }

    public List<DistributorChanges> getDistributorChanges() {
        return distributorChanges;
    }

    public List<ProducerChanges> getProducerChanges() {
        return producerChanges;
    }
}
