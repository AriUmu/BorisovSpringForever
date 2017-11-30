package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHeandlerBeanPostProcessor implements BeanPostProcessor {


    private ProfilingController controller = new ProfilingController();

    private Map<String, Class> map = new HashMap<String, Class>();
    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if(aClass.isAnnotationPresent(Profiling.class)){
            map.put(beanName, aClass);
        }
        return bean;
    }

    @Nullable
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {

        Class aClass = map.get(beanName);
        if(aClass != null){
            return Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                   if(controller.getEnabled()) {
                       long before = System.nanoTime();
                       System.out.println("Profiling");
                       Object invoke = method.invoke(bean, args);
                       long after = System.nanoTime();
                       System.out.println(after - before);
                       System.out.println("End");
                       return invoke;
                   } else {
                       return  method.invoke(bean, args);
                   }
                }
            });
        }
        return bean;
    }
}
