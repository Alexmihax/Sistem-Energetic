package game;

import utils.Constants;
import documents.Contract;
import entities.EntityFactory;
import entities.ConcreteConsumer;
import entities.ConcreteDistributor;
import entities.FactoryProvider;
import input.CostChange;
import input.EntityInput;
import input.InputData;
import input.MonthlyInputData;
import output.OutputWriter;
import repository.Repo;

import java.io.File;
import java.io.IOException;

public final class GameSimulation {
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

    /**
     *
     * @param update data to put in the repository
     */
    public void update(final MonthlyInputData update) {
        FactoryProvider factoryProvider = FactoryProvider.getInstance();
        EntityFactory<?> factory = factoryProvider.getFactory(Constants.CONSUMER);
        for (EntityInput entityInput : update.getNewConsumers()) {
            assert factory != null;
            repo.getConsumers().add((ConcreteConsumer) factory.create(entityInput,
                    Constants.CONCRETECONSUMER));
        }

        for (CostChange change : update.getCostChanges()) {
            ConcreteDistributor distributor = repo.getDistributors().get(change.getId());
            if (!distributor.getIsBankrupt()) {
                distributor.setInfrastructureCost(change.getInfrastructureCost());
                distributor.setProductionCost(change.getProductionCost());
            }
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
