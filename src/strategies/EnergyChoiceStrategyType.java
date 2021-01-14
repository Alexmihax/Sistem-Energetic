package strategies;

import utils.Constants;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {
    GREEN(Constants.GREEN),
    PRICE(Constants.PRICE),
    QUANTITY(Constants.QUANTITY);
    public final String label;

    EnergyChoiceStrategyType(String label) {
        this.label = label;
    }
}
