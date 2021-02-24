package source;

import java.util.*;

public class Participant {
    //Персональні дані
    private String id;
    public String firstName;
    public String lastName;
    public String patronymic;
    public Regions region;

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
    public void vote(PoliticalPower pp, Calendar date) {
        Scanner sc = new Scanner(System.in);
        String voteFirstName = sc.nextLine();
        String voteLastName = sc.nextLine();
        String votePatronymic = sc.nextLine();
        String voteId = sc.nextLine();
        Regions voteRegion = Regions.valueOf(sc.nextLine());
        Participant participant = pp.getParticipant(voteRegion, voteFirstName, voteLastName, votePatronymic, voteId);

        while (participant == this || participant == null) {
            voteFirstName = sc.nextLine();
            voteLastName = sc.nextLine();
            votePatronymic = sc.nextLine();
            voteId = sc.nextLine();
            voteRegion = Regions.valueOf(sc.nextLine());
            participant = pp.getParticipant(voteRegion, voteFirstName, voteLastName, votePatronymic, voteId);
        }
        votedFor.put(date, participant);
        if (participant.votesFrom.get(date) == null)
            participant.votesFrom.put(date, new ArrayList<>());
        participant.votesFrom.get(date).add(this);
    }

    //Голосування при виборі регіонального органу керування
    public void vote(RegionalStruct rs, Calendar date) {
        Scanner sc = new Scanner(System.in);
        String voteFirstName = sc.nextLine();
        String voteLastName = sc.nextLine();
        String votePatronymic = sc.nextLine();
        String voteId = sc.nextLine();
        Participant participant = rs.getParticipant(voteFirstName, voteLastName, votePatronymic, voteId);

        while (participant == this || participant == null) {
            voteFirstName = sc.nextLine();
            voteLastName = sc.nextLine();
            votePatronymic = sc.nextLine();
            voteId = sc.nextLine();
            participant = rs.getParticipant(voteFirstName, voteLastName, votePatronymic, voteId);
        }
        votedFor.put(date, participant);
        if (participant.votesFrom.get(date) == null)
            participant.votesFrom.put(date, new ArrayList<>());
        participant.votesFrom.get(date).add(this);
    }

    //Декларування місячного доходу
    public void declareMonthlyIncome() {
        monthlyIncomes.put(Calendar.getInstance(), currentMonthlyIncome);
        allIncome += currentMonthlyIncome;
        currentMonthlyIncome = 0;
    }

    //Занесення нового доходу в поточну місячну суму
    public void earnMoney(double sumOfMoney) {
        currentMonthlyIncome += sumOfMoney;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, id) + " " + region + " " + allIncome;
    }
}
