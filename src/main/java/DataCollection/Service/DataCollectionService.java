package DataCollection.Service;

import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.Datas;
import DataCollection.domain.MatchDetail;
import DataCollection.repository.DataCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@EnableScheduling
public class DataCollectionService {

    @Autowired
    DataCollectionApiClient dataCollectionApiClient;

    @Autowired
    DataCollectionRepository dataCollectionRepository;

    long targetmatchId;

    @PostConstruct
    public void setUpmatchIdtoUse() throws IOException {
        targetmatchId = 4512496243L;
    }

    public MatchDetail getMatchDetail(long matchId) {
        return dataCollectionApiClient.getMatchDetail(matchId);
    }

    public Datas getDatas(long matchId){
        return dataCollectionRepository.getDatas(matchId);
    }


    @Scheduled(fixedDelay = 1500L)
    public void saveDetailEveryTwoSeconds(){

        MatchDetail matchDetail = dataCollectionApiClient.getMatchDetail(targetmatchId);
        dataCollectionRepository.saveMatchDetail(matchDetail);
        targetmatchId++;
    }
}
