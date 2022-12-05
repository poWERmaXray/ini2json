package ray.utils.reader.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ray.utils.reader.FileReader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
* @ClassName : IniFileReader
* @Author : poWERmaXRay
* @Date: 2022/11/17 21:16
*/
public class IniFileReader<OutputType> implements FileReader<OutputType> {

    private static final ApplicationContext IOC = new ClassPathXmlApplicationContext("applicationContext");

    private static final List<Object> PROPERTY_LIST = new ArrayList<>();

    @Override
    public OutputType readFile(Path path) {
        OutputType resultObject = (OutputType) IOC.getBean(getGenericsClass());
        
        return null;
    }

    private Class<OutputType> getGenericsClass(){
        return (Class<OutputType>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
