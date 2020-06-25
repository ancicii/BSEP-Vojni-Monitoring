package bsep.SIEMagent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Log {

    public String timestamp;
    public String id;
    public String message;
    public String os;
    public String type;
    public String host;
    public String machine;

    public static Log parse(String logString) {
        String[] fields = logString.split("\\|");
        if (fields.length != 7) {
            return null;
        }
        return new Log(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
    }

}
