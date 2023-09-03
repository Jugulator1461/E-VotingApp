package com.IAU.Entities;

import java.io.Serializable;

public class VoteOptionEntity implements Serializable {

    private String optionName;
    private Long optionVoteCount;

    public Long getOptionVoteCount() {
        return optionVoteCount;
    }
    public void setOptionVoteCount(Long optionVoteCount) {
        this.optionVoteCount = optionVoteCount;
    }

    public String getOptionName() {
        return optionName;
    }
    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

}
