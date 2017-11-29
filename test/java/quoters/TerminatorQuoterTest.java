package quoters;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TerminatorQuoterTest {

    @Test
    public void sayQuoterTest() {
        ClassPathXmlApplicationContext contex = new ClassPathXmlApplicationContext("context.xml");
        contex.getBean(TerminatorQuoter.class).sayQuoter();

    }
}