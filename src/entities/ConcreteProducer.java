package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ConcreteProducer extends Producer {

    public class Stats {
        private final int month;
        private final List<Integer> distributorsIds;

        public Stats(int month, List<Integer> distributorsIds) {
            this.month = month;
            this.distributorsIds = distributorsIds;
        }

        public int getMonth() {
            return month;
        }

        public List<Integer> getDistributorsIds() {
            return distributorsIds;
        }
    }

    private final ArrayList<Stats> monthlyStats = new ArrayList<>();

    @JsonIgnore
    private final ArrayList<Distributor> distributorList = new ArrayList<>();

    public ConcreteProducer(int id, String energyType, int maxDistributors, float priceKW, int energyPerDistributor) {
        super(id, energyType, maxDistributors, priceKW, energyPerDistributor);
    }


    public void updated() {
        setChanged();
        notifyObservers();
    }

    public ArrayList<Stats> getMonthlyStats() {
        return monthlyStats;
    }

    public ArrayList<Distributor> getDistributorList() {
        return distributorList;
    }

    public void addMonthlyStats(int month) {
        List<Integer> distributorsIds = distributorList.stream()
                .map(Distributor::getId).collect(Collectors.toList());
        monthlyStats.add(new Stats(month, distributorsIds));
    }
}

