package DataCollection.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class MatchDetail {
    @Id
    private long gameId;
    private int queueId; //queueId=420이면 솔랭
    private List<ParticipantDto> participants; //1~5가 팀 아이디 100 = 블루팀=아래
    private List<TeamStatsDto> teams;

    @Data
    public static class ParticipantDto{
        private int championId;
        private int teamId; //200이 레드팀.
        private int spell1Id;
        private int spell2Id;
        private ParticipantTimelineDto timeline;
    }

    @Data
    public static class TeamStatsDto{
        private int teamId;
        private String win;
    }

    @Data
    public static class ParticipantTimelineDto{
        private String lane;
    }
}
