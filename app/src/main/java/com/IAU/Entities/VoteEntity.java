package com.IAU.Entities;

import java.util.List;

public class VoteEntity {
    private String voteName;
    private List<VoteOptionEntity> voteOption;
    private Long status;

    public Long getStatus() { return status; }
    public void setStatus(Long status) { this.status = status; }
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
