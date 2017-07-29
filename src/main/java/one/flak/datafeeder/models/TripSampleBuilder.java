// Licensed under the GNU GENERAL PUBLIC LICENSE Version 3.
// See LICENSE file in the project root for full license information.

package one.flak.datafeeder.models;

public class TripSampleBuilder {
    private long timestamp;
    private String vin;
    private String tripId;
    private String tripUuid;
    private String tripUid;
    private int speed;
    private double latitude;
    private double longitude;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;

    public TripSampleBuilder setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TripSampleBuilder setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public TripSampleBuilder setTripId(String tripId) {
        this.tripId = tripId;
        return this;
    }

    public TripSampleBuilder setTripUuid(String tripUuid) {
        this.tripUuid = tripUuid;
        return this;
    }

    public TripSampleBuilder setTripUid(String tripUid) {
        this.tripUid = tripUid;
        return this;
    }

    public TripSampleBuilder setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public TripSampleBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public TripSampleBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public TripSampleBuilder setAccelerationX(double accelerationX) {
        this.accelerationX = accelerationX;
        return this;
    }

    public TripSampleBuilder setAccelerationY(double accelerationY) {
        this.accelerationY = accelerationY;
        return this;
    }

    public TripSampleBuilder setAccelerationZ(double accelerationZ) {
        this.accelerationZ = accelerationZ;
        return this;
    }

    public TripSample createTripSample() {
        return new TripSample(timestamp, vin, tripId, tripUuid, tripUid, speed, latitude, longitude, accelerationX, accelerationY, accelerationZ);
    }
}