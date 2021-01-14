package entities;

import utils.Utils;

import java.util.Observable;

public abstract class Producer extends Observable {
    private final int id;
    private final EnergyType energyType;
    private final int maxDistributors;
    private final float priceKW;
    private int energyPerDistributor;
    public Producer(int id, String energyType, int maxDistributors, float priceKW, int energyPerDistributor) {
        this.id = id;
        this.energyType = Utils.stringToEnergyType(energyType);
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public float getPriceKW() {
        return priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", energyType=" + energyType +
                ", maxDistributors=" + maxDistributors +
                ", priceKW=" + priceKW +
                ", energyPerDistributor=" + energyPerDistributor +
                '}';
    }
}
