package com.IAU.Entities;

import java.util.List;

public class VoteEntityList {
    private String voteDbName;
    private List<VoteEntity> voteEntityList;

    public String getVoteDbName() {
        return voteDbName;
    }

    public void setVoteDbName(String voteDbName) {
        this.voteDbName = voteDbName;
    }
    public List<VoteEntity> getVoteEntityList() {
        return voteEntityList;
    }

    public void setVoteEntityList(List<VoteEntity> voteEntityList) {
        this.voteEntityList = voteEntityList;
    }
}
