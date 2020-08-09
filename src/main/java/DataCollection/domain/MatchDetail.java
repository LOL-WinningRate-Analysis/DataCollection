package DataCollection.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MatchDetail {
    @Id
    private long gameId;
    private int queueId;
    private ParticipantDto participants;
    private TeamStatsDto teams;

    @Data
    public class ParticipantDto{
        private int championId;
        private int teamId;
        private int spell1Id;
        private int spell2Id;
    }

    @Data
    public class TeamStatsDto{
        private int teamId;
        private String win;
    }
}
