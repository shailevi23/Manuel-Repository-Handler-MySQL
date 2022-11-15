package org.example.Utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<Object,String> map = new HashMap<>();

    public static Map<Object, String> mapInit(){
        map.put(Integer.class, "Integer");
        map.put(int.class, "int");
        map.put(long.class, "long");
        map.put(Long.class, "Long");
        map.put(double.class, "double");
        map.put(Double.class, "Double");
        map.put(boolean.class, "boolean");
        map.put(Boolean.class, "Boolean");
        return map;
    }

}
