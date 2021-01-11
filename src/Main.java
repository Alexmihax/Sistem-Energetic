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
        InputData inputData = inputLoader.readInitialData();
        GameSimulation game = GameSimulation.getInstance();

        // Setup
        game.setup(inputData);
        game.runRound();

        // Update
        for (int i = 0; i < inputData.getNumberOfTurns(); i++) {
            game.update(inputData.getUpdates().get(i));
            game.runRound();
        }

        // Output
        game.displayState(args[1]);
    }
}
