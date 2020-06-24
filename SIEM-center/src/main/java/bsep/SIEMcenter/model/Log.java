package bsep.SIEMcenter.model;

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

}
