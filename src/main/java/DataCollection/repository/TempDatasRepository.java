package DataCollection.repository;

import DataCollection.domain.MatchDetail;
import DataCollection.domain.MatchIds;
import DataCollection.domain.TempDatas;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Repository
@Slf4j
public class TempDatasRepository {

    @Autowired
    DataCollectionRepository dataCollectionRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveDatasList(int DBId) {
        List<MatchDetail> matchDetails = new LinkedList<>();
        log.info("start");
        for (int i = 0; i <= 200; i++) {
            int temp = DBId + i;
            log.info("{}", temp);
            MatchIds matchIds = new MatchIds();
            if ((matchIds = dataCollectionRepository.findMatchIds(temp)) != null) {
                for (int j = 0; j < 100; j++) {
                    try {
                        MatchDetail matchDetail = dataCollectionRepository.findMatchDetail(matchIds.getMatchIds().get(j));
                        matchDetails.add(matchDetail);
                        TempDatas datas = dataCollectionRepository.MatchDetailtoDatas(matchDetail);
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
