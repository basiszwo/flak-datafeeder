// Copyright (c) Stefan Botzenhart. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.
package one.flak.datafeeder;

import com.csvreader.CsvReader;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
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
//        checkArguments(args);
//        File folder = new File(args[0]);

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

//    private static void checkArguments(String[] args) {
//        if (args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))) {
//            showHelp();
//            System.exit(0);
//        } else if (args.length != 2) {
//            System.out.println("You have to supply exactly two arguments.");
//            System.out.println("Check the help with -h.");
//            System.exit(1);
//        }
//    }
//
//    private static void showHelp() {
//        System.out.println("This command line tool sends the given json trip samples");
//        System.out.println("to a given api endpoint.");
//        System.out.println("");
//        System.out.println("Usage:");
//        System.out.println("java -jar commandline INPUT_CSV_FOLDER YOUR_API_ENDPOINT");
//    }
}
