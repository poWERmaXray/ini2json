package ray.utils.pojo;

import org.springframework.stereotype.Component;

/**
* @ClassName : Pair
* @Description : 存放读取的键值对信息，可以返回key=value的字符串
* @Author : poWERmaXRay
* @Date: 2022/12/5 21:14
*/
@Component
public class Pair {
    private String key;
    private Object value;

    public Pair(){
    }

    public Pair(String key, Object value){
        setKey(key);
        setValue(value);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getKeyAndValue(){
        return key + value;
    }

    public void clean() {
        setKey(null);
        setValue(null);
    }
}
