package source;

import java.io.InputStream;
import java.util.*;

public class Participant {
    //Персональні дані
    private String id;
    public String firstName;
    public String lastName;
    public String patronymic;
    public Regions region;
    public PoliticalPower politicalPower;
    public RegionalStruct regionalStruct;

    //Дані про голосування
    //За кого голосував і коли
    public Map<Calendar, Participant> votedFor = new HashMap<>();
    //Хто голосував за цього активіста і коли
    public Map<Calendar, ArrayList<Participant>> votesFrom = new HashMap<>();

    //Доходи
    //Поточні місячні
    public double currentMonthlyIncome;
    //Загальні доходи за час перебування у партії
    public double allIncome;
    //Задекларовані помісячно
    public Map<Calendar, Double> monthlyIncomes = new HashMap<>();

    public Participant(String firstName, String lastName, String patronymic, Regions region, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.region = region;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    //Голосування при виборі головного органу керування
    public Participant voteGlobal(Calendar date) {
        if (date == null) {
            return new Participant("", "", "", Regions.KYIV, "");
        }

        Scanner sc = new Scanner(System.in);
        String voteId;
        Participant participant;

        do {
            voteId = sc.nextLine();
            participant = politicalPower.getParticipant(voteId);
        } while (participant == this || participant == null);
        votedFor.put(date, participant);

        if (participant.votesFrom.get(date) == null)
            participant.votesFrom.put(date, new ArrayList<>());
        participant.votesFrom.get(date).add(this);

        return participant;
    }

    //Голосування при виборі регіонального органу керування
    public Participant voteRegional(Calendar date) {
        if (regionalStruct == null) {
            return new Participant("", "", "", Regions.KYIV, "");
        }
        if (date == null) {
            return new Participant("", "", "", Regions.KYIV, "");
        }

        Participant participant = getParticipantToVoteFor(regionalStruct);
        votedFor.put(date, participant);

        if (participant.votesFrom.get(date) == null) {
            participant.votesFrom.put(date, new ArrayList<>());
        }
        participant.votesFrom.get(date).add(this);

        return participant;
    }

    private Participant getParticipantToVoteFor(RegionalStruct rs) {
        Scanner sc = new Scanner(System.in);
        String voteId;
        Participant participant;

        do {
            voteId = sc.nextLine();
            participant = rs.getParticipant(voteId);
        } while (participant == this || participant == null);

        return participant;
    }

    //Декларування місячного доходу
    public double declareMonthlyIncome() {
        monthlyIncomes.put(Calendar.getInstance(), currentMonthlyIncome);
        allIncome += currentMonthlyIncome;
        currentMonthlyIncome = 0;

        return currentMonthlyIncome;
    }

    //Занесення нового доходу в поточну місячну суму
    public double earnMoney(double sumOfMoney) {
        if (sumOfMoney > 0) {
            currentMonthlyIncome += sumOfMoney;
        }
        return currentMonthlyIncome;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, id) + " " + region + " " + allIncome;
    }
}
