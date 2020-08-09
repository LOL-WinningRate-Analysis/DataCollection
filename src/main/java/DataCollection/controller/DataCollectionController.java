package DataCollection.controller;

import DataCollection.Service.DataCollectionService;
import DataCollection.domain.Datas;
import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
