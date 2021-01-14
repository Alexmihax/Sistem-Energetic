package game;

import documents.Contract;
import entities.ConcreteConsumer;
import entities.ConcreteDistributor;
import entities.ConcreteProducer;
import entities.EntityFactory;
import entities.FactoryProvider;
import input.DistributorChanges;
import input.EntityInput;
import input.InputData;
import input.ProducerChanges;
import output.OutputWriter;
import repository.Repo;
import strategies.EnergyChoiceStrategy;
import strategies.EnergyChoiceStrategyFactory;
import utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

public final class GameSimulation extends Observable {
    private static final GameSimulation instance = new GameSimulation();
    private Repo repo;

    /**
     * Method for the Singleton design
     * @return instance of the GameSimulation
     */
    public static GameSimulation getInstance() {
        return instance;
    }

    private GameSimulation() {
    }

    /**
     *
     * @param inputData to put in repository
     */
    public void setup(final InputData inputData) {
        repo = new Repo(inputData);
    }



    /**
     * Method that executes the tasks for each round
     */
    public void runRound() {
        //

        for (ConcreteDistributor distributor : repo.getDistributors()) {
            if (!distributor.getIsBankrupt() && distributor.getProducerList().size() == 0) {
                for (ConcreteProducer producer : distributor.getProducerList()) {
                    producer.getDistributorList().remove(distributor);
                }

                EnergyChoiceStrategyFactory StrategyFactory = EnergyChoiceStrategyFactory.getInstance();
                EnergyChoiceStrategy strategy = StrategyFactory.create(distributor.getProducerStrategy());
                distributor.setProducerList(strategy.chooseProducers(repo.getEnergyProducers(),
                        distributor.getEnergyNeededKW()));
                distributor.setNeedsUpdate(false);
                distributor.computeProductionCost();
                for (ConcreteProducer producer : distributor.getProducerList()) {
                    producer.getDistributorList().add(distributor);
                    producer.addObserver(distributor);
                }
            }
        }

        // Get the best offer from distributors
        ConcreteDistributor cheapestDistributor = repo.getCheapestDistributor();
        int cheapestPrice = cheapestDistributor.getContractCost();

        for (ConcreteConsumer consumer : repo.getConsumers()) {
            // Ignore bankrupt consumers
            if (!consumer.getIsBankrupt()) {

                consumer.getMonthlyIncome();

                // Delete ended contracts
                if (consumer.getContract() != null
                        && consumer.getContract().getRemainedContractMonths() == 0) {
                    ConcreteDistributor distributor = consumer.getContract().getDistributor();
                    distributor.getContracts().remove(consumer.getContract());
                    consumer.setContract(null);
                }

                // Create new Contracts
                if (consumer.getContract() == null) {
                    Contract newContract = new Contract(consumer.getId(),
                            cheapestDistributor,
                            cheapestPrice, cheapestDistributor.getContractLength());
                    consumer.setContract(newContract);
                    cheapestDistributor.getContracts().add(newContract);
                }

                consumer.monthlyPay();
            }
        }

        // Distributor monthly pay
        for (ConcreteDistributor distributor : repo.getDistributors()) {
            if (distributor.getBudget() < 0) {
                distributor.setIsBankrupt();
            } else {
                distributor.monthlyPay();
            }
        }

        for (ConcreteConsumer consumer : repo.getConsumers()) {
            if (consumer.getContract() != null) {
                // Decrease months remaining in contracts
                consumer.getContract().monthPassed();

                // Delete the contract from the distributor
                if (consumer.getIsBankrupt() && consumer.getContract() != null) {
                    ConcreteDistributor distributor = consumer.getContract().getDistributor();
                    distributor.getContracts().remove(consumer.getContract());
                    consumer.setContract(null);
                }
            }
        }
    }

    public void runEndOfRound(int round) {
        // Distributors choose their producers
        for (ConcreteDistributor distributor : repo.getDistributors()) {
            if (!distributor.getIsBankrupt() && distributor.getNeedsUpdate()) {

                for (ConcreteProducer producer : distributor.getProducerList()) {
                    producer.getDistributorList().remove(distributor);
                }

                EnergyChoiceStrategyFactory StrategyFactory = EnergyChoiceStrategyFactory.getInstance();
                EnergyChoiceStrategy strategy = StrategyFactory.create(distributor.getProducerStrategy());
                distributor.setProducerList(strategy.chooseProducers(repo.getEnergyProducers(),
                        distributor.getEnergyNeededKW()));
                distributor.setNeedsUpdate(false);
                distributor.computeProductionCost();
                for (ConcreteProducer producer : distributor.getProducerList()) {
                    producer.getDistributorList().add(distributor);
                    producer.addObserver(distributor);
                }
            }
        }

        // Update producers for distributors
        for (ConcreteProducer producer : repo.getEnergyProducers()) {
            producer.addMonthlyStats(round);
        }
    }

    /**
     *
     * @param newConsumers
     */
    public void updateConsumers(List<EntityInput> newConsumers) {
        FactoryProvider factoryProvider = FactoryProvider.getInstance();
        EntityFactory<?> factory = factoryProvider.getFactory(Constants.CONSUMER);
        for (EntityInput consumer : newConsumers) {
            assert factory != null;
            repo.getConsumers().add((ConcreteConsumer) factory.create(consumer,
                    Constants.CONCRETE_CONSUMER));
        }
    }

    /**
     *
     * @param distributorChanges
     */
    public void updateDistributors(List<DistributorChanges> distributorChanges) {
        for (DistributorChanges change : distributorChanges) {
            ConcreteDistributor distributor = repo.getDistributors().get(change.getId());
            if (!distributor.getIsBankrupt()) {
                distributor.setInfrastructureCost(change.getInfrastructureCost());
            }
        }
    }

    /**
     *
     * @param producerChanges
     */
    public void updateProducers(List<ProducerChanges> producerChanges) {
        for (ProducerChanges change : producerChanges) {
            ConcreteProducer producer = repo.getEnergyProducers().get(change.getId());
            producer.setEnergyPerDistributor(change.getEnergyPerDistributor());
            producer.updated();
        }
    }

    /**
     *
     * @param outputPath to write the result to
     * @throws IOException in case of exception to writing
     */
    public void displayState(final String outputPath) throws IOException {
        OutputWriter outputWriter = new OutputWriter();
        File outputFile = new File(outputPath);
        outputWriter.writeOutput(repo, outputFile);
    }
}
