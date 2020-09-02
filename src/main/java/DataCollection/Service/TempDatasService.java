package DataCollection.Service;

import DataCollection.domain.TempDatas;
import DataCollection.repository.DataCollectionRepository;
import DataCollection.repository.ReturnAllDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempDatasService {

    @Autowired
    DataCollectionRepository dataCollectionRepository;

    @Autowired
    ReturnAllDataRepository returnAllDataRepository;

    //임시로 모델링의 적합성에 체크할 데이터 추출
    public TempDatas getTempDatas(long matchId){
        return dataCollectionRepository.getTempDatas(matchId);
    }

    public TempDatas[] getDataList(int DBId){
        return dataCollectionRepository.getDatasList(DBId);
    }

    
    //임시 모델링 테스트에 필요한 데이터 mongoDB에 저장
    public void saveAllDatas(int DBId){
        returnAllDataRepository.saveDatasList(DBId);
    }
}
