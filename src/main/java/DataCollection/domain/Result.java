package DataCollection.domain;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    private List<MatchDetail> matchDetails;
}
