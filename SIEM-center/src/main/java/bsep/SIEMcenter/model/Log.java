package bsep.SIEMcenter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Expires("10m")
@Document
public class Log {

    @Id
    public String id;
    public String timestamp;
    public String message;
    public String os;
    public String type;
    public String host;
    public String machine;
    public boolean pass = true;

}
