package dependencyinjection;

import org.reflections.Reflections;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DependencyInjectionContext {

    public static final String DEPENDENCYINJECTION = "dependencyinjection";
    private Context context;

    public DependencyInjectionContext() throws NamingException, InstantiationException, IllegalAccessException {
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.activate();
        JndiTemplate jndiTemplate = new JndiTemplate();
        context = (InitialContext) jndiTemplate.getContext();
        loadContexts(context);
    }

    private void loadContexts(final Context context) throws IllegalAccessException, InstantiationException, NamingException {
        Reflections reflections = new Reflections(DEPENDENCYINJECTION);
        Map<String,Object> dependencyMap = new HashMap<String, Object>();

        Set<Class<? extends Component>> allClasses =
                reflections.getSubTypesOf(Component.class);
        System.out.println(" All the component classes "+allClasses);

        for(Class aClass : allClasses){
            Field[] fields =  aClass.getDeclaredFields();
            for (Field field : fields){
                if(field.getAnnotation(Autowired.class) != null){
                    if(!dependencyMap.containsKey(field.getName()))
                        dependencyMap.put(field.getName(),field.getType().newInstance());
                    dependencyMap.put(aClass.getName(),aClass.newInstance());
                    field.setAccessible(true);
                    field.set(dependencyMap.get(aClass.getName()),dependencyMap.get(field.getName()));
                }
            }
        }

        System.out.println(" Creting context for all Singleton objects"+allClasses);
        for(String key : dependencyMap.keySet()){
            context.bind(key,dependencyMap.get(key));
        }
    }

    public Object getBean(final String beanName) throws NamingException {
        return context.lookup(DEPENDENCYINJECTION+"."+beanName);
    }
}
