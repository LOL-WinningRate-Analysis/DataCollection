package DataCollection.repository;

import DataCollection.domain.MatchDetail;
import DataCollection.domain.MatchIds;
import DataCollection.domain.TempDatas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class TempDatasRepository {

    @Autowired
    BaseRepository dataCollectionRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    MongoDBRepository mongoDBRepository;


    //MatchDetail을 Datas로 변환
    public TempDatas MatchDetailtoDatas(MatchDetail matchDetail){
        List temp = matchDetail.getParticipants().stream()
                .map(c -> c.getChampionId())
                .collect(Collectors.toList());
        TempDatas datas = new TempDatas();

        if(matchDetail.getTeams().get(0).getWin().equals("Win")){
            temp.add(0);
        }
        else {temp.add(1);}

        datas.setArray(temp);
        datas.setMatchId(matchDetail.getGameId());
        return datas;
    }

    public TempDatas getTempDatas(long matchId){
        MatchDetail matchDetail = mongoDBRepository.findMatchDetail(matchId);
        return MatchDetailtoDatas(matchDetail);
    }

    public TempDatas[] getDatasList(int DBId){
        MatchIds matchIds = mongoDBRepository.findMatchIds(DBId);
        log.info("matchIds : {}",matchIds);
        List<Long> onlymatchIds = matchIds.getMatchIds();
        log.info("only : {}",onlymatchIds);
        TempDatas[] datas = new TempDatas[100];
        int j=0;
        for(Long i : onlymatchIds){
            datas[j] = getTempDatas(i);
            log.info("datas {}",datas[j]);
            j++;
        }
        return datas;
    }

    public void saveDatasList(int DBId) {
        List<MatchDetail> matchDetails = new LinkedList<>();
        log.info("start");
        for (int i = 0; i <= 200; i++) {
            int temp = DBId + i;
            log.info("{}", temp);
            MatchIds matchIds = new MatchIds();
            if ((matchIds = mongoDBRepository.findMatchIds(temp)) != null) {
                for (int j = 0; j < 100; j++) {
                    try {
                        MatchDetail matchDetail = mongoDBRepository.findMatchDetail(matchIds.getMatchIds().get(j));
                        matchDetails.add(matchDetail);
                        TempDatas datas = MatchDetailtoDatas(matchDetail);
                        mongoTemplate.save(datas);
                        log.info("i= {} j={} save {}", temp, j, datas);
                    } catch (NullPointerException ignore) {
                    }catch (IndexOutOfBoundsException ignore){}
                }
            }
        }
        log.info("size : {}",matchDetails.size());
    }
}
