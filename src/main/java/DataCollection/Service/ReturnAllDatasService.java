package DataCollection.Service;

import DataCollection.domain.MatchDetail;
import DataCollection.repository.ReturnAllDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnAllDatasService {

    @Autowired
    ReturnAllDataRepository returnAllDataRepository;

    public List returnAllDetails(int DBId) {
        return returnAllDataRepository.returnAllDetails(DBId);
    }

    public void saveAllDatas(int DBId){
        returnAllDataRepository.saveDatasList(DBId);
    }
}
