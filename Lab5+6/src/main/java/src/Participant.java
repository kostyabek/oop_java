package src;

import java.util.*;


public class Participant implements Comparable<Participant> {
    //Персональні дані
    private String id;
    public String firstName;
    public String lastName;
    public String patronymic;
    public Regions region;   

    //Доходи
    //Поточні місячні
    public double currentMonthlyIncome;
    //Загальні доходи за час перебування у партії
    public double netWorth;

    public Participant(String firstName, String lastName, String patronymic, Regions region, String id, double newWorth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.region = region;
        this.id = id;
        this.netWorth = newWorth;
    }
    
    public Participant(String firstName, String lastName, String patronymic, Regions region, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.region = region;
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.firstName);
        hash = 23 * hash + Objects.hashCode(this.lastName);
        hash = 23 * hash + Objects.hashCode(this.patronymic);
        hash = 23 * hash + Objects.hashCode(this.region);
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
        final Participant other = (Participant) obj;
        return this.id.equals(other.getId());
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, id) + " " + region + " " + netWorth;
    }

    @Override
    public int compareTo(Participant other) {
        return this.id.compareTo(other.id);
    }
}
