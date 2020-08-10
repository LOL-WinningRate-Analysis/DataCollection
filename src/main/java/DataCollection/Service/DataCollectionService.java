package DataCollection.Service;

import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.Datas;
import DataCollection.domain.LeagueEntryDto;
import DataCollection.domain.MatchDetail;
import DataCollection.repository.DataCollectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Service
@EnableScheduling
@Slf4j
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

    /*
    @Scheduled(fixedDelay = 1500L)
    public void saveDetailEveryTwoSeconds(){

        MatchDetail matchDetail = dataCollectionApiClient.getMatchDetail(targetmatchId);
        dataCollectionRepository.saveMatchDetail(matchDetail);
        targetmatchId++;
    }*/

    public

    public LeagueEntryDto[] getuserName(String tier, String division, int page){
        LeagueEntryDto[] leagueEntryDtos = dataCollectionApiClient.getUserName(tier, division, page);
        //log.info("{}",leagueEntryDtos[0].getSummonerName());
        dataCollectionRepository.saveEntryList(leagueEntryDtos);
        return leagueEntryDtos;
    }
}
