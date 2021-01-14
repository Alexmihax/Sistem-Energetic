package entities;

import utils.Constants;

public final class FactoryProvider {

    /**
     * for coding style
     */
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
        return switch (entity) {
            case Constants.CONSUMER -> new ConsumerFactory();
            case Constants.DISTRIBUTOR -> new DistributorFactory();
            case Constants.PRODUCER -> new ProducerFactory();
            default -> null;
        };
    }
}
