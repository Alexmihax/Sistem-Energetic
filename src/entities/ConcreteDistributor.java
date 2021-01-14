package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import documents.Contract;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class ConcreteDistributor extends Distributor implements Observer {
    private boolean isBankrupt = false;
    private final List<Contract> contracts = new ArrayList<>();
    @JsonIgnore
    private ArrayList<ConcreteProducer> producerList = new ArrayList<>();
    private int contractCost;
    @JsonIgnore
    private int productionCost;
    @JsonIgnore
    private boolean needsUpdate = true;
    public ConcreteDistributor(final int id, final int budget, final int contractLength,
                       final int infrastructureCost, final int energyNeededKW,
                       final String producerStrategy) {
        super(id, budget, contractLength, infrastructureCost, energyNeededKW, producerStrategy);
    }


    /**
     * Method that pays the infrastructure and production costs
     */
    @JsonIgnore
    public void monthlyPay() {
        setBudget(getBudget() - getInfrastructureCost() - getProductionCost() * contracts.size());
    }

    public int getContractCost() {
        return contractCost;
    }

    public int computeContractCost() {

        if (contracts.size() != 0) {
            contractCost = (int) Math.round(Math.floor((double) getInfrastructureCost() / contracts.size())
                    + getProductionCost() + getProfit());
            return (int) Math.round(Math.floor((double) getInfrastructureCost() / contracts.size())
                    + getProductionCost() + getProfit());
        }
        contractCost = getInfrastructureCost() + getProductionCost() + getProfit();
        return getInfrastructureCost() + getProductionCost() + getProfit();
    }

    /**
     *
     */
    public void computeProductionCost() {
        float cost = 0;
        for (Producer producer : producerList) {
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
        }
        productionCost = (int) Math.round(Math.floor(cost / 10));
    }

    /**
     *
     * @return profit
     */
    @JsonIgnore
    public int getProfit() {
        return (int) Math.round(Math.floor(Constants.PROFIT * productionCost));
    }

    /**
     * Method that sets a Distributor to the bankrupt state
     */
    public void setIsBankrupt() {
        isBankrupt = true;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public boolean getNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public ArrayList<ConcreteProducer> getProducerList() {
        return producerList;
    }

    public void setProducerList(ArrayList<ConcreteProducer> producerList) {
        this.producerList = producerList;
    }

    @Override
    public void update(Observable o, Object arg) {
        needsUpdate = true;
    }

    @Override
    public String toString() {
        return super.toString() + "ConcreteDistributor{" +
                "isBankrupt=" + isBankrupt +
                ", contracts=" + contracts +
                ", producerList=" + producerList +
                ", contractCost=" + contractCost +
                ", productionCost=" + productionCost +
                ", needsUpdate=" + needsUpdate +
                '}';
    }
}
