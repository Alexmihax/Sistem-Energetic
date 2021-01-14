package strategies;

import entities.ConcreteProducer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;

public class GreenStrategy implements EnergyChoiceStrategy{
    @Override
    public ArrayList<ConcreteProducer> chooseProducers(final List<ConcreteProducer> producerList, final int energyQuantityNeeded) {
        ArrayList<ConcreteProducer> answer = new ArrayList<>();
        int energyQuantity = 0;
        List<ConcreteProducer> sortedList = producerList.stream()
                .sorted(Comparator
                        .<ConcreteProducer,Boolean>comparing(p->p.getEnergyType().isRenewable(), Comparator.reverseOrder())
                        .thenComparingDouble(ConcreteProducer::getPriceKW)
                        .thenComparing(reverseOrder(comparingInt(ConcreteProducer::getEnergyPerDistributor))))
                .collect(Collectors.toList());
        for (ConcreteProducer producer : sortedList) {
            if (producer.getMaxDistributors() > producer.getDistributorList().size()) {
                answer.add(producer);
                energyQuantity += producer.getEnergyPerDistributor();
                if (energyQuantity > energyQuantityNeeded) {
                    break;
                }
            }
        }
        return answer;
    }
}
