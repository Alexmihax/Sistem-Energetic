package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import documents.Contract;

public abstract class Consumer {
    private final int id;
    private int budget;
    @JsonIgnore
    private final int income;
    public Consumer(final int id, final int budget, final int income) {
        this.id = id;
        this.budget = budget;
        this.income = income;
    }

    /**
     * Method that makes payment between consumer and distributor
     */
    public abstract void payDistributor();

    /**
     * Method that computes the contract payment for consumer
     */
    public abstract void monthlyPay();

    /**
     * Method to get income for a month
     */
    public abstract void getMonthlyIncome();

    /**
     * Method to set a Consumer to the bankrupt state
     */
    public abstract void setIsBankrupt();

    /**
     *
     * @return the bankruptcy state of the consumer
     */
    public abstract boolean getIsBankrupt();

    /**
     *
     * @return the contract of the consumer with trhe distributor
     */
    public abstract Contract getContract();

    /**
     *
     * @param contract new contract
     */
    public abstract void setContract(Contract contract);

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
     * @param budget the new budget
     */
    public void setBudget(final int budget) {
        this.budget = budget;
    }

    /**
     *
     * @return income
     */
    public int getIncome() {
        return income;
    }
}
