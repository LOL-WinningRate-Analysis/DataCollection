package DataCollection.controller;

import DataCollection.Service.ReturnAllDatasService;
import DataCollection.domain.MatchDetail;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReturnAllDatasController {

    @Autowired
    ReturnAllDatasService returnAllDatasService;

    @GetMapping("/returnAllDetails")
    public List returnAllDetails(@RequestParam int DBId){
        return returnAllDatasService.returnAllDetails(DBId);
    }


}
