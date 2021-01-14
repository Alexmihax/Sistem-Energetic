package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import strategies.EnergyChoiceStrategyType;
import utils.Utils;

public abstract class Distributor {
    private final int id;
    private int budget;
    @JsonIgnore
    private int contractLength;
    @JsonIgnore
    private int infrastructureCost;
    private int energyNeededKW;
    private EnergyChoiceStrategyType producerStrategy;
    public Distributor(final int id, final int budget, final int contractLength,
                       final int infrastructureCost, final int energyNeededKW,
                       final String producerStrategy) {
        this.id = id;
        this.budget = budget;
        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = Utils.stringToEnergyChoiceStrategyType(producerStrategy);
    }


    /**
     * @param money to get
     */
    @JsonIgnore
    public void getContractPayment(final int money) {
        budget += money;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     *
     * @param budget new budget
     */
    public void setBudget(final int budget) {
        this.budget = budget;
    }

    /**
     *
     * @return contractLength
     */
    public int getContractLength() {
        return contractLength;
    }

    /**
     *
     * @param contractLength new contractLength
     */
    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    /**
     *
     * @return infrastructureCost
     */
    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    /**
     *
     * @param infrastructureCost new infrastructureCost
     */
    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id +
                ", budget=" + budget +
                ", contractLength=" + contractLength +
                ", infrastructureCost=" + infrastructureCost +
                ", energyNeededKW=" + energyNeededKW +
                ", producerStrategy=" + producerStrategy +
                '}';
    }
}
