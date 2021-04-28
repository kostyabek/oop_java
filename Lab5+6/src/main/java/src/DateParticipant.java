package src;

import java.util.Calendar;
import java.util.Objects;

public class DateParticipant implements Comparable<DateParticipant> {

    private Calendar date;
    private Participant participant;

    public DateParticipant(Calendar date, Participant participant) {
        this.date = date;
        this.participant = participant;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDateString() {
        String dateString = String.format("%s.%s.%s %s:%s",
                date.get(Calendar.DAY_OF_MONTH),
                date.get(Calendar.MONTH) + 1,
                date.get(Calendar.YEAR),
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE));

        return dateString;
    }

    public Participant getParticipant() {
        return participant;
    }

    @Override
    public int compareTo(DateParticipant o) {
        return this.participant.getId().compareTo(o.participant.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.date);
        hash = 79 * hash + Objects.hashCode(this.participant);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DateParticipant other = (DateParticipant) obj;
        return this.date.compareTo(other.date) == 0 && this.participant.getId().equals(other.participant.getId());
    }
}
