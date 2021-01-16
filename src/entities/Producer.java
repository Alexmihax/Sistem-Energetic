package entities;

import utils.Utils;

import java.util.ArrayList;
import java.util.Observable;

public abstract class Producer extends Observable {
    private final int id;
    private final EnergyType energyType;
    private final int maxDistributors;
    private final float priceKW;
    private int energyPerDistributor;
    public Producer(int id, String energyType, int maxDistributors,
                    float priceKW, int energyPerDistributor) {
        this.id = id;
        this.energyType = Utils.stringToEnergyType(energyType);
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    /**
     * Method for the Observer pattern
     */
    public abstract void updated();

    /**
     * Method that registers the monthly stats
     * @param month of the stats
     */
    public abstract void addMonthlyStats(int month);

    /**
     *
     * @return the distributor list
     */
    public abstract ArrayList<Distributor> getDistributorList();

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the source of the energy
     */
    public EnergyType getEnergyType() {
        return energyType;
    }

    /**
     *
     * @return maximum number of distributors
     */
    public int getMaxDistributors() {
        return maxDistributors;
    }

    /**
     *
     * @return the price of a KW
     */
    public float getPriceKW() {
        return priceKW;
    }

    /**
     *
     * @return the energy per distributor
     */
    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    /**
     *
     * @param energyPerDistributor new energy per distributor
     */
    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

}
