package DataCollection.repository;

import DataCollection.Service.DataCollectionService;
import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class BaseRepository {

    @Autowired
    DataCollectionService dataCollectionService;
    @Autowired
    DataCollectionApiClient dataCollectionApiClient;

    @Autowired
    private MongoTemplate mongoTemplate;


    public MatchIds makeMatchIds(String summonerName,int Id){
        MatchIds matchIds = new MatchIds();
        matchIds.setSummonerName(summonerName);
        matchIds.setId(Id);
        return  matchIds;
    }

}
