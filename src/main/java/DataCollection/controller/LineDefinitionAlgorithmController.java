package DataCollection.controller;

import DataCollection.Service.LineDefinitionAlgorithmService;
import DataCollection.domain.MergedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LineDefinitionAlgorithmController {

    @Autowired
    LineDefinitionAlgorithmService lineDefinitionAlgorithmService;

    @GetMapping("/LineDefinition/test")
    public MergedData informationMerge(@RequestParam long matchId){
        return lineDefinitionAlgorithmService.informationMerge(matchId);
    }

    @GetMapping("/Test")
    public List test(@RequestParam long matchId){
        MergedData mergedData = lineDefinitionAlgorithmService.informationMerge(matchId);
        return lineDefinitionAlgorithmService.test(mergedData);
    }


    @GetMapping("/Test2")
    public List test2(@RequestParam long matchId) {
        MergedData mergedData = lineDefinitionAlgorithmService.informationMerge(matchId);
        return lineDefinitionAlgorithmService.test2(mergedData);
    }

    @GetMapping("/Test3")
    public List test3(@RequestParam long matchId){
        MergedData mergedData = lineDefinitionAlgorithmService.informationMerge(matchId);
        return lineDefinitionAlgorithmService.test3(mergedData);
    }

    @GetMapping("/Weight")
    public List<List<Integer>> getWeight(@RequestParam long matchId){
        MergedData mergedData = lineDefinitionAlgorithmService.informationMerge(matchId);
        return lineDefinitionAlgorithmService.getWeight(mergedData);
    }

    @GetMapping("/decide")
    public List decideLine(@RequestParam long matchId){
        MergedData mergedData = lineDefinitionAlgorithmService.informationMerge(matchId);
        return lineDefinitionAlgorithmService.decideLine(mergedData);
    }
}
