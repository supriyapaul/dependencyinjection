package dependencyinjection;

import javax.naming.NamingException;

public class MainClass {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NamingException {

        DependencyInjectionContext dependencyInjectionContext = new DependencyInjectionContext();
        Component1 component1 = (Component1) dependencyInjectionContext.getBean("Component1");
        Dependency1 dependency1 = component1.getDependency();
        dependency1.setValue("Value is set");
        Component2 component2 = (Component2) dependencyInjectionContext.getBean("Component2");
        System.out.println("--------- dependency value----"+component2.getDependency().getValue());

        if(component1.getDependency().getValue().equals(component2.getDependency().getValue())){
            System.out.println("----------- Singleton instance has been injected");
        }
    }
}
