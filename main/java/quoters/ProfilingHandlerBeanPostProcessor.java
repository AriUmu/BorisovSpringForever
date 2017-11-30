package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private ProfilingController controller = new ProfilingController();
    private Map<String, Class> map = new HashMap<>();

    public ProfilingHandlerBeanPostProcessor() {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            platformMBeanServer.registerMBean(controller,new ObjectName("profiling", "name", "controller"));
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if(aClass.isAnnotationPresent(Profiling.class)){
            map.put(beanName, aClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isEnabled()) {
                        System.out.println("Profiling...");
                        long before = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        long after = System.nanoTime();
                        System.out.println(after - before);
                        System.out.println("That's all!");
                        return retVal;
                    } else {
                        return method.invoke(bean,args);
                    }
                }
            });
        }
        return bean;
    }
}
