package quoters;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TerminatorQuoterTest {
    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

    @Test
    public void testConfig() throws InterruptedException {
        while (true) {
            Thread.sleep(500);
            context.getBean(Quoter.class).sayQuoter();
        }
    }
}