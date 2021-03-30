package source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class RegionalStructTest {
    PoliticalPower pp;
    Participant participant;

    InputStreamChanger inputStreamChanger;

    @BeforeEach
    void setup () {
        pp = new PoliticalPower("PATRIOT");
        pp.addParticipant(new Participant("Konstantin", "Biektin", "Oleksandrovych", Regions.MYKOLAIV, "Pbc13D92"));
        pp.addParticipant(new Participant("Dmitri", "Mariash", "Ihorovych", Regions.MYKOLAIV, "sFpb1347"));
        pp.addParticipant(new Participant("Ivan", "Kolomiy", "Hryhorievych", Regions.ODESA, "dDTs4p90"));
        pp.addParticipant(new Participant("Vasyl'", "Kolomiy", "Stepanovych", Regions.ODESA, "ddps4P74"));
        pp.addParticipant(new Participant("Mykola", "Drozd", "Valentynovych", Regions.KYIV, "MSA2koop"));
        pp.addParticipant(new Participant("Vitaliy", "Drozd", "Mykhailovych", Regions.KYIV, "msa1koop"));
        pp.addParticipant(new Participant("Vitaliy", "Banka", "Oleksandrovych", Regions.KYIV, "msa3koop"));
        pp.addParticipant(new Participant("Vladislav", "Krona", "Ihorovych", Regions.KHARKIV, "13372ds8"));
        pp.addParticipant(new Participant("Evgeniy", "Zhuk", "Petrovych", Regions.KHARKIV, "pptkjsft"));
        pp.addParticipant(new Participant("Vladislav", "Krona", "Symonovych", Regions.CHERNIHIV, "zxcvbnml"));

        participant = new Participant("Vladyslav", "Murk", "Dmytrovych", Regions.KYIV, "bcd13f17");

        inputStreamChanger = new InputStreamChanger();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings={"kkk18", "11111111"})
    void getParticipantWithInvalidValues(String id) {
        assertEquals("", pp.regionalStructs.get(Regions.KYIV).getParticipant(id).getId());
    }

    @ParameterizedTest
    @ValueSource(strings={"MSA2koop", "msa1koop", "msa3koop"})
    void getParticipantWithValidValues(String id) {
        assertEquals(id, pp.regionalStructs.get(Regions.KYIV).getParticipant(id).getId());
    }

    @Test
    void getParticipantIncomes() {
        RegionalStruct kyiv = pp.regionalStructs.get(Regions.KYIV);
        Participant participant = kyiv.getParticipant("MSA2koop");
        assertNull(kyiv.getParticipantIncomes(participant, new Date(10000), new Date(20000)));

        participant.earnMoney(100000);
        participant.declareMonthlyIncome();
        Map<Calendar, Double> incomes = kyiv.getParticipantIncomes(participant, new Date(10000), new Date());
        assertNotNull(incomes);
        assertTrue(incomes.size()==1 && incomes.containsValue(100000.0));
    }

    @Test
    void formCongressList() {
        // TODO: 3/18/2021 Needs Fixing reading user input
        inputStreamChanger.restoreDefaultInputStream();
        inputStreamChanger.changeInputStreamData("2" + System.getProperty("line.separator") +
                "MSA2koop" + System.getProperty("line.separator") +
                "msa3koop" + System.getProperty("line.separator"));

        RegionalStruct kyiv = pp.regionalStructs.get(Regions.KYIV);
        List congressList = kyiv.formCongressList();
        System.out.println(congressList.get(0));
        System.out.println(congressList.get(1));
        assertTrue(congressList.size()==2);
    }
}