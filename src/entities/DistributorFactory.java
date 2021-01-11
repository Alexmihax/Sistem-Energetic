package entities;

import utils.Constants;
import input.EntityInput;

public final class DistributorFactory implements EntityFactory<Distributor> {

    @Override
    public Distributor create(final EntityInput inputData, final String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        if (type.equals(Constants.CONCRETEDISTRIBUTOR)) {
            return new ConcreteDistributor(inputData.getId(), inputData.getBudget(),
                    inputData.getContractLength(), inputData.getInfrastructureCost(),
                    inputData.getProductionCost());
        }
        return null;
    }
}
