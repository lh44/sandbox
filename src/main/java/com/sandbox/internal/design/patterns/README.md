## Design Patterns
* Design patterns can speed up the development process by providing tested, proven development paradigms
* Reusing design patterns helps to prevent subtle issues that can cause major problems and improves code readability and
 maintainability for coders and architects familiar with the patterns.
* Design patterns provide generic solutions, documented in a format that does not require specifics tied to a particular problem.
* In addition, patterns allow developers to communicate using well-known, well understood names for software interactions. 
Common design patterns can be improved over time, making them more robust than ad-hoc designs.

## How to learn
* Choose patterns you might've already worked on
* Singleton, Factory, Abstract factory, Prototype, Builder
* Adapter, Decorator, Facade, Proxy, Bridge
* Chain of responsibility, Command, Observer, let's see others
* Learn the basics of a pattern
* Understand how it was applied with the projects you've worked on

## Points
* Service locator is the opposite of IoC

## Patterns
### Creational Design Pattern
-[x] Factory Pattern
```java
import java.util.*;
import java.util.Set;

//In Factory pattern, we create object without exposing the creation logic
// to the client and refer to newly created object using a common interface.

class CloudConnectorFactory {
    
    private final Map<Type, CloudConnector> connectors = new HashMap<>();

    public void addConnector(Type type, CloudConnector connector) {
        connectors.put(type, connector);
    }

    public CloudConnector getConnector(Type type) {
        return connectors.get(type);
    }
}

interface CloudConnector {
    Type getType();
    Object launchMachine(Object configuration);
}

enum Type {
    AWS, GCP, OPENSTACK;
}

//@Component
//Process this annotation and 
@interface Connector {
    Type type();
}

@Connector(type = Type.AWS)
static class AWSConnector implements CloudConnector {
   
    @Override
    public Object launchMachine(Object configuration) {
        return null;
    }
}
```
-[ ] Abstract Factory Pattern
-[x] Singleton Pattern
```java
public class Singleton {
    private static Singleton singleton;
    private Singleton() {}
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```
-[x] Prototype Pattern
    * Avoid object creation overhead by cloning an existing object 
-[x] Builder Pattern
```java
public class Instance {
    private final int count;
    private final int memoryInMB;
    private final int vcpus;

    public Instance(int count, int memoryInMB, int vcpus) {
        this.count = count;
        this.memoryInMB = memoryInMB;
        this.vcpus = vcpus;
    }
}

public class InstanceBuilder {
    private int count;
    private int memoryInMB;
    private int vcpus;
    
    public InstanceBuilder count(int count) {
        this.count = count;
    }
    
    public InstanceBuilder memoryInMB(int memoryInMB) {
        this.memoryInMB = memoryInMB;
    }
    
    public InstanceBuilder vcpus(int vcpus) {
        this.vcpus = vcpus;
    }
    
    public Instance build() {
        return new Instance(count, memoryInMB, vcpus);    
    }
}
```
### Structural Design Pattern
-[ ] Adapter Pattern
-[ ] Bridge Pattern
-[ ] Composite Pattern
-[ ] Decorator Pattern
-[x] Facade Pattern
    
    Abstracts complex subsystems, facade knows how to delegate to the right subsystems
    It's applied at the microservice level
-[ ] Flyweight Pattern
-[ ] Proxy Pattern
### Behavioral Design Pattern
-[ ] Chain Of Responsibility Pattern
-[ ] Command Pattern
-[ ] Interpreter Pattern
-[ ] Iterator Pattern
-[ ] Mediator Pattern
-[ ] Memento Pattern
-[ ] Observer Pattern
-[ ] State Pattern
-[ ] Strategy Pattern
-[ ] Template Pattern
-[ ] Visitor Pattern