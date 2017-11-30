package quoters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String originakClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> originalClass = Class.forName(originakClassName);
                Method[] methods = originalClass.getMethods();
                for (Method method : methods){
                    if(method.isAnnotationPresent(PostProxy.class)){
                        Object bean = context.getBean(name);
                        try {
                            Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                            try {
                                currentMethod.invoke(bean);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
