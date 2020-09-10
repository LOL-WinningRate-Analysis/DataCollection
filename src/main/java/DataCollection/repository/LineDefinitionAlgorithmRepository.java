package DataCollection.repository;

import DataCollection.domain.MatchDetail;
import DataCollection.domain.MergedData;
import DataCollection.domain.TimeLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class LineDefinitionAlgorithmRepository {

    @Autowired
    MongoDBRepository mongoDBRepository;

    public MergedData informationMerge(long matchId) {
        MatchDetail matchDetail = mongoDBRepository.findMatchDetail(matchId);
        TimeLine timeLine = mongoDBRepository.findTimeLine(matchId);
        MergedData mergedData = new MergedData();
        mergedData.setMatchDetail(matchDetail);
        mergedData.setTimeLine(timeLine);
        return mergedData;
    }

    public boolean IsBuySupportItem(MergedData mergedData, int participantId){
        for(int i=1;i<4;i++){
            try {
                List<Integer> temp = mergedData.getTimeLine().getFrames().get(i).getEvents().stream()
                        .filter(e -> e.getParticipantId() == participantId)
                        .map(e -> e.getItemId())
                        .collect(Collectors.toList());

                log.info("{}", temp);
                for (int j : temp) {
                    if (j == 3850 || j == 3854 || j == 3858 || j == 3962)
                        return true;
                }
            }catch (NullPointerException ignore){}
        }
        return false;
    }

    public List decideJungle(MergedData mergedData){
        List<Integer> result = new ArrayList<Integer>();
        for(int i=0;i<10;i++){
            result.add(0);
            if(mergedData.getMatchDetail().getParticipants().get(i).getSpell1Id()==11||
            mergedData.getMatchDetail().getParticipants().get(i).getSpell2Id()==11){
                result.set(i,100);
            }
        }
        for(int i=0;i<6;i++){
            for(int j=1;j<=10;j++){
                try{
                int weight = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getJungleMinionsKilled();
                int participantId=mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getParticipantId();
                int temp = result.get(participantId-1)+weight;
                result.set(participantId-1,temp);
            }catch (NullPointerException ignore){};
            }
        }
        return result;
    }

    public List decideTop(MergedData mergedData){
        List<Integer> result = new ArrayList<Integer>(100);
        for(int i=0;i<10;i++){
            result.add(0);
        }
        for(int i=1;i<7;i++) {
            for (int j = 1; j <= 10; j++) {
                int x = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getPosition().getX();
                int y = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getPosition().getY();
                int X_weight = Math.abs(2000-x);
                int Y_weight = Math.abs(12000-y);
                int participantId=mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getParticipantId();
                log.info("{} {}, {} {} {}",i,j,X_weight,Y_weight,participantId);
                if(X_weight>2500||Y_weight>2500) continue;
                int weight = X_weight+Y_weight;
                weight = 20000/weight;
                if((x<11000&&y>12500)||(x<2500&&y>4000)) weight+=10;

                int temp = result.get(participantId-1)+weight;
                result.set(participantId-1,temp);
                log.info("{}, {}",i,result);
            }
        }
        return result;
    }


    public List decideMid(MergedData mergedData){
        List<Integer> result = new ArrayList<Integer>(100);
        for(int i=0;i<10;i++){
            result.add(0);
        }
        for(int i=1;i<7;i++) {
            for (int j = 1; j <= 10; j++) {
                int x = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getPosition().getX();
                int y = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getPosition().getY();

                int weight = Math.abs(x-y);

                int participantId=mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getParticipantId();

                if(x<4800||y<4800||x>10200||y>10200||weight>2000) continue;

                weight = 400/weight;
                int temp = result.get(participantId-1)+weight;
                result.set(participantId-1,temp);
                log.info("{}, {}",i,result);
            }
        }
        return result;
    }

    public List decideBotom(MergedData mergedData){
        List<Integer> result = new ArrayList<Integer>(100);
        for(int i=0;i<10;i++){
            result.add(0);
        }
        for(int i=1;i<7;i++) {
            for (int j = 1; j <= 10; j++) {
                int x = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getPosition().getX();
                int y = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getPosition().getY();
                int X_weight = Math.abs(12000-x);
                int Y_weight = Math.abs(2000-y);
                int participantId=mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getParticipantId();
                if(X_weight>2500||Y_weight>2500) continue;
                int weight = X_weight+Y_weight;
                weight = 20000/weight;

                int minionkills = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getMinionsKilled();

                weight += minionkills/4;

                if((x>12500&&y<11000)||(x>4000&&y<2500)) weight+=10;
                int temp = result.get(participantId-1)+weight;
                result.set(participantId-1,temp);
            }
        }
        for(int i=1;i<=10;i++){
            if(IsBuySupportItem(mergedData,i)) {
                int temp = result.get(i-1);
                result.set(i - 1, temp/10);
            }
        }
        return result;
    }


    public List decideSupport(MergedData mergedData){
        List<Integer> result = new ArrayList<Integer>(100);
        for(int i=0;i<10;i++){
            result.add(0);
        }
        for(int i=1;i<7;i++) {
            for (int j = 1; j <= 10; j++) {
                int x = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getPosition().getX();
                int y = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getPosition().getY();
                int X_weight = Math.abs(12000-x);
                int Y_weight = Math.abs(2000-y);
                int participantId=mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getParticipantId();
                if(X_weight>2500||Y_weight>2500) continue;
                int weight = X_weight+Y_weight;
                weight = 20000/weight;

                int minionkills = mergedData.getTimeLine().getFrames().get(i).getParticipantFrames().get(Integer.toString(j)).getMinionsKilled();

                weight -= minionkills/3;

                if((x>12500&&y<11000)||(x>4000&&y<2500)) weight+=10;
                int temp = result.get(participantId-1)+weight;
                result.set(participantId-1,temp);
            }
        }
        for(int i=1;i<=10;i++){
            if(!IsBuySupportItem(mergedData,i)) {
                int temp = result.get(i-1);
                result.set(i - 1, temp/10);
            }else{
                int temp = result.get(i-1);
                result.set(i-1,temp+30);
            }
        }
        return result;
    }


    public List<List> getWeight(MergedData mergedData) {
        List<List> result = new ArrayList<List>();
        List top = decideTop(mergedData);
        List jungle = decideJungle(mergedData);
        List mid = decideMid(mergedData);
        List bottom = decideBotom(mergedData);
        List support = decideSupport(mergedData);

        result.add(top);
        result.add(jungle);
        result.add(mid);
        result.add(bottom);
        result.add(support);

        return result;
    }
}
