package DataCollection.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchIds {
    @Id
    private int id;
    private String summonerName;
    private List<Long> matchIds;
}
