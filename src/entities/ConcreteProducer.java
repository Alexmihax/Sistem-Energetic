package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class ConcreteProducer extends Producer {

    private final class Stats {
        private final int month;
        private final ArrayList<Integer> distributorsIds;

        Stats(int month, ArrayList<Integer> distributorsIds) {
            this.month = month;
            this.distributorsIds = distributorsIds;
        }

        public int getMonth() {
            return month;
        }

        public ArrayList<Integer> getDistributorsIds() {
            return distributorsIds;
        }
    }

    private final ArrayList<Stats> monthlyStats = new ArrayList<>();
    @JsonIgnore
    private final ArrayList<Distributor> distributorList = new ArrayList<>();

    ConcreteProducer(int id, String energyType, int maxDistributors,
                            float priceKW, int energyPerDistributor) {
        super(id, energyType, maxDistributors, priceKW, energyPerDistributor);
    }

    /**
     * Method for the Observer pattern
     */
    public void updated() {
        setChanged();
        notifyObservers();
    }

    /**
     * Method that registers the monthly stats
     * @param month of the stats
     */
    public void addMonthlyStats(int month) {
        ArrayList<Integer> distributorsIds = (ArrayList<Integer>) distributorList.stream()
                .map(Distributor::getId).sorted().collect(Collectors.toList());
        monthlyStats.add(new Stats(month, distributorsIds));
    }

    public ArrayList<Distributor> getDistributorList() {
        return distributorList;
    }

    public ArrayList<Stats> getMonthlyStats() {
        return monthlyStats;
    }
}

