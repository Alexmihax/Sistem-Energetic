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

        /* Read initial data */
        game.setup(inputData);
        /* Run initial round */
        game.runRound();

        /* Normal rounds */
        for (int i = 0; i < inputData.getNumberOfTurns(); i++) {
            /* Update consumers */
            game.updateConsumers(inputData.getUpdates().get(i).getNewConsumers());
            /* Update distributors */
            game.updateDistributors(inputData.getUpdates().get(i).getDistributorChanges());
            /* Run normal round */
            game.runRound();
            /* Update producers */
            game.updateProducers(inputData.getUpdates().get(i).getProducerChanges());
            /* Run end of the round tasks */
            game.runEndOfRound(i + 1);
        }

        /* Output */
        game.displayState(args[1]);
    }
}
