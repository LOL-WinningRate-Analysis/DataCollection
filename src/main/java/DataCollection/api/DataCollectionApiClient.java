package DataCollection.api;

import DataCollection.domain.LeagueEntryDto;
import DataCollection.domain.MatchDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class DataCollectionApiClient {
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    private String ApiKey = "RGAPI-e5ab97f6-0815-4208-9c5c-d3e3d299fe72";
    private String GET_GAMEDETAIL_URI = "https://kr.api.riotgames.com/lol/match/v4/matches/{gameId}?api_key={ApiKey}";
    private String GET_USERNAME_URI="https://kr.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/{tier}/{division}?page={page}&api_key={ApiKey}";
    private String GET_GAMEIDS_URI="";

    public MatchDetail getMatchDetail(long matchid){
        MatchDetail matchDetail = restTemplate.getForObject(GET_GAMEDETAIL_URI,MatchDetail.class,matchid,ApiKey);
        return matchDetail;
    }

    public LeagueEntryDto[] getUserName(String tier, String divisiion, int page){
        LeagueEntryDto[] leagueEntryDtoSet = restTemplate.getForObject(GET_USERNAME_URI, LeagueEntryDto[].class,tier,divisiion,page,ApiKey);
        log.info("{}",leagueEntryDtoSet);
        return leagueEntryDtoSet;
    }

}