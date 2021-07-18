package com.sandbox.designpatterns;

import com.sandbox.datastructures.v2.Map;
import com.sandbox.datastructures.v2.impl.Hashmap;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import sun.reflect.misc.ReflectUtil;

public class Factory {
    //In Factory pattern, we create object without exposing the creation logic
    // to the client and refer to newly created object using a common interface.

    static Map<Type, CloudConnector> connectors = new Hashmap<>();

    public static void register(Type type, Class<?> connector) throws Exception {
        connectors.put(type, (CloudConnector) ReflectUtil.newInstance(connector));
    }

    public static CloudConnector getConnector(Type type) {
        return connectors.get(type);
    }

    enum Type {
        AWS, GCP, DEFAULT
    }

    @XmlElement
    interface CloudConnector {
        void startConnection();

        Object launchMachine(Object configuration);
    }

    static class AWSConnector implements CloudConnector {

        static {
            try {
                Factory.register(Type.AWS, AWSConnector.class);
            } catch (Exception e) {}
        }

        @Override
        public void startConnection() {
            //aws logic
        }

        @Override
        public Object launchMachine(Object configuration) {
            return null;
        }
    }

}
