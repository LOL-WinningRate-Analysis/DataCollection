package DataCollection.controller;

import DataCollection.Service.DataCollectionService;
import DataCollection.Service.TempDatasService;
import DataCollection.domain.TempDatas;
import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataCollectionController {

    @Autowired
    DataCollectionService dataCollectionService;

    @Autowired
    TempDatasService tempDatasService;

    /*
    @GetMapping("/GetMatchDetail")
    public MatchDetail getMatchDetail(@RequestParam long matchId){
        return dataCollectionService.getMatchDetail(matchId);
    }

     */

    @GetMapping("/Save/saveUserInformation")
    public void saveUserInformation(@RequestParam String tier, @RequestParam String division, @RequestParam int Id, @RequestParam int page) throws InterruptedException
    {       dataCollectionService.saveUserInformation(tier, division,Id ,page);
    }

    @GetMapping("/Save/saveMatchDetail")
    public void savedetail(@RequestParam int startnum) throws InterruptedException {
        dataCollectionService.savedetail(startnum);
    }

    @GetMapping("/Save/saveMatchTimeLine")
    public void saveTimeLine(@RequestParam int DBId){dataCollectionService.saveTimeLine(DBId);}
}
