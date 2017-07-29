// Licensed under the GNU GENERAL PUBLIC LICENSE Version 3.
// See LICENSE file in the project root for full license information.

package one.flak.datafeeder;

import com.google.gson.JsonObject;
import one.flak.datafeeder.models.TripSample;

public class JsonTransformer {

    public JsonObject transform(TripSample tripSample) {
        JsonObject obj = new JsonObject();

        obj.addProperty("created_at", tripSample.getTimestamp());

        obj.add("location", buildLocation(tripSample));
        obj.add("acceleration", buildAcceleration(tripSample));
        obj.addProperty("trip_id", tripSample.getTripId());

        return obj;
    }

    private JsonObject buildLocation(TripSample tripSample) {
        JsonObject location = new JsonObject();

        location.addProperty("latitude", tripSample.getLatitude());
        location.addProperty("longitude", tripSample.getLongitude());

        return location;
    }

    private JsonObject buildAcceleration(TripSample sample) {
        JsonObject acceleration = new JsonObject();

        acceleration.addProperty("x", sample.getAccelerationX());
        acceleration.addProperty("y", sample.getAccelerationY());
        acceleration.addProperty("z", sample.getAccelerationZ());

        return acceleration;
    }
}