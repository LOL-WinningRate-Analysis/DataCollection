package DataCollection.api;

import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataCollectionApiClient {
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    private String ApiKey = "RGAPI-3e0cb194-512c-4e6c-ac34-ec2f145f8728";
    private String GET_GAMEDETAIL_URI = "https://kr.api.riotgames.com/lol/match/v4/matches/{gameId}?api_key={ApiKey}";
    public MatchDetail getMatchDetail(long matchid){
        MatchDetail matchDetail = restTemplate.getForObject(GET_GAMEDETAIL_URI,MatchDetail.class,matchid,ApiKey);
        return matchDetail;
    }

}