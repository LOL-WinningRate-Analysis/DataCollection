package DataCollection.Service;

import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCollectionService {

    @Autowired
    DataCollectionApiClient dataCollectionApiClient;

    public MatchDetail getMatchDetail(int matchId) {
        return dataCollectionApiClient.getMatchDetail(matchId);
    }
}
