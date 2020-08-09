package DataCollection.api;

import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataCollectionApiClient {
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    private String BUSARRIVALITEM_REQUEST_URI = "https://kr.api.riotgames.com/lol/match/v4/matches/{gameId}?api_key={ApiKey}";
    public MatchDetail getMatchDetail(int matchid){
        MatchDetail matchDetail = restTemplate.getForObject(BUSARRIVALITEM_REQUEST_URI,MatchDetail.class,);
        return matchDetail;
    }

}