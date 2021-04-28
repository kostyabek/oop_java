package src;

public class PositionParticipant {
    private String position;
    private String participantId;

    public PositionParticipant(String position, String participantId) {
        this.position = position;
        this.participantId = participantId;
    }

    public String getPosition() {
        return position;
    }

    public String getParticipantId() {
        return participantId;
    }
}
