package output;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class OutputWriter {

    public void writeOutput(Database Database,
                            File outputFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Database);
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(json);
        // System.out.println(json);
        fileWriter.close();
    }
}
