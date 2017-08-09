// Licensed under the GNU GENERAL PUBLIC LICENSE Version 3.
// See LICENSE file in the project root for full license information.

package one.flak.datafeeder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

public class ImportFileWorker {

    private Path filePath;

    /**
     * The transfomer to use to transform the read json file
     * to make it a valid api request object.
     */
    private JsonTransformer jsonTransformer;

    /**
     * The url to send api requests.
     */
    private URI apiUrl;

    private static int batchSize = 250;


    private TripSampleCSVReader csvReader;

    public ImportFileWorker(Path filePath, URI apiUrl, JsonTransformer transformer, TripSampleCSVReader csvReader) {
        this.filePath = filePath;
        this.jsonTransformer = transformer;
        this.apiUrl = apiUrl;
        this.csvReader = csvReader;
    }

    public void run() throws IOException {
        int count = 0;
        JsonArray tripSamples = new JsonArray();

        while(csvReader.hasNext()) {
            JsonObject tripSampleObject = this.jsonTransformer.transform(csvReader.next());

            tripSamples.add(tripSampleObject);

            count++;

            if(count == batchSize) {
                count = 0;
                makeApiRequest(tripSamples);
                tripSamples = new JsonArray();
                System.out.println("Sending API Request with " + batchSize + " samples ...");
            }
        }

    }

    public void makeApiRequest(JsonElement tripSample) {
        StringEntity jsonBody = new StringEntity(tripSample.toString(), ContentType.APPLICATION_JSON);

        HttpUriRequest request = RequestBuilder.post(apiUrl)
                .setEntity(jsonBody)
                .build();
        try {
            HttpClients.createDefault().execute(request);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
