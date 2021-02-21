package source;

/*Створіть наступну модель: є політична сила, яка може приймати до своїх лав громадян країни. Політична сила може
  проводити з’їзди своїх членів та обирати свої керівні органи.
  Повинна бути можливість подивитися, за кого голосував кожний учасник,
  або хто голосував за конкретну людину в керуючому органі.
  Кожен активіст може пошукати детальну інформацію по різним критеріям серед всього складу однопартійців,
  крім того є можливість декларувати помісячно свої доходи та дивитися доходи однопартійців за любий період.
  Повинна бути можливість формувати списки учасників з’їздів, це роблять регіональні партійні структури.
  Таким чином регіональний орган обирає з своїх лав учасників з’їзді.
  Повинна бути можливість формування загальних списків учасників з’їздів з можливістю сортування за регіонами,
  доходами, та по алфавіту.*/

public class Main {
    public static void main(String[] args) {
        PolitPower pp = new PolitPower("PATRIOT");
        pp.addParticipant(new Participant("Konstantin", "Biektin", "Oleksandrovych", Regions.MYKOLAIV, "pbc13d92"));
        pp.addParticipant(new Participant("Dmitri", "Mariash", "Ihorovych", Regions.MYKOLAIV, "sfpb1347"));
        pp.addParticipant(new Participant("Ivan", "Kolomiy", "Hryhorievych", Regions.ODESA, "ddts4p90"));
        pp.addParticipant(new Participant("Vasyl'", "Kolomiy", "Stepanovych", Regions.ODESA, "ddps4p74"));
        pp.addParticipant(new Participant("Mykola", "Drozd", "Valentynovych", Regions.KYIV, "msa2koop"));
        pp.addParticipant(new Participant("Vitaliy", "Drozd", "Mykhailovych", Regions.KYIV, "msa1koop"));
        pp.addParticipant(new Participant("Vitaliy", "Banka", "Oleksandrovych", Regions.KYIV, "msa3koop"));
        pp.addParticipant(new Participant("Vladislav", "Krona", "Ihorovych", Regions.KHARKIV, "13372ds8"));
        pp.addParticipant(new Participant("Evgeniy", "Zhuk", "Petrovych", Regions.KHARKIV, "pptkjsft"));
        pp.addParticipant(new Participant("Vladislav", "Krona", "Symonovych", Regions.CHERNIHIV, "zxcvbnml"));
    }
}
