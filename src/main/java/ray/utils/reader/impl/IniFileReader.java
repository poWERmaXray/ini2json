package ray.utils.reader.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ray.utils.pojo.CollectorContext;
import ray.utils.pojo.Pair;
import ray.utils.reader.FileReader;
import ray.utils.utils.ValueDealer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
* @ClassName : IniFileReader
* @Author : poWERmaXRay
* @Date: 2022/11/17 21:16
*/
public class IniFileReader implements FileReader {

    private static final ApplicationContext IOC = new ClassPathXmlApplicationContext("applicationContext");

    private static final List<Object> PROPERTY_LIST = new ArrayList<>();

    /**
     * 读取ini的配置信息，进行属性注入
     * @param path ini文件的路径
     * @param genericObject 需要被返回的空实例
     * @return ini文件中的配置，完全注入后的实例化
     */
    @Override
    public Object getInstance(Path path, Object genericObject) {
        CollectorContext context = IOC.getBean(CollectorContext.class);
        context.setInjectInstance(IOC.getBean(genericObject.getClass()));
        context.setLines(readFile(path));
        context.setParams(getInstanceFields(context));
        // 对每一行的信息进行找值和注入
        context.getLines().forEach(line-> {
            context.setCurrentLine(line);
            subLine(context);
        });
        return context.getInjectInstance();
    }

    private List<String> readFile(Path path) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e){
            System.out.println("ini path incorrect");
        }
        return lines;
    }

    /**
     * 对于非引用属性，截取字符串取出属性并进行封装
     * @param currentLine 当前行的字符串
     * @param message 存储属性信息的键值
     * @param param 属性参数
     * @param injectAim 注入属性的目标对象
     */
    private  void rebuildStringAndInjectProperty(String currentLine, Pair message, Field param, Object injectAim){
        String paramName = param.getName();
        int length = paramName.length();

        if (!currentLine.contains(paramName)){
            return;
        }
        message.setKey(paramName);
        String value;
        // key的起始位置
        int paramBeginIndex = currentLine.indexOf(paramName);
        // 左引号下标
        int markLeft = currentLine.substring(paramBeginIndex).indexOf('"') + paramBeginIndex;
        // 右引号下标
        int markRight = currentLine.substring(markLeft + 1).indexOf('"') + markLeft + 1;
        // 第一个引号后的逗号
        int commaMark = currentLine.substring(paramBeginIndex).indexOf(',');
        commaMark = commaMark == currentLine.length() ? commaMark : commaMark + paramBeginIndex;
        // 当逗号在引号之间
        if (commaMark > markLeft && commaMark < markRight){
            value = currentLine.substring(paramBeginIndex + length + 1, markRight + 1);
        }else {
            value = currentLine.substring(paramBeginIndex + length + 1, commaMark);
        }
        message.setValue(ValueDealer.castValue(value));
        try {
            param.setAccessible(true);
            param.set(injectAim, message.getValue());
        } catch (IllegalAccessException ignore){}
        message.clean();
    }

    private void subLine(CollectorContext context){
        Pair message;
        String currentLine;
        // 对于每一行字符串，对所有属性field遍历
        for (Field param : context.getParams()) {
            currentLine = context.getCurrentLine();
            message = context.getMessage();

            Field[] subFields = param.getClass().getFields();
            // 判断该param是否为引用属性，其中是否又包含了属性
            boolean isAttribute = currentLine.substring(currentLine.indexOf(param.getName() + 1)).startsWith("(");
            // 是基本数据类型或其包装类，内部无嵌套属性时
            if (subFields.length < 1 || !isAttribute){
                rebuildStringAndInjectProperty(currentLine, message, param, context.getInjectInstance());
                continue;
            }

            try {
                Field instance = param.getClass().newInstance();
                int leftIndex = currentLine.indexOf('(') + 1;
                int rightIndex = currentLine.indexOf(')');
                for (Field subField : subFields){
                    // 去掉前后括号后，调用非引用属性注入的方法
                    String attributeString = currentLine.substring(leftIndex, rightIndex);
                    rebuildStringAndInjectProperty(attributeString, message, subField, instance);
                    param.setAccessible(true);
                }
                // 不同于rebuild方法中的set，这里的set是将新的对象注入到要返回的对象中
                param.set(context.getInjectInstance(), instance);
            } catch (Exception ignored) {}
        }
    }

    private Field[] getInstanceFields(CollectorContext context){
        return context.getInjectInstance().getClass().getFields();
    }
}
