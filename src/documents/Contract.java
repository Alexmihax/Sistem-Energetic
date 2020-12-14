package documents;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class Contract {
    private int consumerId;
    private int price;
    private int remainedContractMonths;
    private int distributorId;

    public Contract(final int consumerId, final int distributorId,
                    final int price, final int period) {
        this.consumerId = consumerId;
        this.distributorId = distributorId;
        this.price = price;
        this.remainedContractMonths = period;
    }

    public Contract() {

    }
    @JsonIgnore
    public int getDistributorId() {
        return distributorId;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public int getPrice() {
        return price;
    }

    /**
     *
     */
    public void monthPassed() {
        remainedContractMonths--;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }


}
