package DataCollection.Service;

import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.Datas;
import DataCollection.domain.LeagueEntryDto;
import DataCollection.domain.MatchDetail;
import DataCollection.domain.MatchIds;
import DataCollection.repository.DataCollectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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


    @PostConstruct
    public void setUpmatchIdtoUse() throws IOException {

    }

    public MatchDetail getMatchDetail(long matchId) {
        return dataCollectionApiClient.getMatchDetail(matchId);
    }

    public Datas getDatas(long matchId){
        return dataCollectionRepository.getDatas(matchId);
    }

    public Datas[] getDataList(int DBId){
        return dataCollectionRepository.getDatasList(DBId);
    }

    /*
    @Scheduled(fixedDelay = 1500L)
    public void saveDetailEveryTwoSeconds(){

        MatchDetail matchDetail = dataCollectionApiClient.getMatchDetail(targetmatchId);
        dataCollectionRepository.saveMatchDetail(matchDetail);
        targetmatchId++;
    }*/

    public void getuserName(String tier, String division,int Id,int page) throws InterruptedException {
        LeagueEntryDto[] leagueEntryDtos = dataCollectionApiClient.getUserName(tier, division,page);
        log.info("leagueEntryDtos: {}",leagueEntryDtos);
                for(int i=0;leagueEntryDtos[i]!=null;i++) {
                    Thread.sleep(3000);
                    MatchIds matchIds;
                    matchIds = dataCollectionRepository.makeMatchIds(leagueEntryDtos[i].getSummonerName(),Id+i);
                    try {
                        String accountId = dataCollectionApiClient.getAccountId(leagueEntryDtos[i].getSummonerName());

                    matchIds.setMatchIds(dataCollectionApiClient.getMatchIds(accountId));
                    }catch (HttpClientErrorException ignore){}
                    dataCollectionRepository.saveMatchIds(matchIds);
                    log.info("{}",matchIds);
                }

        //log.info("{}",leagueEntryDtos[0].getSummonerName());
    }

    public void savedetail(int startnum) throws InterruptedException {
        log.info("start");
        for(int i=0;i<=204;i++){
            int temp = startnum+i;
            log.info("{}",temp);
            MatchIds matchIds = new MatchIds();
            if((matchIds = dataCollectionRepository.findMatchIds(temp))!=null){
                for(int j=0;j<100;j++){
                    try{
                    Thread.sleep(1500);
                    MatchDetail matchDetail = getMatchDetail(matchIds.getMatchIds().get(j));
                    dataCollectionRepository.saveMatchDetail(matchDetail);
                    log.info("i= {} j={} Save detail : {}",temp,j,matchDetail);
                }catch (Exception ignore){}
                }
            }
        }
    }
}
