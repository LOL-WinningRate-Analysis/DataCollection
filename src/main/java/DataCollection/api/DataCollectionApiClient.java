package DataCollection.api;

import DataCollection.domain.LeagueEntryDto;
import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataCollectionApiClient {
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    private String ApiKey = "RGAPI-e5ab97f6-0815-4208-9c5c-d3e3d299fe72";
    private String GET_GAMEDETAIL_URI = "https://kr.api.riotgames.com/lol/match/v4/matches/{gameId}?api_key={ApiKey}";
    private String GET_USERNAME_URI="https://kr.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/{tier}/{division}?page=1500&api_key={ApiKey}"
    private String GET_GAMEIDS_URI="";
    public MatchDetail getMatchDetail(long matchid){
        MatchDetail matchDetail = restTemplate.getForObject(GET_GAMEDETAIL_URI,MatchDetail.class,matchid,ApiKey);
        return matchDetail;
    }

    public List<String> getUserName(String tier, String divisiion){
        Set<LeagueEntryDto> leagueEntryDtos = restTemplate.getForObject(GET_USERNAME_URI, Set.class,tier,divisiion);
        List<LeagueEntryDto> targetList = new ArrayList<>(leagueEntryDtos);
        return  targetList.stream()
                .map(n -> n.getSummonerName())
                .collect(Collectors.toList());
    }

}