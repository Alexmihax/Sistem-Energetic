package entities;

import utils.Constants;
import input.EntityInput;

public final class ConsumerFactory implements EntityFactory<Consumer> {

    @Override
    public Consumer create(final EntityInput inputData, final String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        if (type.equals(Constants.CONCRETECONSUMER)) {
            return new ConcreteConsumer(inputData.getId(), inputData.getBudget(),
                    inputData.getIncome());
        }
        return null;
    }
}
