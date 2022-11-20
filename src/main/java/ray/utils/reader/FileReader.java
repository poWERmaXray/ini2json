package ray.utils.reader;

import java.lang.reflect.Field;
import java.nio.file.Path;

/**
* @ClassName : FileReader
* @Description : 读取要改变的源文件并返回为一个对象
* @Author : poWERmaXRay
* @Date: 2022/11/17 21:05
*/
public abstract class FileReader<OutputType> {
    public abstract OutputType readFile(Path path);

    protected abstract Field[] getBeanProperties(Object object);

    protected abstract OutputType setProperties(Field[] fields, OutputType aimInject, String readline);
}
