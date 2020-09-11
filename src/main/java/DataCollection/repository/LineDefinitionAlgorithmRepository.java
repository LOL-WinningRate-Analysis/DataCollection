package DataCollection.repository;

import DataCollection.api.DataCollectionApiClient;
import DataCollection.domain.MatchDetail;
import DataCollection.domain.MergedData;
import DataCollection.domain.TimeLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class LineDefinitionAlgorithmRepository {

    @Autowired
    MongoDBRepository mongoDBRepository;

    @Autowired
    DataCollectionApiClient dataCollectionApiClient;

    public MergedData informationMerge(long matchId) {
        MatchDetail matchDetail = mongoDBRepository.findMatchDetail(matchId);
        TimeLine timeLine = mongoDBRepository.findTimeLine(matchId);

        if(matchDetail==null)
            matchDetail = dataCollectionApiClient.getMatchDetail(matchId);
        if(timeLine==null)
            timeLine = dataCollectionApiClient.getTimeLine(matchId);

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

    public List<Integer> decideJungle(MergedData mergedData){
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

    public List<Integer> decideTop(MergedData mergedData){
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


    public List<Integer> decideMid(MergedData mergedData){
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

    public List<Integer> decideBotom(MergedData mergedData){
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


    public List<Integer> decideSupport(MergedData mergedData){
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


    public List<List<Integer>> getWeight(MergedData mergedData) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
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

    public List<Integer> decideLine(List<List<Integer>> lists){
        List<Integer> participantsIds = new ArrayList<Integer>();
        for(int i=0;i<10;i++) {
            participantsIds.add(0);
        }

        int max_team1=0;
        int max_team2=0;

        for(int a=0;a<5;a++){
            if(lists.get(0).get(a)==0) continue;
            for(int b=0;b<5;b++){
                if(lists.get(1).get(b)==0) continue;
                if(b==a) continue;
                for(int c=0;c<5;c++){
                    if(lists.get(2).get(c)==0) continue;
                    if(c==a||c==b) continue;
                    for(int d=0;d<5;d++){
                        if(lists.get(3).get(d)==0) continue;
                        if(d==c||d==b||d==a) continue;
                        for(int e=0;e<5;e++){
                            if(e==d||e==c||e==b||e==a) continue;
                            int temp = lists.get(0).get(a)+lists.get(1).get(b)+lists.get(2).get(c)+lists.get(3).get(d)+lists.get(4).get(e);
                            if(temp>max_team1){
                                participantsIds.set(0,a);
                                participantsIds.set(1,b);
                                participantsIds.set(2,c);
                                participantsIds.set(3,d);
                                participantsIds.set(4,e);
                                max_team1=temp;
                            }
                        }
                    }
                }
            }
        }
        for(int a=5;a<10;a++){
            if(lists.get(0).get(a)==0) continue;
            for(int b=5;b<10;b++){
                if(lists.get(1).get(b)==0) continue;
                if(b==a) continue;
                for(int c=5;c<10;c++){
                    if(lists.get(2).get(c)==0) continue;
                    if(c==a||c==b) continue;
                    for(int d=5;d<10;d++){
                        if(lists.get(3).get(d)==0) continue;
                        if(d==c||d==b||d==a) continue;
                        for(int e=5;e<10;e++){
                            if(e==d||e==c||e==b||e==a) continue;
                            int temp = lists.get(0).get(a)+lists.get(1).get(b)+lists.get(2).get(c)+lists.get(3).get(d)+lists.get(4).get(e);
                            if(temp>max_team2){
                                participantsIds.set(5,a);
                                participantsIds.set(6,b);
                                participantsIds.set(7,c);
                                participantsIds.set(8,d);
                                participantsIds.set(9,e);
                                max_team2=temp;
                            }
                        }
                    }
                }
            }
        }

        return participantsIds;

    }
}
