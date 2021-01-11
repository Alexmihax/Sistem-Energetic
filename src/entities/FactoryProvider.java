package entities;

import utils.Constants;

public final class FactoryProvider {

    private FactoryProvider() {

    }

    private static final FactoryProvider instance = new FactoryProvider();

    /**
     * method for the singleton design
     * @return instance of the FactoryProvider
     */
    public static FactoryProvider getInstance() {
        return instance;
    }

    /**
     *
     * @param entity the type for which we need the factory
     * @return the factory for the specified entity
     */
    public EntityFactory<?> getFactory(final String entity) {
        if (entity == null || entity.isEmpty()) {
            return null;
        }
        if (entity.equals(Constants.CONSUMER)) {
            return new ConsumerFactory();
        } else if (entity.equals(Constants.DISTRIBUTOR)) {
            return new DistributorFactory();
        }
        return null;
    }
}
