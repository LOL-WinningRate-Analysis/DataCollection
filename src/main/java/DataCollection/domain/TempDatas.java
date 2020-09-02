package DataCollection.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class TempDatas {
    @Id
    private Long matchId;
    private List<Integer> array;    //[championId1,...,championId10,Win] teamId 100이 이기면 Win=0, teamId 200이 이기면 Win=1
                                    //championId1~5까지는 team100의 챔피언들, 6~10까지는 team200의 챔피언들
}
