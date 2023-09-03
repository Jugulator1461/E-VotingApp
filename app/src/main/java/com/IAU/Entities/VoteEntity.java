package com.IAU.Entities;

import java.io.Serializable;
import java.util.List;

public class VoteEntity implements Serializable {
    private String voteName;
    private List<VoteOptionEntity> voteOption;
    private List<String> voterUserId;
    private Long status;

    public List<String> getVoterUserId() {
        return voterUserId;
    }

    public void setVoterUserId(List<String> voterUserId) {
        this.voterUserId = voterUserId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<VoteOptionEntity> getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(List<VoteOptionEntity> voteOption) {
        this.voteOption = voteOption;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public String getVoteName() {
        return voteName;
    }

}
