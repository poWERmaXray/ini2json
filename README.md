# ini2json
灵活地将配置文件转为json数据

将ini配置文件转化为unreal editor可导入为datatable的数据文件

记录一下思路：
使用者需要生成对应的pojo，并指明dataTable使用的pojo
    具体是通过一个方法的泛型

流程：
    泛型方法调用，获取泛型中的属性
    读取配置文件，逐行对属性进行遍历
    读取一个属性，就从行中剔除这个属性的相关字符信息，并返回剔除后的行字符串
    如果行字符串为空，则进行下一行
    
创建一个行信息读取的对象，保存有：
    所有的行信息list<String>
    当前行的字符String
    当前遍历的field对象信息
    内置的注入属性 属性 Object
    内置的整个field + 注入 的字符信息 field=value
    