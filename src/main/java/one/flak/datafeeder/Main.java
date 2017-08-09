// Licensed under the GNU GENERAL PUBLIC LICENSE Version 3.
// See LICENSE file in the project root for full license information.

package one.flak.datafeeder;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {

        if(args.length != 1) {
            System.out.println("You have to provide a configuration file as argument");
            System.exit(1);
        }
        Properties props = new Properties();

        try(BufferedReader reader = Files.newBufferedReader(new File(args[0]).toPath())) {
            props.load(reader);
        } catch(IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        String apiUrl = props.getProperty("apiUrl");

        int startDate = Integer.parseInt(props.getProperty("startDate"));
        int endDate = Integer.parseInt(props.getProperty("endDate"));

        File inputFolder = new File(props.getProperty("inputFolder"));

        List<File> directories = Arrays.asList(inputFolder.listFiles())
                .stream()
                .filter((e) -> {
                    if(e.getName().equals(".DS_Store")) {
                        return false;
                    }

                    int fileDate = Integer.parseInt(FilenameUtils.getBaseName(e.getName()));

                    boolean isInRange = fileDate >= startDate && fileDate <= endDate;

                    return isInRange && e.isDirectory();
                })
                .collect(Collectors.toList());


        URI apiURL = new URL(apiUrl).toURI();


        directories.parallelStream().forEach((dir) -> {
            Arrays.asList(dir.listFiles()).parallelStream()
                    .filter((file) -> {
                        if(file.getName().equals(".DS_Store")) {
                            return false;
                        }

                        return !file.isDirectory();
                    })
                    .forEach((f) -> {
                        System.out.println("Processing file " + f.getAbsolutePath());

                        TripSampleCSVReader tripSampleCSVReader = null;

                        try {
                            tripSampleCSVReader = new TripSampleCSVReader(f.toString());
                            new ImportFileWorker(f.toPath(), apiURL, new JsonTransformer(), tripSampleCSVReader).run();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        });
    }
}
