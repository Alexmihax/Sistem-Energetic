package game;

import documents.Contract;
import entities.ConcreteConsumer;
import entities.Consumer;
import entities.Distributor;
import entities.EntityFactory;
import entities.FactoryProvider;
import entities.Producer;
import input.DistributorChanges;
import input.EntityInput;
import input.InputData;
import input.ProducerChanges;
import output.OutputWriter;
import repository.Repo;
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

    /**
     * for coding style
     */
    private GameSimulation() {
    }

    /**
     * Method that initialises the database
     * @param inputData to put in repository
     */
    public void setup(final InputData inputData) {
        repo = new Repo(inputData);
    }

    /**
     * Method that executes the tasks for each round
     */
    public void runRound() {
        /* New Distributors choose their producers */
        for (Distributor distributor : repo.getDistributors()) {
            if (!distributor.getIsBankrupt() && distributor.getProducerList().size() == 0) {
                distributor.applyStrategy(repo.getEnergyProducers());
            }
        }

        /* Get the best offer from distributors */
        Distributor cheapestDistributor = repo.getCheapestDistributor();
        int cheapestPrice = cheapestDistributor.getContractCost();

        /* Consumers choose distributors and pay them */
        for (Consumer consumer : repo.getConsumers()) {
            /* Ignore bankrupt consumers */
            if (!consumer.getIsBankrupt()) {
                /* Consumers get salary */
                consumer.getMonthlyIncome();

                /* Delete ended contracts */
                if (consumer.getContract() != null
                        && consumer.getContract().getRemainedContractMonths() == 0) {
                    Distributor distributor = consumer.getContract().getDistributor();
                    distributor.getContracts().remove(consumer.getContract());
                    consumer.setContract(null);
                }

                /* Create new Contracts */
                if (consumer.getContract() == null) {
                    Contract newContract = new Contract(consumer.getId(),
                            cheapestDistributor,
                            cheapestPrice, cheapestDistributor.getContractLength());
                    consumer.setContract(newContract);
                    cheapestDistributor.getContracts().add(newContract);
                }

                /* Pay the distributor */
                consumer.monthlyPay();
            }
        }

        /* Distributor monthly pay */
        for (Distributor distributor : repo.getDistributors()) {
            /* Check bankruptcy */
            if (distributor.getBudget() < 0) {
                distributor.setIsBankrupt();
                for (Producer producer : distributor.getProducerList()) {
                    producer.getDistributorList().remove(distributor);
                    producer.deleteObserver(distributor);
                }
            } else {
                /* Distributors pays the producers*/
                distributor.monthlyPay();
            }
        }

        /* Update the contracts */
        for (Consumer consumer : repo.getConsumers()) {
            if (consumer.getContract() != null) {
                /* Decrease months remaining in contracts */
                consumer.getContract().monthPassed();

                /* Delete the contract from the distributor */
                if (consumer.getIsBankrupt() && consumer.getContract() != null) {
                    Distributor distributor = consumer.getContract().getDistributor();
                    distributor.getContracts().remove(consumer.getContract());
                    consumer.setContract(null);
                }
            }
        }
    }

    /**
     * Method that executes the tasks at the end of the round
     * @param round the current month
     */
    public void runEndOfRound(int round) {
        /* Distributors choose their producers if the old producers updated */
        for (Distributor distributor : repo.getDistributors()) {
            if (!distributor.getIsBankrupt() && distributor.getNeedsUpdate()) {
                for (Producer producer : distributor.getProducerList()) {
                    producer.getDistributorList().remove(distributor);
                    producer.deleteObserver(distributor);
                }

                distributor.applyStrategy(repo.getEnergyProducers());
            }
        }

        /* Update producers for distributors */
        for (Producer producer : repo.getEnergyProducers()) {
            producer.addMonthlyStats(round);
        }
    }

    /**
     * Method that updates consumer lists in the database
     * @param newConsumers to be added
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
     * Method that updates distributors in the database
     * @param distributorChanges the updates
     */
    public void updateDistributors(List<DistributorChanges> distributorChanges) {
        for (DistributorChanges change : distributorChanges) {
            Distributor distributor = repo.getDistributors().get(change.getId());
            if (!distributor.getIsBankrupt()) {
                distributor.setInfrastructureCost(change.getInfrastructureCost());
            }
        }
    }

    /**
     * Method that updates the producers in the database
     * @param producerChanges the updates
     */
    public void updateProducers(List<ProducerChanges> producerChanges) {
        for (ProducerChanges change : producerChanges) {
            Producer producer = repo.getEnergyProducers().get(change.getId());
            producer.setEnergyPerDistributor(change.getEnergyPerDistributor());
            producer.updated();
        }
    }

    /**
     * Method that displays the state of the game simulation
     * @param outputPath to write the result to
     * @throws IOException in case of exception to writing
     */
    public void displayState(final String outputPath) throws IOException {
        OutputWriter outputWriter = new OutputWriter();
        File outputFile = new File(outputPath);
        outputWriter.writeOutput(repo, outputFile);
    }
}
