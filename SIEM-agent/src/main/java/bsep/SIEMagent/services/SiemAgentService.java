package bsep.SIEMagent.services;

import bsep.SIEMagent.model.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;
import com.sun.jna.platform.win32.Advapi32Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SiemAgentService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Environment env;

    @Value("${endpoint.siem-log}")
    private String endpoint;

    @Value("${filelogger.filepath}")
    private String filePath;

    private String lastFileLog = "";
    private HashMap<String, Integer> lastWindowsNumber = initHashMap();

    @EventListener(ApplicationReadyEvent.class)
    public void startLogScanning() {
        new Thread(() -> {
            try {
                while (true) {
                    System.out.println("\nTHREAD RUN\n");
                    runFileLogScan();
                    runWindowsLogScan();
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void runFileLogScan() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        Scanner sc = new Scanner(fis);
        String line = "";
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.equals(lastFileLog))
                break;
        }
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            Log newLog = Log.parse(line);
//            try {
//                sendLog(newLog);
//            } catch (JsonProcessingException | URISyntaxException e) {
//                e.printStackTrace();
//            }
            System.out.println(line);
        }
        lastFileLog = line;
        sc.close();
    }

    private void runWindowsLogScan() {
        groupLogScan("Application");
        groupLogScan("Security");
        groupLogScan("System");
        lastWindowsNumber.forEach((key, value) -> System.out.println(key + " " + value));
    }

    private void groupLogScan(String group) {
        Advapi32Util.EventLogIterator iter = new Advapi32Util.EventLogIterator(group);
        Advapi32Util.EventLogRecord record = null;
        while(iter.hasNext()) {
            record = iter.next();
            if (record.getRecordNumber() == lastWindowsNumber.getOrDefault(group, -1))
                break;
        }
        while(iter.hasNext()) {
            record = iter.next();

            LocalDateTime date =
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(record.getRecord().TimeGenerated.longValue()), ZoneId.systemDefault());
            String timestamp = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ") + ".000000";
            String logId = group + record.getRecordNumber();
            String logOs = System.getProperty("os.name").replace(" ", "");
            String logType = record.getType().name();
            String logHost = record.getSource();
            String logMessage = System.getProperty("os.name") + " " + logType + " log for event ID " + record.getEventId();
            String logMachine = "localhost";
//            String fullLog = timestamp + "|" + logId + "|" + logMessage + "|" + logOs + "|" + logType + "|" + logHost;
//            System.out.println(fullLog);
            Log newLog = new Log(timestamp, logId, logMessage, logOs, logType, logHost, logMachine);
            try {
                sendLog(newLog);
            } catch (JsonProcessingException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        assert record != null;
        lastWindowsNumber.put(group, record.getRecordNumber());
    }

    private void sendLog(Log log) throws JsonProcessingException, URISyntaxException {
        assert endpoint != null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(log);
        System.out.println(jsonString);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity(new URI(endpoint), request, String.class);
        System.out.println(responseEntityStr.getStatusCode());
    }

    private HashMap<String, Integer> initHashMap() {
        HashMap<String, Integer> ret = new HashMap<>();
        ret.put("Application", -1);
        ret.put("Security", -1);
        ret.put("System", -1);
        return ret;
    }
}
