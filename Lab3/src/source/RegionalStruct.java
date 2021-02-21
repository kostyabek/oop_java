package source;

import java.util.*;

public class RegionalStruct {
    public Regions region;
    //Керуючий орган
    public Participant officer;
    //Список активістів
    public List<Participant> participants = new ArrayList<>();
    //Список тих, хто відправляється на з'їзд партії
    public List<Participant> congressAttendees = new ArrayList<>();

    public RegionalStruct(Regions region) {
        this.region = region;
    }

    //Отримання усіх даних про активіста
    public Participant getParticipant(String firstName, String lastName, String patronymic, String id) {
        for (Participant participant: participants) {
            if (firstName == participant.firstName &&
                lastName == participant.lastName &&
                patronymic == participant.patronymic &&
                id == participant.getId()) {
                return participant;
            }
        }
        return null;
    }

    //Отримання даних про доходи активіста
    public Map<Calendar, Double> getParticipantIncomes(Participant participant, Date start, Date end) {
        if (participant.monthlyIncomes.isEmpty())
            return null;
        Map<Calendar, Double> incomes = new HashMap<>();
        participant.monthlyIncomes.entrySet().forEach(entry -> {
            if (entry.getKey().getTime().getTime() >= start.getTime() &&
                entry.getKey().getTime().getTime() < end.getTime()) {
                incomes.put(entry.getKey(), entry.getValue());
            }
        });
        return incomes;
    }

    //Формування списку активістів, що відправляються на з'їзд партії
    public List<Participant> formCongressList() {
        int numberOfParticipants;
        String firstName;
        String lastName;
        String patronymic;
        String id;
        Participant participant;
        Scanner sc = new Scanner(System.in);

        do {
            numberOfParticipants = sc.nextInt();
        } while (numberOfParticipants < 0 || numberOfParticipants > participants.size());
        do {
            firstName = sc.nextLine();
            lastName = sc.nextLine();
            patronymic = sc.nextLine();
            id = sc.nextLine();
            participant = getParticipant(firstName, lastName, patronymic, id);
            if (participant != null && !congressAttendees.contains(participant)) {
                congressAttendees.add(participant);
            }
        } while (congressAttendees.size() < numberOfParticipants);
        return congressAttendees;
    }
}
