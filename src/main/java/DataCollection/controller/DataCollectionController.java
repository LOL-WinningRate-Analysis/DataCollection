package DataCollection.controller;

import DataCollection.Service.DataCollectionService;
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
    public MatchDetail getMatchDetail(@RequestParam int matchId){
        return dataCollectionService.getMatchDetail(matchId);
    }

}
