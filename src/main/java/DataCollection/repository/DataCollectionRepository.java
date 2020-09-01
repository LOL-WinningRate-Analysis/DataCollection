package DataCollection.repository;

import DataCollection.Service.DataCollectionService;
import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DataCollectionRepository {

    @Autowired
    DataCollectionService dataCollectionService;
    @Autowired
    DataCollectionApiClient dataCollectionApiClient;

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
        datas.setMatchId(matchDetail.getGameId());
        return datas;
    }

    public Datas getDatas(long matchId){
        MatchDetail matchDetail = findMatchDetail(matchId);
        return MatchDetailtoDatas(matchDetail);
    }

    public Datas[] getDatasList(int DBId){
        MatchIds matchIds = findMatchIds(DBId);
        log.info("matchIds : {}",matchIds);
        List<Long> onlymatchIds = matchIds.getMatchIds();
        log.info("only : {}",onlymatchIds);
        Datas[] datas = new Datas[100];
        int j=0;
        for(Long i : onlymatchIds){
            datas[j] = getDatas(i);
            log.info("datas {}",datas[j]);
            j++;
        }
    return datas;
    }

    public void saveMatchDetail(MatchDetail matchDetail){
            MatchDetail saveDetail1 = mongoTemplate.save(matchDetail);
            log.info("Save: {}", matchDetail.getGameId());
    }

    public void saveEntryList(LeagueEntryDto[] leagueEntryDtos){
        NameList savenameList = new NameList();
        savenameList.setNames(Arrays.stream(leagueEntryDtos).map(c -> c.getSummonerName()).collect(Collectors.toList()));
        savenameList.setTier(leagueEntryDtos[0].getTier());
        savenameList.setDivision(leagueEntryDtos[0].getRank());
        mongoTemplate.save(savenameList);
    }

    public void savematchIds(List<String> matchids){
        mongoTemplate.save(matchids);
    }


    public MatchIds makeMatchIds(String summonerName,int Id){
        MatchIds matchIds = new MatchIds();
        matchIds.setSummonerName(summonerName);
        matchIds.setId(Id);
        return  matchIds;
    }

    public void saveMatchIds(MatchIds matchIds){
        mongoTemplate.save(matchIds);
        log.info("Save matchIds{}",matchIds);
    }

    public MatchIds findMatchIds(int DBId){
        Query query = Query.query(
                Criteria.where("_id").is(DBId)
        );
        MatchIds matchIds = mongoTemplate.findOne(query, MatchIds.class);
        return matchIds;
    }

    public MatchDetail findMatchDetail(long matchId){
        Query query = Query.query(
                Criteria.where("_id").is(matchId)
        );
        MatchDetail matchDetail = mongoTemplate.findOne(query, MatchDetail.class);
        return matchDetail;
    }

    public void saveTimeLine(TimeLine timeLine){
        mongoTemplate.save(timeLine);
    }
}
