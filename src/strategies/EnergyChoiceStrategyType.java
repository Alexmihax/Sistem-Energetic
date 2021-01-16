package strategies;

import utils.Constants;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {
    GREEN(Constants.GREEN),
    PRICE(Constants.PRICE),
    QUANTITY(Constants.QUANTITY);
    private final String label;

    EnergyChoiceStrategyType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
