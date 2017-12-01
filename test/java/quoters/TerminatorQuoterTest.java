package quoters;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TerminatorQuoterTest {
    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

    @Test
    public void testConfig() throws InterruptedException {
        context.getBean(Quoter.class).sayQuoter();
//        while (true) {
//            Thread.sleep(500);
//            context.getBean(Quoter.class).sayQuoter();
//        }
    }
}