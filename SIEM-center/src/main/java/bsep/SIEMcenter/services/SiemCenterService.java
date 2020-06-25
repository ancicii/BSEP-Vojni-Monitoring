package bsep.SIEMcenter.services;

import bsep.SIEMcenter.model.Log;
import bsep.SIEMcenter.model.LogStorage;
import bsep.SIEMcenter.model.LogTempModel;
import bsep.SIEMcenter.repository.LogRepository;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


@Service
public class SiemCenterService {

    private final KieContainer kieContainer;
    private KieSession kSession = null;
    private final LogRepository logRepository;

    @Autowired
    public SiemCenterService(KieContainer kieContainer, LogRepository logRepository) {
        this.kieContainer = kieContainer;
        this.logRepository = logRepository;
    }


    public boolean createNewAlarm(LogTempModel logTempModel) {
        InputStream template = SiemCenterService.class.getResourceAsStream("/bsep.SIEMcenter.rules/alarm_rule.drt");

        List<LogTempModel> data = new ArrayList<>();

        data.add(logTempModel);

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        drl = drl.substring(drl.indexOf("rule"));

        try {
            Files.write(Paths.get("src/main/resources/bsep.SIEMcenter.rules/alarm.drl"), drl.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean addNewAlarm(Log log){

        try {
            if (kSession == null) {
                kSession = kieContainer.newKieSession("ksession-rules");
                kSession.setGlobal("logStorage", new LogStorage());
            }
            kSession.insert(log);

            int br = kSession.fireAllRules();

            LogStorage logs = (LogStorage) kSession.getGlobal("logStorage");
            System.out.println(br);
            System.out.println(logs.getLogs());

            this.logRepository.save(log);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public LogStorage getAlarms(){

        LogStorage logStorage = new LogStorage();
        if(kSession != null) {
            logStorage = (LogStorage) kSession.getGlobal("logStorage");
        }
        
        for(Log l : this.logRepository.findAll()){
            System.out.println(l.getId());
        }
    
        return logStorage;
    }


}
