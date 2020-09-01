package DataCollection.domain;

import lombok.Data;

import java.util.List;

@Data
public class NameList {
    private String tier;
    private String division;
    private List<String> Names;
}
