package strategies;

import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;

public final class GreenStrategy implements EnergyChoiceStrategy {
    @Override
    public ArrayList<Producer> chooseProducers(final List<Producer> producerList,
                                               final int energyQuantityNeeded) {
        List<Producer> sortedList = producerList.stream()
                .sorted(Comparator
                        .<Producer, Boolean>comparing(p -> p.getEnergyType().isRenewable(),
                                Comparator.reverseOrder())
                        .thenComparingDouble(Producer::getPriceKW)
                        .thenComparing(reverseOrder(comparingInt(
                                Producer::getEnergyPerDistributor))))
                .collect(Collectors.toList());
        return chooseSortedProducers(energyQuantityNeeded, sortedList);
    }
}
