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

    public void addLog(String message) {
        logs.add(message);
    }
}
