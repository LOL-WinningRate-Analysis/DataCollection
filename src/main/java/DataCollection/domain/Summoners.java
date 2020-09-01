package DataCollection.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Summoners {
    private String accountId;
    @Id
    private String name;
}
