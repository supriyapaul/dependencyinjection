package dependencyinjection;

public class Component2 extends Component{

    @Autowired
    private Dependency1 dependency;

    public Dependency1 getDependency() {
        return dependency;
    }
}
