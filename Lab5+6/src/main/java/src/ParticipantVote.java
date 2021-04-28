package src;

import java.util.Calendar;

public class ParticipantVote {
    
    private Calendar dateTime;
    private String voterId;
    private String votedForId;

    public ParticipantVote(Calendar dateTime, String voterId, String votedForId) {
        this.voterId = voterId;
        this.votedForId = votedForId;
        this.dateTime = dateTime;
    }
    
    public Calendar getDateAndTime() {
        return dateTime;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getVotedForId() {
        return votedForId;
    }
}
