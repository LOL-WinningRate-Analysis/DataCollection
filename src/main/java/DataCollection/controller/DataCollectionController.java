package DataCollection.controller;

import DataCollection.Service.DataCollectionService;
import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.Datas;
import DataCollection.domain.LeagueEntryDto;
import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class DataCollectionController {

    @Autowired
    DataCollectionService dataCollectionService;

    @GetMapping("/GetMatchDetail")
    public MatchDetail getMatchDetail(@RequestParam long matchId){
        return dataCollectionService.getMatchDetail(matchId);
    }

    @GetMapping("/GetDatas")
    public Datas getDatas(@RequestParam long matchId){
        return dataCollectionService.getDatas(matchId);
    }

    @GetMapping("/test")
    public LeagueEntryDto[] getuserName(@RequestParam String tier, @RequestParam String division, @RequestParam int page){
        return dataCollectionService.getuserName(tier, division, page);
    }
}
