package DataCollection.repository;

import DataCollection.domain.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ReturnAllDataRepository {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    DataCollectionRepository dataCollectionRepository;

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
    }

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
                        Datas datas = dataCollectionRepository.MatchDetailtoDatas(matchDetail);
                        mongoTemplate.save(datas);
                        log.info("i= {} j={} save {}", temp, j, datas);
                    } catch (NullPointerException ignore) {
                    }catch (IndexOutOfBoundsException ignore){}
                }
            }
        }
        log.info("size : {}",matchDetails.size());
    }

    public void saveTimeLine(int DBId) {
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
                        Datas datas = dataCollectionRepository.MatchDetailtoDatas(matchDetail);
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


//mongoexport -d test -c collectionName -o output.json --port 27017