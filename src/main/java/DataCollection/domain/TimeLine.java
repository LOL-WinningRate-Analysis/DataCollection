package DataCollection.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TimeLine {
    private List<MatchFrameDto> frames;
    private long frameInterval;
    @Data
    public static class MatchFrameDto{
        private Map<String, MatchParticipantFrameDto> participantFrames;
        private List<MatchEventDto> events;
        private long timestamp;
    }
    @Data
    public static class MatchParticipantFrameDto{
        private int participantId;
        private int minionsKilled;
        private int teamScore;
        private int dominionScore;
        private int totalGold;
        private int level;
        private int xp;
        private int currentGold;
        private MatchPositionDto position;
        private int jungleMinionsKilled;
    }
    @Data
    public static class MatchEventDto{
        private String laneType;
        private int skillSlot;
        private String ascendedType;
        private int creatorId;
        private int afterId;
        private String eventType;
        private String type;
        private String levelUpType;
        private String wardType;
        private int participantId;
        private String towerType;
        private int itemId;
        private int beforeId;
        private String pointCaptured;
        private String monsterType;
        private String monsterSubType;
        private int teamId;
        private MatchPositionDto position;
        private int killerId;
        private long timestamp;
        private List<Integer> assistingParticipantIds;
        private String buildingType;
        private int victimId;
    }
    @Data
    public static class MatchPositionDto{
        private int x;
        private int y;
    }
}
