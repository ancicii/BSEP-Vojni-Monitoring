package bsep.SIEMcenter.controllers;

import bsep.SIEMcenter.exceptions.BadRequestException;
import bsep.SIEMcenter.model.Log;
import bsep.SIEMcenter.model.LogStorage;
import bsep.SIEMcenter.model.LogTempModel;
import bsep.SIEMcenter.services.SiemCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/siem-center")
@CrossOrigin
public class SiemCenterController {

    private final SiemCenterService newSiemCenterService;

    @Autowired
    public SiemCenterController(SiemCenterService newSiemCenterService) {
        this.newSiemCenterService = newSiemCenterService;
    }


    @GetMapping(value = "/get")
    public String get(){
        System.out.println("Get from SIEM center!");
        return "SIEM center method!";
    }

    @PostMapping(value = "/template")
    public ResponseEntity<String> createNewRule(@RequestBody LogTempModel logTempModel){

        boolean rez = newSiemCenterService.createNewAlarm(logTempModel);

        if (rez) {
            return new ResponseEntity<>("New rule added successfully", HttpStatus.OK);
        } else {
            throw new BadRequestException("Error while creating rule");
        }

    }

    @PostMapping()
    public ResponseEntity addAlarm(@RequestBody Log log){

        boolean ok = newSiemCenterService.addNewAlarm(log);
        if(ok){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/alarms")
    public ResponseEntity<List<String>> alarmList(){

        LogStorage logStorage = newSiemCenterService.getAlarms();
        return new ResponseEntity<>(logStorage.getLogs(),HttpStatus.OK);

    }

}
