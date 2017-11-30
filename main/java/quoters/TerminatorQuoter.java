package quoters;

import javax.annotation.PostConstruct;

public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 2, max = 7)
    private int repeat;
    private String message;

    @PostConstruct
    public void init(){
        System.out.println("Phase 2");
        System.out.println(repeat);
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public TerminatorQuoter() {
        System.out.println("Phase 1");
    }

    public void sayQuoter() {

        for (int i = 0; i < repeat; i++) {
            System.out.println("message =" + message);
        }
    }
}
