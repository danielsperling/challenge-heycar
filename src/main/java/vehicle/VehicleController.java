package vehicle;

import com.github.cliftonlabs.json_simple.JsonArray;
import org.springframework.web.bind.annotation.*;


@RestController
public class VehicleController {

    @GetMapping("/vehicles")
    public JsonArray getVehicles() {
        return new VehicleRepo().readVehicles();
    }

    @GetMapping("/vehicles/{id}")
    public Vehicle getVehicleById(@PathVariable(value = "id") String id) {
       return new VehicleRepo().readVehicle(id);
    }

    @PostMapping("/vehicles")
    public Vehicle createVehicle(@RequestBody VehicleForm body){
        return new VehicleRepo().writeVehicle(body);
    }
}
