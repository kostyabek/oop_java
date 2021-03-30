package source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class ParticipantTest {
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
        pp.addParticipant(participant);

        inputStreamChanger = new InputStreamChanger();
    }

    @Test
    void voteRegional() {
        assertTrue(participant.voteRegional(null).getId().equals(""));

        inputStreamChanger.restoreDefaultInputStream();
        inputStreamChanger.changeInputStreamData("MSA2koop");
        Participant votedFor = participant.voteRegional(Calendar.getInstance());
        assertTrue(votedFor.getId().equals("MSA2koop"));
    }

    @Test
    void voteGlobal() {
        assertTrue(participant.voteRegional(null).getId().equals(""));

        inputStreamChanger.restoreDefaultInputStream();
        inputStreamChanger.changeInputStreamData("msa1koop");
        Participant votedFor = participant.voteGlobal(Calendar.getInstance());
        assertTrue(votedFor.getId().equals("msa1koop"));
    }

    @ParameterizedTest
    @CsvSource(value = {"0;-1", "0;0", "5.23;5.23"}, delimiter= ';')
    void earnMoney(double expectedIncome, double money) {
        assertEquals(expectedIncome, participant.earnMoney(money));
    }



     /*
    @TestFactory
    Stream<DynamicTest> earnMoney() {
        double[][] values = {{-1, 0}, {0, 0}, {5.23, 5.23}};
        return Arrays.stream(values).map(entry -> {
            double money = entry[0];
            double expectedIncome = entry[1];
            return dynamicTest(money + "->" + expectedIncome, () -> assertEquals(expectedIncome, participant.earnMoney(money)));
        });
    }
     */
}