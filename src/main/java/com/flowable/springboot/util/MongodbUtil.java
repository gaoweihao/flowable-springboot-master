package com.flowable.springboot.util;

import com.flowable.springboot.bean.UserInfoEntity;
import org.springframework.data.mongodb.core.query.Update;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.List;

public class MongodbUtil {
    public static void main(String[] args) {
        UserInfoEntity user = new UserInfoEntity();
        getMongodbUpdate(user);
    }

    /**
     * 获取更新mogodb数据问题 update 对象
     *
     * @param obj 更新的实体类
     * @return update
     */
    public static Update getMongodbUpdate(Object obj) {
        Update update = new Update();
        Field[] fields = obj.getClass().getDeclaredFields();
        Field field;
        String fieldName;
        for (int len = fields.length, i = 0; i < len; i++) {
            field = fields[i];
            fieldName = field.getName();
//            if (List.class.isAssignableFrom(field.getType())) {
//                Type type = field.getGenericType();
//                if (type instanceof ParameterizedType) {
//                    ParameterizedType pt = (ParameterizedType) type;
//                    //得到对象list中实例的类型
//                    Class clz = (Class) pt.getActualTypeArguments()[0];
//                    //获取到属性的值的Class对象
//                    Class clazz = field.get(obj).getClass();
//                    Method m = clazz.getDeclaredMethod("size");
//                    //调用list的size方法，得到list的长度
//                    int size = (Integer) m.invoke(field.get(db));
//                    for (int i = 0; i < size; i++) {//遍历list，调用get方法，获取list中的对象实例
//                        Method getM = clazz.getDeclaredMethod("get", int.class);
//                        if (!getM.isAccessible()) {
//                            getM.setAccessible(true);
//                        }
//                        mHelper.getDao(clz).createOrUpdate(getM.invoke(field.get(db), i));//加入到Ormlite数据库中
//                    }
//                }
//            } else {
                //类型为基本类型 + String
                Class<?> beanClass = obj.getClass();
                PropertyDescriptor descriptor = getPropertyDescriptor(fieldName, beanClass);
                if (null != descriptor) {
                    Object value = getValueByMethod(descriptor, obj);
                    if (null != value) {
                        update.set(fieldName, value);
                    }
                }
            }
       // }
        return update;
    }

    /**
     * 获取类相关属性所对应的属性值
     *
     * @param fieldName 类属性名称
     * @param beanClass 类class
     * @return object
     */
    public static PropertyDescriptor getPropertyDescriptor(String fieldName, Class<?> beanClass) {
        PropertyDescriptor descriptor = null;
        try {
            descriptor = new PropertyDescriptor(fieldName, beanClass);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return descriptor;
    }

    /**
     * 通过方法获取属性所对应的值
     *
     * @param descriptor
     * @param obj        实体类
     * @return
     */
    public static Object getValueByMethod(PropertyDescriptor descriptor, Object obj) {
        Method readMethod = descriptor.getReadMethod();
        try {
            return readMethod.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
