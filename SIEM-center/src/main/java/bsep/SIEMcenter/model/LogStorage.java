package bsep.SIEMcenter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogStorage {
    private ArrayList<String> logs = new ArrayList<>();

    public void addLog(String host, String type, String message) {
        logs.add("An alarm was triggered by " + host + " as a " + type + " with a following message " + message);
    }
}
