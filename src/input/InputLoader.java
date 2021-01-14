package input;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InputLoader {
    /**
     * The path to the input file
     */
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * The method reads the database
     *
     * @return an Input Data object
     */
    public InputData readInitialData() {
        JSONParser jsonParser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();
        List<EntityInput> consumerInput = new ArrayList<>();
        List<EntityInput> distributorInput = new ArrayList<>();
        List<EntityInput> producerInput = new ArrayList<>();
        List<MonthlyInputData> updateList = new ArrayList<>();
        int numberOfTurns = 0;
        try {
            // Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(inputPath));

            // Reading the number of turns
            numberOfTurns = ((Long) jsonObject.get(Constants.NUMBER_OF_TURNS)).intValue();

            // Reading the update list
            JSONArray updatesDatabase = (JSONArray) jsonObject.get(Constants.MONTHLY_UPDATES);
            updateList = mapper.readValue(updatesDatabase.toString(),
                    new TypeReference<List<MonthlyInputData>>() { });

            // Reading initial consumer, distributor and producers lists
            JSONObject database = (JSONObject) jsonObject.get(Constants.INITIALDATA);
            JSONArray jsonConsumers = (JSONArray)
                    database.get(Constants.CONSUMERS);
            consumerInput = mapper.readValue(jsonConsumers.toString(),
                    new TypeReference<List<EntityInput>>() { });

            JSONArray jsonDistributors = (JSONArray)
                    database.get(Constants.DISTRIBUTORS);
            distributorInput = mapper.readValue(jsonDistributors.toString(),
                    new TypeReference<List<EntityInput>>() { });

            JSONArray jsonProducers = (JSONArray)
                    database.get(Constants.PRODUCERS);
            producerInput = mapper.readValue(jsonProducers.toString(),
                    new TypeReference<List<EntityInput>>() { });

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new InputData(numberOfTurns, consumerInput, distributorInput, producerInput, updateList);
    }
}
