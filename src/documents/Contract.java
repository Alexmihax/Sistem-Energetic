package documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.Distributor;

public final class Contract {
    private final int consumerId;
    private final int price;
    private int remainedContractMonths;
    private final Distributor distributor;

    public Contract(final int consumerId, final Distributor distributor,
                    final int price, final int period) {
        this.consumerId = consumerId;
        this.distributor = distributor;
        this.price = price;
        this.remainedContractMonths = period;
    }

    /**
     * Method to decrease the number of months remained in the contract
     */
    public void monthPassed() {
        remainedContractMonths--;
    }

    @JsonIgnore
    public Distributor getDistributor() {
        return distributor;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public int getPrice() {
        return price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
}
