package utils;

import entities.EnergyType;
import strategies.EnergyChoiceStrategyType;

public final class Utils {

    /**
     * for coding style
     */
    private Utils() {

    }

    /**
     * Transforms a string into an enum
     * @param type of the energy
     * @return an EnergyType enum
     */
    public static EnergyType stringToEnergyType(final String type) {
        return switch (type) {
            case "WIND" -> EnergyType.WIND;
            case "COAL" -> EnergyType.COAL;
            case "HYDRO" -> EnergyType.HYDRO;
            case "SOLAR" -> EnergyType.SOLAR;
            case "NUCLEAR" -> EnergyType.NUCLEAR;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     * @param type of the energy choice strategy
     * @return an EnergyChoiceStrategyType
     */
    public static EnergyChoiceStrategyType stringToEnergyChoiceStrategyType(final String type) {
        return switch (type) {
            case "GREEN" -> EnergyChoiceStrategyType.GREEN;
            case "PRICE" -> EnergyChoiceStrategyType.PRICE;
            case "QUANTITY" -> EnergyChoiceStrategyType.QUANTITY;
            default -> null;
        };
    }
}
