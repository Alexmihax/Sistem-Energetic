package input;

import common.Constants;
import entities.Consumer;
import entities.Distributor;
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

    public String getInputPath() {
        return inputPath;
    }

    /**
     * The method reads the database
     *
     * @return an Input object
     */
    public Input readInitialData() {
        // TODO Implement with ObjectMapper
        JSONParser jsonParser = new JSONParser();
        List<Consumer> consumers = new ArrayList<>();
        List<Distributor> distributors = new ArrayList<>();
        List<MonthlyInputData> updates = new ArrayList<>();
        int numberOfTurns = 0;
        try {
            // Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser
                    .parse(new FileReader(inputPath));
            numberOfTurns = ((Long) jsonObject.get(Constants.NMBOFTURNS)).intValue();
            JSONObject database = (JSONObject) jsonObject.get(Constants.INITIALDATA);
            JSONArray updatesDatabase = (JSONArray) jsonObject.get(Constants.MONTHLYUPDATES);

            JSONArray jsonConsumers = (JSONArray)
                    database.get(Constants.CONSUMERS);
            JSONArray jsonDistributors = (JSONArray)
                    database.get(Constants.DISTRIBUTORS);
            consumers = readConsumers(jsonConsumers);
            distributors = readDistributors(jsonDistributors);
            updates = readUpdates(updatesDatabase);
            if (jsonConsumers == null) {
                consumers = null;
            }

            if (jsonDistributors == null) {
                distributors = null;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new Input(numberOfTurns, consumers, distributors, updates);
    }

    public List<MonthlyInputData> readUpdates(JSONArray jsonUpdates) {
            List<MonthlyInputData> updates = new ArrayList<>();
            if (jsonUpdates != null) {
                for (Object jsonIterator : jsonUpdates) {
                    JSONArray jsonConsumers = (JSONArray)
                            ((JSONObject) jsonIterator).get(Constants.NEWCONSUMERS);
                    JSONArray jsonCostsChanges= (JSONArray)
                            ((JSONObject) jsonIterator).get(Constants.COSTSCHANGES);
                    updates.add(new MonthlyInputData(readConsumers(jsonConsumers),
                                                    readCostChange(jsonCostsChanges)
                            ));
                }
            } else {
                System.out.println("NU EXISTA UPDATE-URI");
            }
        return updates;
    }

    public List<Consumer> readConsumers(JSONArray jsonConsumers) {
        List<Consumer> consumers = new ArrayList<>();
        if (jsonConsumers != null) {
            for (Object jsonIterator : jsonConsumers) {
                consumers.add(new Consumer(
                        ((Long) ((JSONObject) jsonIterator).get(Constants.ID))
                                .intValue(),
                        ((Long) ((JSONObject) jsonIterator).get(Constants.INITIALBUDGET))
                                .intValue(),
                        ((Long) ((JSONObject) jsonIterator).get(Constants.MONTHLYINCOME))
                                .intValue())
                );
            }
        } else {
            System.out.println("NU EXISTA CONSUMATORI");
        }
        return consumers;
    }

    public List<Distributor> readDistributors(JSONArray jsonDistributors) {
        List<Distributor> distributors = new ArrayList<>();
        if (jsonDistributors != null) {
            for (Object jsonIterator : jsonDistributors) {
                distributors.add(new Distributor(
                        ((Long) ((JSONObject) jsonIterator).get(Constants.ID))
                                .intValue(),
                        ((Long) ((JSONObject) jsonIterator).get(Constants.INITIALBUDGET))
                                .intValue(),
                        ((Long) ((JSONObject) jsonIterator).get(Constants.CONTRACTLENGTH))
                                .intValue(),
                        ((Long) ((JSONObject) jsonIterator).get(Constants.INITIALINFRACOST))
                                .intValue(),
                        ((Long) ((JSONObject) jsonIterator).get(Constants.INITIALPRODCOST))
                                .intValue()
                ));
            }
        } else {
            System.out.println("NU EXISTA DISTRIBUTORI");
        }
        return distributors;
    }

    public List<CostChange> readCostChange(JSONArray jsonCostChanges) {
        List<CostChange> costChanges = new ArrayList<>();
        if (jsonCostChanges != null) {
            for (Object jsonIterator : jsonCostChanges) {
                costChanges.add(new CostChange(
                        ((Long) ((JSONObject) jsonIterator).get(Constants.ID))
                                .intValue(),
                        ((Long) ((JSONObject) jsonIterator).get(Constants.INFRASCTRUCTURECOST))
                                .intValue(),
                        ((Long) ((JSONObject) jsonIterator).get(Constants.PRODUCTIONCOST))
                                .intValue()
                ));
            }
        } else {
            System.out.println("NU EXISTA DISTRIBUTORI");
        }
        return costChanges;
    }
}
