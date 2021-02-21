package source;

import java.util.*;

public class PolitPower {
    public String name;
    //Список регіональних структур (регіон - структура)
    public Map<Regions, RegionalStruct> regionalStructs = new HashMap<>();
    //Список проведених з'їздів по датах (дата - список присутніх активістів)
    public Map<Calendar, List<Participant>> congresses = new HashMap<>();

    //Головний керівний орган партії
    public Participant leader = null;
    public Participant secretary = null;
    public Participant treasurer = null;
    public Participant presiding = null;

    public PolitPower(String name) {
        this.name = name;
    }

    //Додавання нового активіста
    public void addParticipant(Participant person) {
        if (regionalStructs.get(person.region) == null) {
            regionalStructs.put(person.region, new RegionalStruct(person.region));
        }
        regionalStructs.get(person.region).participants.add(person);
    }

    //Отримання інформації про конкретного активіста
    public Participant getParticipant(Regions region, String firstName, String lastName, String patronymic, String id) {
        return regionalStructs.get(region).getParticipant(firstName, lastName, patronymic, id);
    }

    public List<Participant> getParticipantsByCriteria(String criteria) {
        List<Participant> result = new ArrayList<>();
        String criteriaLowerCased = criteria.toLowerCase();

        for (Regions region: regionalStructs.keySet()) {
            for (Participant participant: regionalStructs.get(region).participants) {
                if (participant.firstName.toLowerCase().contains(criteriaLowerCased) ||
                participant.lastName.toLowerCase().contains(criteriaLowerCased) ||
                participant.patronymic.toLowerCase().contains(criteriaLowerCased) ||
                participant.getId().contains(criteriaLowerCased) ||
                participant.region.name().toLowerCase().contains(criteriaLowerCased)) {
                    result.add(participant);
                }
            }
        }
        if (result.isEmpty())
            return null;
        return result;
    }

    //Проведення з'їзду та формування загального списку тих, кто був
    public void conductCongress() {
        Calendar congressDate = Calendar.getInstance();
        List<Participant> congressAttendeesGlobal = new ArrayList<>();

        for (Regions region: regionalStructs.keySet()) {
            congressAttendeesGlobal.addAll(regionalStructs.get(region).formCongressList());
        }
        congresses.put(congressDate, congressAttendeesGlobal);
    }

    //Проведення виборів головного керуючого органу
    public void conductElection(String position) {
        Calendar date = Calendar.getInstance();
        Scanner sc = new Scanner(System.in);
        Participant winner = null;
        int votes = 0;

        while (!position.matches("(leader)|(secretary)|(treasurer)|(presiding)")) {
            position = sc.nextLine();
        }
        for (Regions region: regionalStructs.keySet()) {
            for (Participant participant: regionalStructs.get(region).participants) {
                participant.vote(this, date);
            }
        }
        for (Regions region: regionalStructs.keySet()) {
            for (Participant participant: regionalStructs.get(region).participants) {
                if (participant.votesFrom.size() > votes) {
                    votes = participant.votesFrom.size();
                    winner = participant;
                }
            }
        }
        switch (position) {
            case "leader":
                leader = winner;
                break;
            case "secretary":
                secretary = winner;
                break;
            case "treasurer":
                treasurer = winner;
                break;
            case "presiding":
                presiding = winner;
                break;
        }
    }

    //Проведення виборів регіональних керуючих органів
    public void conductRegionalElection() {
        Calendar date = Calendar.getInstance();
        Scanner sc = new Scanner(System.in);
        Participant winner = null;
        int votes = 0;

        for (Regions region: regionalStructs.keySet()) {
            for (Participant participant: regionalStructs.get(region).participants) {
                participant.vote(regionalStructs.get(participant.region), date);
            }
        }
        for (Regions region: regionalStructs.keySet()) {
            for (Participant participant: regionalStructs.get(region).participants) {
                if (participant.votesFrom.size() > votes) {
                    votes = participant.votesFrom.size();
                    winner = participant;
                }
            }
            regionalStructs.get(region).officer = winner;
        }
    }

    //Сортування списків активістів, що були на з'їздах
    public void sortCongressAttendees() {
        Scanner sc = new Scanner(System.in);
        String field = null;
        String order = null;

        do {
            field = sc.nextLine();
            order = sc.nextLine();
        } while (!field.matches("(fn)|(ln)|(n)|(reg)|(inc)") || !order.matches("[a]|[d]"));
        List<List<Participant>> participantLists = new ArrayList<>(congresses.values());
        if (order == "a") {
            switch (field) {
                case "fn":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickFirstName(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
                case "ln":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickLastName(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
                case "n":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickName(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
                case "reg":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickRegion(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
                case "inc":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickIncome(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
            }
        }
        else if (order == "b") {
            switch (field) {
                case "fn":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickFirstNameReversed(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
                case "ln":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickLastNameReversed(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
                case "n":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickNameReversed(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
                case "reg":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickRegionReversed(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
                case "inc":
                    for (int i = 0; i < participantLists.size(); i++) {
                        sortQuickIncomeReversed(participantLists.get(i), 0, participantLists.size() - 1);
                    }
                    break;
            }
        }
    }

    //За доходами по зростанню
    private void sortQuickIncome(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;

        while (i <= j)
        {
            while (arr.get(i).allIncome < arr.get(pivot).allIncome) {
                i++;
            }
            while (arr.get(i).allIncome > arr.get(pivot).allIncome) {
                j--;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За доходами по спаданню
    private void sortQuickIncomeReversed(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;

        while (i <= j)
        {
            while (arr.get(i).allIncome > arr.get(pivot).allIncome) {
                i++;
            }
            while (arr.get(i).allIncome < arr.get(pivot).allIncome) {
                j--;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За регіонами по зростанню
    private void sortQuickRegion(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;

        while (i <= j)
        {
            while (arr.get(i).region.name().compareToIgnoreCase(arr.get(pivot).region.name()) < 0) {
                i++;
            }
            while (arr.get(j).region.name().compareToIgnoreCase(arr.get(pivot).region.name()) > 0) {
                j--;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За регіонами по спаданню
    private void sortQuickRegionReversed(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;

        while (i <= j)
        {
            while (arr.get(i).region.name().compareToIgnoreCase(arr.get(pivot).region.name()) > 0) {
                i++;
            }
            while (arr.get(j).region.name().compareToIgnoreCase(arr.get(pivot).region.name()) < 0) {
                j--;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За П.І.Б. по зростанню
    private void sortQuickName(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;
        String pivotName = arr.get(pivot).lastName + arr.get(pivot).firstName + arr.get(pivot).patronymic;
        String iName = arr.get(i).lastName + arr.get(i).firstName + arr.get(i).patronymic;
        String jName = arr.get(j).lastName + arr.get(j).firstName + arr.get(j).patronymic;

        while (i <= j)
        {
            while (iName.compareToIgnoreCase(pivotName) < 0) {
                i++;
                iName = arr.get(i).lastName + arr.get(i).firstName + arr.get(i).patronymic;
            }
            while (jName.compareToIgnoreCase(pivotName) > 0) {
                j--;
                jName = arr.get(j).lastName + arr.get(j).firstName + arr.get(j).patronymic;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За П.І.Б. по спаданню
    private void sortQuickNameReversed(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;
        String pivotName = arr.get(pivot).lastName + arr.get(pivot).firstName + arr.get(pivot).patronymic;
        String iName = arr.get(i).lastName + arr.get(i).firstName + arr.get(i).patronymic;
        String jName = arr.get(j).lastName + arr.get(j).firstName + arr.get(j).patronymic;

        while (i <= j)
        {
            while (iName.compareToIgnoreCase(pivotName) > 0) {
                i++;
                iName = arr.get(i).lastName + arr.get(i).firstName + arr.get(i).patronymic;
            }
            while (jName.compareToIgnoreCase(pivotName) < 0) {
                j--;
                jName = arr.get(j).lastName + arr.get(j).firstName + arr.get(j).patronymic;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За прізвищем по зростанню
    private void sortQuickLastName(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;

        while (i <= j)
        {
            while (arr.get(i).lastName.compareToIgnoreCase(arr.get(pivot).lastName) < 0) {
                i++;
            }
            while (arr.get(j).lastName.compareToIgnoreCase(arr.get(pivot).lastName) > 0) {
                j--;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За прізвищем по спаданню
    private void sortQuickLastNameReversed(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;

        while (i <= j)
        {
            while (arr.get(i).lastName.compareToIgnoreCase(arr.get(pivot).lastName) > 0) {
                i++;
            }
            while (arr.get(j).lastName.compareToIgnoreCase(arr.get(pivot).lastName) < 0) {
                j--;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За іменем по зростанню
    private void sortQuickFirstName(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;

        while (i <= j)
        {
            while (arr.get(i).firstName.compareToIgnoreCase(arr.get(pivot).firstName) < 0) {
                i++;
            }
            while (arr.get(j).firstName.compareToIgnoreCase(arr.get(pivot).firstName) > 0) {
                j--;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }

    //За іменем по спаданню
    private void sortQuickFirstNameReversed(List<Participant> arr, int firstIndex, int lastIndex)
    {
        int i = firstIndex, j = lastIndex;
        int pivot = (arr.size() - 1) / 2;

        while (i <= j)
        {
            while (arr.get(i).firstName.compareToIgnoreCase(arr.get(pivot).firstName) > 0) {
                i++;
            }
            while (arr.get(j).firstName.compareToIgnoreCase(arr.get(pivot).firstName) < 0) {
                j--;
            }
            if (i <= j)
            {
                Participant temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (j > firstIndex) {
            sortQuickFirstName(arr, firstIndex, j);
        }
        if (i < lastIndex) {
            sortQuickFirstName(arr, i, lastIndex);
        }
    }
}
