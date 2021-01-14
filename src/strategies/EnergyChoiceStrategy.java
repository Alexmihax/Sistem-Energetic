package strategies;

import entities.ConcreteProducer;

import java.util.ArrayList;
import java.util.List;

public interface EnergyChoiceStrategy {
    /**
     *
     */
    ArrayList<ConcreteProducer> chooseProducers(final List<ConcreteProducer> producerList, final int energyQuantity);
}
