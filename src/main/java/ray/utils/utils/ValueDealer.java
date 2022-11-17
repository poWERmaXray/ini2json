package ray.utils.utils;

/**
* @ClassName : ValueDealer
* @Description : 工具类，传入一个读取到的字符，判断类型并返回正确的类型的数据
* @Author : poWERmaXRay
* @Date: 2022/11/17 21:09
*/
public class ValueDealer {
    private ValueDealer(){}

    public static Object castValue(String inValue){
        String resultValue = inValue.toUpperCase();

        // bool
        if (resultValue.equals("TRUE") || resultValue.equals("FALSE")){
            return Boolean.valueOf(resultValue);
        }

        // TODO: another enum or object type need to handle

        // Float
        try {
            if (resultValue.contains(".")){
                return Float.valueOf(resultValue);
            }else {
                return Integer.valueOf(resultValue);
            }
        } catch (NumberFormatException e){
            // just catch exception and do nothing
        }

        // string, if \" in string, take off \"
        if (resultValue.contains("\"")){
            resultValue = resultValue.substring(1,resultValue.length() - 1);
        }
        return resultValue;
    }
}
