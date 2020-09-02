package DataCollection.controller;

import DataCollection.Service.TempDatasService;
import DataCollection.domain.TempDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempDatasController {
    @Autowired
    TempDatasService tempDatasService;

    @GetMapping("/TempDatas/getTempDatas")
    public TempDatas getDatas(@RequestParam long matchId){
        return tempDatasService.getTempDatas(matchId);
    }

    @GetMapping("/TempDatas/getTempDatasList")
    public TempDatas[] getdataList(@RequestParam int DBId){
        return tempDatasService.getDataList(DBId);
    }
    @GetMapping("/TempDats/saveAllDatasList")
    public void saveAllDatasList(@RequestParam int DBId){ tempDatasService.saveAllDatas(DBId);}

}
