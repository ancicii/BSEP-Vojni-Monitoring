package bsep.SIEMagent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@CrossOrigin
public class SiemAgentController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Environment env;

    @GetMapping(value = "/data")
    public String getData() {
        System.out.println("Returning data from siem agent");
        return "Hello from siem agent get data method";
    }

    @GetMapping(value = "/ms-data")
    public String getAgentData() {
        System.out.println("Got inside siem agent method");
        try {
            String endpoint = env.getProperty("endpoint.siem-service");

            return restTemplate.getForObject(new URI(endpoint), String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Exception occurred.. so, returning default data";
    }
}
