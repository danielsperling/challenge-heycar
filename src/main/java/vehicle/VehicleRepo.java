package vehicle;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Integer.parseInt;

public class VehicleRepo {
    public Vehicle readVehicle(String id) {
        JsonArray vehicles = this.readVehicles();
        Optional<JsonObject> myVehicle = vehicles.stream().map(JsonObject.class::cast).filter(x ->
            x.get("id").equals(id)
        ).findFirst();

        if (myVehicle.isPresent()) {
            JsonObject result = myVehicle.get();
            return new Vehicle(result.get("id").toString(),
                    result.get("code").toString(),
                    result.get("make").toString(),
                    result.get("model").toString(),
                    parseInt(result.get("kW").toString()),
                    parseInt(result.get("year").toString()),
                    result.get("color").toString(),
                    parseInt(result.get("price").toString()));

        } else {
            return null;
        }

    }

    public JsonArray readVehicles()  {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("vehicles.json"));
            JsonArray vehicles = (JsonArray) Jsoner.deserialize(reader);

            reader.close();
            return vehicles;
        } catch (JsonException e) {
            e.printStackTrace();
            return new JsonArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonArray();
        }
    }

    public Vehicle writeVehicle(VehicleForm vehicleBody) {
        try {
            // Read all vehicles from JSON
            JsonArray vehicles = this.readVehicles();
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("vehicles.json"));

            JsonObject myVehicle = new JsonObject();
            String vehicleId = UUID.randomUUID().toString();

            myVehicle.put("id", vehicleId);
            myVehicle.put("code", vehicleBody.getCode());
            myVehicle.put("make", vehicleBody.getMake());
            myVehicle.put("model", vehicleBody.getModel() );
            myVehicle.put("kW", vehicleBody.getkW() );
            myVehicle.put("year", vehicleBody.getYear() );
            myVehicle.put("color", vehicleBody.getColor() );
            myVehicle.put("price", vehicleBody.getPrice() );

            vehicles.add(myVehicle);

            Jsoner.serialize(vehicles, writer);
            writer.close();
            return new Vehicle(vehicleId, vehicleBody.getCode(), vehicleBody.getMake(), vehicleBody.getModel(), vehicleBody.getkW(), vehicleBody.getYear(), vehicleBody.getColor(), vehicleBody.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}