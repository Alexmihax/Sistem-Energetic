package strategies;

import entities.Producer;

import java.util.ArrayList;
import java.util.List;

public interface EnergyChoiceStrategy {
    /**
     * Method that sorts the producers and chooses them
     */
    ArrayList<Producer> chooseProducers(List<Producer> producerList,
                                        int energyQuantity);

    /**
     * Method that chooses the producers depending on the energy needed, after being sorted
     * @param energyQuantityNeeded for the distributor
     * @param sortedList of the producers
     * @return the chosen producers
     */
    default ArrayList<Producer> chooseSortedProducers(int energyQuantityNeeded,
                                                              List<Producer> sortedList) {
        int energyQuantity = 0;
        ArrayList<Producer> answer = new ArrayList<>();
        for (Producer producer : sortedList) {
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
