package DataCollection.Service;

import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.*;
import DataCollection.repository.BaseRepository;
import DataCollection.repository.MongoDBRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;


@Service
@EnableScheduling
@Slf4j
public class DataCollectionService {

    @Autowired
    DataCollectionApiClient dataCollectionApiClient;

    @Autowired
    BaseRepository dataCollectionRepository;

    @Autowired
    MongoDBRepository mongoDBRepository;


    public MatchDetail getMatchDetail(long matchId) {
        return dataCollectionApiClient.getMatchDetail(matchId);
    }



    /*
    @Scheduled(fixedDelay = 1500L)
    public void saveDetailEveryTwoSeconds(){

        MatchDetail matchDetail = dataCollectionApiClient.getMatchDetail(targetmatchId);
        dataCollectionRepository.saveMatchDetail(matchDetail);
        targetmatchId++;
    }*/

    public void saveUserInformation(String tier, String division,int Id,int page) throws InterruptedException {
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
                    mongoDBRepository.saveMatchIds(matchIds);
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
            if((matchIds = mongoDBRepository.findMatchIds(temp))!=null){
                for(int j=0;j<100;j++){
                    try{
                        if(matchIds.getMatchIds().get(j)==null)
                            break;
                    Thread.sleep(1500);
                    MatchDetail matchDetail = getMatchDetail(matchIds.getMatchIds().get(j));
                    mongoDBRepository.saveMatchDetail(matchDetail);
                    log.info("i= {} j={} Save detail : {}",temp,j,matchDetail);
                }catch (Exception ignore){}
                }
            }
        }
    }

    public void saveTimeLine(int DBId) {
        log.info("start");
        for (int i = 0; i <= 200; i++) {
            int temp = DBId + i;
            log.info("{}", temp);
            MatchIds matchIds = new MatchIds();
            if ((matchIds = mongoDBRepository.findMatchIds(temp)) != null) {
                for (int j = 0; j < 100; j++) {
                    try {

                        long matchid = matchIds.getMatchIds().get(j);
                        Thread.sleep(2000);
                        TimeLine timeLine = dataCollectionApiClient.getTimeLine(matchid);
                        timeLine.setMatchid(matchid);
                        mongoDBRepository.saveTimeLine(timeLine);
                        log.info("i= {} j={} \n {}", temp, j, timeLine);
                    } catch (NullPointerException ignore) {
                    }catch (IndexOutOfBoundsException ignore){} catch (InterruptedException e) {
                    }catch (HttpServerErrorException htt){}
                }
            }
        }
    }
}
