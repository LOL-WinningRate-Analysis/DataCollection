package DataCollection.repository;

import DataCollection.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class MongoDBRepository {

    @Autowired
    MongoTemplate mongoTemplate;
/*
   public List returnAllDetails(int DBId) {
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
                        log.info("i= {} j={}", temp, j, matchDetail);
                    } catch (NullPointerException ignore) {
                    }catch (IndexOutOfBoundsException ignore){}
                }
            }
        }
        log.info("size : {}",matchDetails.size());
        return matchDetails;
    }*/

    public void saveMatchDetail(MatchDetail matchDetail){
    MatchDetail saveDetail1 = mongoTemplate.save(matchDetail);
    log.info("Save: {}", matchDetail.getGameId());
    }

    public MatchDetail findMatchDetail(long matchId){
        Query query = Query.query(
                Criteria.where("_id").is(matchId)
        );
        MatchDetail matchDetail = mongoTemplate.findOne(query, MatchDetail.class);
        return matchDetail;
    }

    public MatchIds findMatchIds(int DBId){
        Query query = Query.query(
                Criteria.where("_id").is(DBId)
        );
        MatchIds matchIds = mongoTemplate.findOne(query, MatchIds.class);
        return matchIds;
    }

    public void saveMatchIds(MatchIds matchIds){
        mongoTemplate.save(matchIds);
        log.info("Save matchIds{}",matchIds);
    }

    public void saveTimeLine(TimeLine timeLine){
        mongoTemplate.save(timeLine);
    }


}


//mongoexport -d test -c collectionName -o output.json --port 27017