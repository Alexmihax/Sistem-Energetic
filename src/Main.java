import game.GameSimulation;
import input.InputData;
import input.InputLoader;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
         InputLoader inputLoader = new InputLoader(args[0]);
//        InputLoader inputLoader = new InputLoader("checker/resources/in/complex_5.json");
        InputData inputData = inputLoader.readInitialData();
        GameSimulation game = GameSimulation.getInstance();

        // Setup
        game.setup(inputData);
        game.runRound();

        // Update
        for (int i = 0; i < inputData.getNumberOfTurns(); i++) {
            game.updateConsumers(inputData.getUpdates().get(i).getNewConsumers());
            game.updateDistributors(inputData.getUpdates().get(i).getDistributorChanges());
            game.runRound();
            game.updateProducers(inputData.getUpdates().get(i).getProducerChanges());
            game.runEndOfRound(i + 1);
        }

        // Output
         game.displayState(args[1]);
//        game.displayState("results.out");
    }
}
