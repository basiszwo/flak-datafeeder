# Data feeder tool for FLAK stack

Data feeder tool to read trip samples CSV data, transform each line to JSON and POST it to the trip samples endpoint of the FLAK example api.

# Usage

## Build the JAR

```
mvn package
```

## Run

```
java -jar datafeeder-1.0-SNAPSHOT-jar-with-dependencies.jar configuration.properties
```
