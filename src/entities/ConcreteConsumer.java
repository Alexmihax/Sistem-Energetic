package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.Constants;
import documents.Contract;

public final class ConcreteConsumer extends Consumer {
    private boolean isBankrupt = false;
    @JsonIgnore
    private Contract contract = null;
    @JsonIgnore
    private int penalty = 0;

    public ConcreteConsumer(final int id, final int budget, final int income) {
        super(id, budget, income);
    }

    /**
     * Method that makes payment between consumer and distributor
     */
    public void payDistributor() {
        int money = contract.getPrice() + penalty;
        setBudget(getBudget() - money);
        contract.getDistributor().getContractPayment(money);
        penalty = 0;
    }

    /**
     * Method that computes the contract payment for consumer
     */
    public void monthlyPay() {
        if (getBudget() >= contract.getPrice() + penalty) {
            payDistributor();
        } else if (penalty > 0) {
            setIsBankrupt();
        } else {
            setPenalty((int) Math.round(Math.floor(Constants.PENALTY * contract.getPrice())));
        }
    }

    /**
     * Method to get income for a month
     */
    public void getMonthlyIncome() {
        setBudget(getBudget() + getIncome());
    }

    /**
     * Method to set a Consumer to the bankrupt state
     */
    public void setIsBankrupt() {
        isBankrupt = true;
    }

    public void setPenalty(final int penalty) {
        this.penalty = penalty;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

}
