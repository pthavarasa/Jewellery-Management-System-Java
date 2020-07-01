package login;

public enum Option {
    Admin , Worker;

    private Option(){}
    public String value(){
        return name();
    }

    public static Option fromvalue(String v){
        return valueOf(v);
    }
}
