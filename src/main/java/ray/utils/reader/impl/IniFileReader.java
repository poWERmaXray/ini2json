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
public class IniFileReader<OutputType> extends FileReader<OutputType> {

    private static final ApplicationContext IOC = new ClassPathXmlApplicationContext("applicationContext");

    private static final List<Object> PROPERTY_LIST = new ArrayList<>();

    @Override
    public OutputType readFile(Path path) {
        OutputType resultObject = (OutputType) IOC.getBean(getGenericsClass());
        Field[] fields = getBeanProperties(resultObject);
        try {
            // 读取文件
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            // 将行信息写入到pojo中
            for (String line : lines) {
                // 通过方法注入
                setProperties(fields,resultObject,line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected Field[] getBeanProperties(Object object) {
        Class<?> objectClass = object.getClass();
        return objectClass.getFields();
    }

    /**
     * 进行属性注入的方法
     * 根据成员属性，在行内容readline中查找字段进行匹配，并注入到目标对象中
     * @param fields 注入对象的成员属性信息
     * @param aimInject 注入信息的目标对象
     * @param readline 当前行信息
     * @return
     */
    @Override
    protected OutputType setProperties(Field[] fields, OutputType aimInject, String readline) {

        return null;
    }

    private Class<OutputType> getGenericsClass(){
        return (Class<OutputType>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
