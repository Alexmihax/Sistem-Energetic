package entities;

import input.EntityInput;
import utils.Constants;

public final class ProducerFactory implements EntityFactory<Producer> {
    @Override
    public Producer create(final EntityInput inputData, final String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        if (type.equals(Constants.CONCRETE_PRODUCER)) {
            return new ConcreteProducer(inputData.getId(), inputData.getEnergyType(),
                    inputData.getMaxDistributors(), inputData.getPriceKW(),
                    inputData.getEnergyPerDistributor());
        }
        return null;
    }
}
