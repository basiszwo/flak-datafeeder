// Licensed under the GNU GENERAL PUBLIC LICENSE Version 3.
// See LICENSE file in the project root for full license information.

package one.flak.datafeeder;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        String apiUrl = "http://api-1.flak.one/samples";

        File inputFolder = new File("/Volumes/WD Ultra 2TB/trip-samples-csv");

        int startDate = Integer.parseInt("20170201");
        int endDate = Integer.parseInt("20170228");

        List<File> csvFiles = Arrays.asList(inputFolder.listFiles())
                .stream()
                .filter((e) -> {
                    System.out.println("Processing file " + e.getAbsolutePath());

                    if (e.getName().equals(".DS_Store")) {
                        return false;
                    }

                    System.out.println(FilenameUtils.getBaseName(e.getName()));

                    int fileDate = Integer.parseInt(FilenameUtils.getBaseName(e.getName()));

                    boolean isInRange = fileDate >= startDate && fileDate <= endDate;
                    return !e.isDirectory() && isInRange;
                })
                .collect(Collectors.toList());

        URI apiURL = new URL(apiUrl).toURI();

        csvFiles.parallelStream().forEach((f) -> {
            TripSampleCSVReader tripSampleCSVReader = null;

            try {
                tripSampleCSVReader = new TripSampleCSVReader(f.toString());
                new ImportFileWorker(f.toPath(), apiURL, new JsonTransformer(), tripSampleCSVReader).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
