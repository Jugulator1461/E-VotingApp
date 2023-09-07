package com.IAU.Entities;

import java.io.Serializable;

public class VoteOptionEntity implements Serializable {

    private String optionName;
    private Long optionVoteCount;
    private Long optionStatus;

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
    public Long getOptionStatus() {
        return optionStatus;
    }

    public void setOptionStatus(Long optionStatus) {
        this.optionStatus = optionStatus;
    }

}
