package DataCollection.repository;

import DataCollection.Service.DataCollectionService;
import DataCollection.domain.Datas;
import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataCollectionRepository {

    @Autowired
    DataCollectionService dataCollectionService;

    //MatchDetail을 Datas로 변환
    public Datas MatchDetailtoDatas(MatchDetail matchDetail){
        List temp = matchDetail.getParticipants().stream()
        .map(c -> c.getChampionId())
        .collect(Collectors.toList());
        Datas datas = new Datas();

        if(matchDetail.getTeams().get(0).getWin()=="Win"){
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

}
