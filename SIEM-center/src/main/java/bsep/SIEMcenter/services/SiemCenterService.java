package bsep.SIEMcenter.services;

import bsep.SIEMcenter.model.Log;
import bsep.SIEMcenter.model.LogStorage;
import bsep.SIEMcenter.model.LogTempModel;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public SiemCenterService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }


    public boolean createNewAlarm(LogTempModel logTempModel) {
        InputStream template = SiemCenterService.class.getResourceAsStream("/rules/alarm_rule.drt");

        List<LogTempModel> data = new ArrayList<>();

        data.add(logTempModel);

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        drl = drl.substring(drl.indexOf("rule"));

        try {
            Files.write(Paths.get("src/main/resources/rules/alarm.drl"), drl.getBytes(), StandardOpenOption.APPEND);
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
            kSession.fireAllRules();

            return true;
        }catch (Exception e){
            return false;
        }
    }


}
