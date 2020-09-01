package DataCollection.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

    @Data
    public class Match {
        @Id
        private String encryptedAccountId;
        private List<Match_> matches;
        /*
        private long startIndex;
        private long endIndex;
        private long totalGames;


         */
        @Data
        public static class Match_{
            //private String platformId;
            private long gameId;
/*
        private int champion;

        private long queue;
        private long season;
        private long timestamp;
        private String role;
        private String lane;

         */
        }
    }

