package strategies;

import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class QuantityStrategy implements EnergyChoiceStrategy {
    @Override
    public ArrayList<Producer> chooseProducers(List<Producer> producerList,
                                               final int energyQuantityNeeded) {
        List<Producer> sortedList = producerList.stream()
                .sorted(Comparator
                .comparingInt(Producer::getEnergyPerDistributor)
                .reversed())
                .collect(Collectors.toList());
        return chooseSortedProducers(energyQuantityNeeded, sortedList);
    }
}
