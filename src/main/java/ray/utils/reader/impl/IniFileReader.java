package ray.utils.reader.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ray.utils.reader.FileReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
* @ClassName : IniFileReader
* @Author : poWERmaXRay
* @Date: 2022/11/17 21:16
*/
public class IniFileReader<OutputType> extends FileReader<OutputType> {

    private static final ApplicationContext IOC = new ClassPathXmlApplicationContext("applicationContext");

    private static final List<Object> PROPERTY_LIST = new ArrayList<>();

    @Override
    public OutputType readFile() {
//        OutputType resultObject = (OutputType) IOC.getBean();
        return null;
    }

    @Override
    protected Field[] getBeanProperties(Object object) {
        Class<?> objectClass = object.getClass();
        return objectClass.getFields();
    }

    @Override
    protected OutputType setProperties(Field[] fields, OutputType aimInject, String readline) {
        return null;
    }
}
