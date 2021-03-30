package source;

import java.util.*;
import java.util.stream.Stream;

public class PoliticalPower {
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

    public PoliticalPower(String name) {
        this.name = name;
    }

    //Додавання нового активіста
    public Boolean addParticipant(Participant person) {
        if (!validatePersonDataBeforeAdding(person)) {
            return false;
        }

        participantDataToUpperCase(person);

        if (regionalStructs.get(person.region) == null) {
            regionalStructs.put(person.region, new RegionalStruct(person.region));
        }

        regionalStructs.get(person.region).participants.add(person);
        person.politicalPower = this;
        person.regionalStruct = regionalStructs.get(person.region);
        return true;
    }

    public Boolean validatePersonDataBeforeAdding(Participant person) {
        if (person == null)
            return false;
        else if (person.getId().length() != 8)
            return false;
        else if (person.firstName.length()<3 || person.lastName.length()<3 || person.patronymic.length()<6)
            return false;
        else {
            for (Character sym: person.firstName.toCharArray()) {
                if (Character.isDigit(sym))
                    return false;
            }
            for (Character sym: person.lastName.toCharArray()) {
                if (Character.isDigit(sym))
                    return false;
            }
            for (Character sym: person.patronymic.toCharArray()) {
                if (Character.isDigit(sym))
                    return false;
            }
        }

        return true;
    }

    //Отримання інформації про конкретного активіста
    public Participant getParticipant(String id) {
        for (RegionalStruct regionalStruct : regionalStructs.values()) {
            for (Participant participant : regionalStruct.participants) {
                if (id.equals(participant.getId())) {
                    return participant;
                }
            }
        }
        return new Participant("", "", "", Regions.KYIV, "");
    }

    public void participantDataToUpperCase(Participant person) {
        person.firstName = person.firstName.toUpperCase();
        person.lastName = person.lastName.toUpperCase();
        person.patronymic = person.patronymic.toUpperCase();
    }

    public List<Participant> getParticipantsByCriteria(String criteria) {
        List<Participant> result = new ArrayList<>();
        String criteriaUpperCased = criteria.toUpperCase();

        for (Regions region: regionalStructs.keySet()) {
            for (Participant participant: regionalStructs.get(region).participants) {
                if (participant.firstName.contains(criteriaUpperCased) ||
                participant.lastName.contains(criteriaUpperCased) ||
                participant.patronymic.contains(criteriaUpperCased) ||
                participant.getId().contains(criteria) ||
                participant.region.name().contains(criteriaUpperCased)) {
                    result.add(participant);
                }
            }
        }
        if (result.isEmpty())
            return new ArrayList<>();
        return result;
    }

    //Проведення з'їзду та формування загального списку тих, кто був
    public List<Participant> conductCongress() {
        Calendar congressDate = Calendar.getInstance();
        List<Participant> congressAttendeesGlobal = new ArrayList<>();

        for (Regions region: regionalStructs.keySet()) {
            congressAttendeesGlobal.addAll(regionalStructs.get(region).formCongressList());
        }
        congresses.put(congressDate, congressAttendeesGlobal);

        return congressAttendeesGlobal;
    }

    //Проведення виборів головного керуючого органу
    public Participant conductGlobalElection(String position) {
        Calendar date = Calendar.getInstance();
        Scanner sc = new Scanner(System.in);
        Participant winner = null;
        int votes = 0;

        while (!position.matches("(leader)|(secretary)|(treasurer)|(presiding)")) {
            position = sc.nextLine();
        }
        for (Regions region: regionalStructs.keySet()) {
            for (Participant participant: regionalStructs.get(region).participants) {
                participant.voteGlobal(date);
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

        return winner;
    }

    //Проведення виборів регіональних керуючих органів
    public Participant conductRegionalElection() {
        Calendar date = Calendar.getInstance();
        Scanner sc = new Scanner(System.in);
        Participant winner = null;
        int votes = 0;

        for (Regions region: regionalStructs.keySet()) {
            for (Participant participant: regionalStructs.get(region).participants) {
                participant.voteRegional(date);
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

        return winner;
    }

    //Сортування списків активістів, що були на з'їздах
    public Map<Calendar, List<Participant>> sortCongressAttendees() {
        Scanner sc = new Scanner(System.in);
        String field;
        String order;

        do {
            field = sc.nextLine();
            order = sc.nextLine();
        } while (!field.matches("(firstname)|(lastname)|(fullname)|(region)|(income)") || !order.matches("[a]|[d]"));

        Set<Map.Entry<Calendar, List<Participant>>> participantLists = congresses.entrySet();
        Iterator<Map.Entry<Calendar, List<Participant>>> iter = participantLists.iterator();

        if (order.equals("a")) {
            switch (field) {
                case "firstname":
                    while (iter.hasNext()) {
                        sortQuickFirstName(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
                case "lastname":
                    while (iter.hasNext()) {
                        sortQuickLastName(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
                case "fullname":
                    while (iter.hasNext()) {
                        sortQuickName(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
                case "region":
                    while (iter.hasNext()) {
                        sortQuickRegion(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
                case "income":
                    while (iter.hasNext()) {
                        sortQuickIncome(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
            }
        }
        else if (order.equals("b")) {
            switch (field) {
                case "firstname":
                    while (iter.hasNext()) {
                        sortQuickFirstNameReversed(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
                case "lastname":
                    while (iter.hasNext()) {
                        sortQuickLastNameReversed(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
                case "fullname":
                    while (iter.hasNext()) {
                        sortQuickNameReversed(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
                case "region":
                    while (iter.hasNext()) {
                        sortQuickRegionReversed(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
                case "income":
                    while (iter.hasNext()) {
                        sortQuickIncomeReversed(iter.next().getValue(), 0, iter.next().getValue().size() - 1);
                    }
                    break;
            }
        }

        return congresses;
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
