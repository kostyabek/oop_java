package access;

public class Access {
    String defVar = "no modifier";
    public String pubVar = "public modifier";
    protected String proVar = "protected modifier";
    private String privVar = "private modifier";
}

class InternalAccess {
    public void printVars() {
        Access acs = new Access();
        System.out.println(acs.defVar);
        System.out.println(acs.pubVar);
        System.out.println(acs.proVar);
    }
}

class InternalAccessDerived extends Access {
    public void printVars() {
        System.out.println(defVar);
        System.out.println(pubVar);
        System.out.println(proVar);
    }
}