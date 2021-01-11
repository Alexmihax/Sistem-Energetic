package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
