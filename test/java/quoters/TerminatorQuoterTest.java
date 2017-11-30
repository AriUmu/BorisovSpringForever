package quoters;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TerminatorQuoterTest {

    @Test
    public void sayQuoterTest() {
        ClassPathXmlApplicationContext contex = new ClassPathXmlApplicationContext("context.xml");

       while (true) {
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           contex.getBean(Quoter.class).sayQuoter();
       }

    }
}