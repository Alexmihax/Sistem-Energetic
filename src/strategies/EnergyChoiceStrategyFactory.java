package strategies;

import utils.Constants;

public class EnergyChoiceStrategyFactory {

    /**
     * for coding style
     */
    private EnergyChoiceStrategyFactory() {

    }

    private static final EnergyChoiceStrategyFactory instance = new EnergyChoiceStrategyFactory();

    /**
     * method for the singleton design
     * @return instance of EnergyChoiceStrategyFactory
     */
    public static EnergyChoiceStrategyFactory getInstance() {
        return instance;
    }

    public EnergyChoiceStrategy create(EnergyChoiceStrategyType type) {
        if (type == null) {
            return null;
        }
        return switch(type.label) {
            case (Constants.GREEN) -> new GreenStrategy();
            case (Constants.PRICE) -> new PriceStrategy();
            case (Constants.QUANTITY) -> new QuantityStrategy();
            default->null;
        };
    }
}
