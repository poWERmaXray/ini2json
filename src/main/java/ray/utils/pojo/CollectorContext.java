package ray.utils.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
* @ClassName : Collector
* @Description : 描述抓取行信息的pojo类，存放当前行的各种信息
* @Author : poWERmaXRay
* @Date: 2022/12/5 21:09
*/
@Component
public class CollectorContext {

    private Field param;
    private List<String> lines;
    @Autowired
    private Pair message;
    private String currentLine;

    @Override
    public String toString() {
        return "CollectorContext{" +
                "param=" + param +
                ", lines=" + lines +
                ", message=" + message +
                ", currentLine='" + currentLine + '\'' +
                '}';
    }

    public Field getParam() {
        return param;
    }

    public void setParam(Field param) {
        this.param = param;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public Pair getMessage() {
        return message;
    }

    public void setMessage(Pair message) {
        this.message = message;
    }

    public String getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(String currentLine) {
        this.currentLine = currentLine;
    }
}
