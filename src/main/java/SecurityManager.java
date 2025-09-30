
public class SecurityManager {

    private String regex;

    // package private constructor
    SecurityManager(String regex){
        this.regex = regex;
    }

    // 0 = successful validate
    // 1 = regex error
    // 2 = does not match existing entry
    public static int validate(String username, String password){

        return -1;
    }

}
