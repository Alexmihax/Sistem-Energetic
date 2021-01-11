package output;

import com.fasterxml.jackson.databind.ObjectMapper;
import repository.Repo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class OutputWriter {

    /**
     *
     * @param repo to write from
     * @param outputFile to write to
     * @throws IOException in case of exception to writing
     */
    public void writeOutput(final Repo repo, final File outputFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(repo);
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(json);
        fileWriter.close();
    }
}
