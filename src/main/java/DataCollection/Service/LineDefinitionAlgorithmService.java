package DataCollection.Service;

import DataCollection.domain.MergedData;
import DataCollection.repository.LineDefinitionAlgorithmRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineDefinitionAlgorithmService {

    @Autowired
    LineDefinitionAlgorithmRepository lineDefinitionAlgorithmRepository;

    public MergedData informationMerge(long matchId) {
        return lineDefinitionAlgorithmRepository.informationMerge(matchId);
    }

    public List test(MergedData mergedData){
        return lineDefinitionAlgorithmRepository.decideJungle(mergedData);
    }

    public List test2(MergedData mergedData){
        return lineDefinitionAlgorithmRepository.decideSupport(mergedData);
    }

    public List test3(MergedData mergedData){ return lineDefinitionAlgorithmRepository.decideBotom(mergedData);}

    public List<List<Integer>> getWeight(MergedData mergedData) {
        return lineDefinitionAlgorithmRepository.getWeight(mergedData);
    }

    public List<Integer> decideLine(MergedData mergedData){
        List<List<Integer>> lists = getWeight(mergedData);
        return lineDefinitionAlgorithmRepository.decideLine(lists);
    }
}
