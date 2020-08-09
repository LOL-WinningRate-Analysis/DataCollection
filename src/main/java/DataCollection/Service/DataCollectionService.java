package DataCollection.Service;

import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.Datas;
import DataCollection.domain.MatchDetail;
import DataCollection.repository.DataCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCollectionService {

    @Autowired
    DataCollectionApiClient dataCollectionApiClient;

    @Autowired
    DataCollectionRepository dataCollectionRepository;

    public MatchDetail getMatchDetail(long matchId) {
        return dataCollectionApiClient.getMatchDetail(matchId);
    }

    public Datas getDatas(long matchId){
        return dataCollectionRepository.getDatas(matchId);
    }
}
