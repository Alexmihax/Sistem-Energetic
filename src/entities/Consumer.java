package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import documents.Contract;

public final class Consumer implements Entity {
    private final int id;
    private boolean isBankrupt;
    private int budget;
    @JsonIgnore
    private final int income;
    @JsonIgnore
    private Contract contract = new Contract();
    @JsonIgnore
    private int penalty = 0;

    @JsonIgnore
    public int getPenalty() {
        return penalty;
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

    public Consumer(final int id, final int budget, final int income) {
        this.id = id;
        this.budget = budget;
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public int getIncome() {
        return income;
    }

    /**
     *
     */
    public void setIsBankrupt() {
        isBankrupt = true;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }
}
