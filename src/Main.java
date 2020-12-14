import database.Database;
import documents.Contract;
import entities.Consumer;
import entities.Distributor;
import game.GameEngine;
import input.CostChange;
import input.Input;
import input.InputLoader;
import input.MonthlyInputData;
import output.OutputWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        GameEngine game = new GameEngine();
        InputLoader inputLoader = new InputLoader(args[0]);
        Input input = inputLoader.readInitialData();
        Database database = new Database(input);

        // Setup
        Distributor cheapestDistributor = database.getCheapestDistributor();
        int cheapestPrice = cheapestDistributor.getContractCost();

        for (Consumer consumer : database.getConsumers()) {
            // Income
            consumer.setBudget(consumer.getBudget() + consumer.getIncome());
            Contract firstContract = new Contract(consumer.getId(),
                    cheapestDistributor.getId(),
                    cheapestPrice, cheapestDistributor.getContractLength());
            consumer.setContract(firstContract);
            cheapestDistributor.getContracts().add(firstContract);
            //System.out.println("Pret:" + cheapestPrice);
            // Consumer pays
            if (consumer.getBudget() >= consumer.getContract().getPrice()) {
                // System.out.println("Normal pay" + consumer.getContract().getPrice());
                consumer.setBudget(consumer.getBudget() - consumer.getContract().getPrice());
                // Distributor gets payed
                database.getDistributors().get(consumer.getContract().getDistributorId())
                        .getPayment(consumer.getContract().getPrice());

            } else {
                // System.out.println("Set Penalty");
                consumer.setPenalty((int)Math.round(Math.floor(1.2 * consumer.getContract().getPrice())));
            }
        }

        for (Distributor distributor : database.getDistributors()) {
            if (distributor.getBudget() < 0) {
                distributor.setIsBankrupt();
            } else {
                distributor.pay();
            }
        }

        // Month passed
        for (Consumer consumer : database.getConsumers()) {
            consumer.getContract().monthPassed();
        }

        // Update
        for (MonthlyInputData update : input.getUpdates()) {
            // Update database
            database.getConsumers().addAll(update.getNewConsumers());
            for (CostChange change : update.getCostChanges()) {
                Distributor distributor = database.getDistributors().get(change.getId());
                if (!distributor.getIsBankrupt()) {
                    distributor.setInfrastructureCost(change.getInfrastructureCost());
                    distributor.setProductionCost(change.getProductionCost());
                }
            }
            // Calculate cheapest Distributor
            cheapestDistributor = database.getCheapestDistributor();
            cheapestPrice = cheapestDistributor.getContractCost();
            // Consumer monthly
            for (Consumer consumer : database.getConsumers()) {
                if (!consumer.getIsBankrupt()) {
                    consumer.setBudget(consumer.getBudget() + consumer.getIncome());
                    // Delete contracts
                    if (consumer.getContract() != null && consumer.getContract().getRemainedContractMonths() == 0) {
                        Distributor distributor = database.getDistributors().get(consumer.getContract().getDistributorId());
                        distributor.getContracts().remove(consumer.getContract());
                        consumer.setContract(null);
                    }
                    // Setup new Contract
                    if (consumer.getContract() == null) {
                        Contract firstContract = new Contract(consumer.getId(),
                                cheapestDistributor.getId(),
                                cheapestPrice, cheapestDistributor.getContractLength());
                        // System.out.println("Pret: " + cheapestPrice);
                        consumer.setContract(firstContract);
                        cheapestDistributor.getContracts().add(firstContract);
                    }
                    if (consumer.getPenalty() != 0) {
                        if (consumer.getBudget() < consumer.getContract().getPrice() + consumer.getPenalty()) {
                            // System.out.println("Bankrupt");
                            consumer.setIsBankrupt();
                        } else {
//                          System.out.println("pay penalty");
                            // Consumer pay
                            consumer.setBudget(consumer.getBudget()
                                    - consumer.getContract().getPrice() - consumer.getPenalty());
                            // Distributor gets payed
                            database.getDistributors().get(consumer.getContract().getDistributorId())
                                    .getPayment(consumer.getContract().getPrice() + consumer.getPenalty());
                            // Reset penalty
                            consumer.setPenalty(0);
                        }
                    } else if (consumer.getBudget() >= consumer.getContract().getPrice()) {
                        // System.out.println("Normal pay " + consumer.getContract().getPrice());
                        // Consumer pay
                        consumer.setBudget(consumer.getBudget() - consumer.getContract().getPrice());
                        // Distributor gets payed
                        database.getDistributors().get(consumer.getContract().getDistributorId())
                                .getPayment(consumer.getContract().getPrice());

                    } else {
                        // System.out.println("Set Penalty");
                        consumer.setPenalty((int)Math.round(Math.floor(1.2 * consumer.getContract().getPrice())));
                    }
                }
            }

            for (Distributor distributor : database.getDistributors()) {
                if (distributor.getBudget() < 0) {
                    distributor.setIsBankrupt();
                } else {
                    distributor.pay();
                }
            }

            for (Consumer consumer : database.getConsumers()) {
                if (consumer.getContract() != null) {
                    consumer.getContract().monthPassed();
                    if (consumer.getIsBankrupt()) {
                        Distributor distributor = database.getDistributors().get(consumer.getContract().getDistributorId());
                        distributor.getContracts().remove(consumer.getContract());
                        consumer.setContract(null);
                    }
                }
            }
        }

        // Output
        OutputWriter outputWriter = new OutputWriter();
        File outputFile = new File(args[1]);
        outputWriter.writeOutput(database, outputFile);
        // game.setup(input);
        // game.update(input);
        // game.displayState();
        // OutputWriter outputWriter = new OutputWriter();
    }
}
