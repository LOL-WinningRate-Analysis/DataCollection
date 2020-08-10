package DataCollection.repository;

import DataCollection.Service.DataCollectionService;
import DataCollection.domain.Datas;
import DataCollection.domain.MatchDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DataCollectionRepository {

    @Autowired
    DataCollectionService dataCollectionService;

    @Autowired
    private MongoTemplate mongoTemplate;

    //MatchDetail을 Datas로 변환
    public Datas MatchDetailtoDatas(MatchDetail matchDetail){
        List temp = matchDetail.getParticipants().stream()
        .map(c -> c.getChampionId())
        .collect(Collectors.toList());
        Datas datas = new Datas();

        if(matchDetail.getTeams().get(0).getWin().equals("Win")){
            temp.add(0);
        }
        else {temp.add(1);}

        datas.setArray(temp);
        return datas;
    }

    public Datas getDatas(long matchId){
        MatchDetail matchDetail = dataCollectionService.getMatchDetail(matchId);
        return MatchDetailtoDatas(matchDetail);
    }

    public void saveMatchDetail(MatchDetail matchDetail){
        if(matchDetail.getQueueId()==420){
            MatchDetail saveDetail1 = mongoTemplate.save(matchDetail);
            log.info("Save: {}", matchDetail.getGameId());
        }else{
            log.info("{}'s queueId is {}",matchDetail.getGameId(),matchDetail.getQueueId());
        }

    }

}
