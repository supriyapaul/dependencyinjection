package dependencyinjection;

public class Component1 extends Component {

    @Autowired
    private Dependency1 dependency;

    public Dependency1 getDependency() {
        return dependency;
    }
}
