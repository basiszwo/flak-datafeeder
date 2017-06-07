// Copyright (c) Stefan Botzenhart. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.
package one.flak.datafeeder;

import com.csvreader.CsvReader;
import one.flak.datafeeder.models.TripSample;
import one.flak.datafeeder.models.TripSampleBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TripSampleCSVReader {

    private CsvReader reader;

    public TripSampleCSVReader(String filePath) throws IOException {
        this.reader = new CsvReader(filePath, ',');

        reader.readHeaders();
    }

    public TripSample next() throws IOException {
        // builder
        TripSampleBuilder builder = new TripSampleBuilder();

        builder.setTimestamp(SafeCsvGetter.getLong(reader.get(0)));
        builder.setTripId(reader.get(1));
        builder.setTripUuid(reader.get(2));
        builder.setTripUid(reader.get(3));
        builder.setSpeed(SafeCsvGetter.getInt(reader.get(4)));
        builder.setVin(reader.get(5));
        builder.setAccelerationX(SafeCsvGetter.getDouble(reader.get(6)));
        builder.setAccelerationY(SafeCsvGetter.getDouble(reader.get(7)));
        builder.setAccelerationZ(SafeCsvGetter.getDouble(reader.get(8)));
        builder.setLatitude(SafeCsvGetter.getDouble(reader.get(9)));
        builder.setLongitude(SafeCsvGetter.getDouble(reader.get(10)));

        return builder.createTripSample();
    }


    public boolean hasNext() {
        try {
            return this.reader.readRecord();
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

}
