package DataCollection.controller;

import DataCollection.Service.DataCollectionService;
import DataCollection.domain.Datas;
import DataCollection.domain.MatchDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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
    public void getuserName(@RequestParam String tier, @RequestParam String division, @RequestParam int Id, @RequestParam int page) throws InterruptedException
    {       dataCollectionService.getuserName(tier, division,Id ,page);
    }

    @GetMapping("/savedetail-DB")
    public void savedetail(@RequestParam int startnum) throws InterruptedException {
        dataCollectionService.savedetail(startnum);
    }

    @GetMapping("/Datasss")
    public Datas[] getdataList(@RequestParam int DBId){
        return dataCollectionService.getDataList(DBId);
    }
}
