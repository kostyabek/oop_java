package source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class PoliticalPowerTest {
    PoliticalPower pp;
    Participant participant;

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
    }

    @Test
    void addParticipant() {
        assertFalse(pp.addParticipant(null));

        assertFalse(pp.addParticipant(new Participant("aa", "Biektin", "Oleksandrovych", Regions.MYKOLAIV, "pbc13d92")));
        assertFalse(pp.addParticipant(new Participant("Konstantin", "bb", "Oleksandrovych", Regions.MYKOLAIV, "pbc13d92")));
        assertFalse(pp.addParticipant(new Participant("Konstantin", "Biektin", "cc", Regions.MYKOLAIV, "pbc13d92")));

        assertFalse(pp.addParticipant(new Participant("Konstantin", "Biektin", "Oleksandrovych", Regions.MYKOLAIV, "pbc13d")));

        assertFalse(pp.addParticipant(new Participant("Konstantin1", "Biektin", "Oleksandrovych", Regions.MYKOLAIV, "pbc13d92")));
        assertFalse(pp.addParticipant(new Participant("Konstantin", "Biektin1", "Oleksandrovych", Regions.MYKOLAIV, "pbc13d92")));
        assertFalse(pp.addParticipant(new Participant("Konstantin", "Biektin", "Oleksandrovych1", Regions.MYKOLAIV, "pbc13d92")));

        assertTrue(pp.addParticipant(new Participant("Konstantin", "Biektin", "Oleksandrovych", Regions.MYKOLAIV, "pbc13d92")));
    }

    @Test
    void getParticipant() {
        Participant participant = pp.getParticipant(null);
        assertTrue(participant.firstName.length()==0);

        participant = pp.getParticipant("");
        assertTrue(participant.firstName.length()==0);

        participant = pp.getParticipant("1123");
        assertTrue(participant.firstName.length()==0);

        participant = pp.getParticipant("123456789");
        assertTrue(participant.firstName.length()==0);

        participant = pp.getParticipant("ddts4p90");
        assertEquals("HRYHORIEVYCH", participant.patronymic);
    }

    @Test
    void getParticipantsByCriteria() {
        assertEquals(1, pp.getParticipantsByCriteria("konstantin").size());
        assertEquals(1, pp.getParticipantsByCriteria("BIEKTIN").size());
        assertEquals(2, pp.getParticipantsByCriteria("oleKsanDrovych").size());
        assertEquals(0, pp.getParticipantsByCriteria("pbc13d92").size());
        assertEquals(1, pp.getParticipantsByCriteria("Pbc13D92").size());
        assertEquals(2, pp.getParticipantsByCriteria("odeSa").size());
    }

    @Test
    void conductCongress() {

    }

    @Test
    void conductGlobalElection() {

    }

    @Test
    void conductRegionalElection() {

    }
}