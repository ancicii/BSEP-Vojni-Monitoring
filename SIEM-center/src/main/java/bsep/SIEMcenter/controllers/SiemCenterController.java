package bsep.SIEMcenter.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/siem-center")
@CrossOrigin
public class SiemCenterController {

    @GetMapping(value = "/get")
    public String get(){
        System.out.println("Get from SIEM center!");
        return "SIEM center method!";
    }

}
