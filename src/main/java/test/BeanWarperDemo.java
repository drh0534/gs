package test;

import com.alibaba.fastjson.JSON;
import com.gs.pp.orm.User;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

/**
 * Created by duronghong on 2016/3/3.
 * Email: drh0534@163.com
 */
public class BeanWarperDemo {

    public static void main(String[] args) {
        BeanWrapper user = new BeanWrapperImpl(new User());
        user.setPropertyValue("name","Jims");
        //also can do this
        PropertyValue value = new PropertyValue("name","Jimy");
        user.setPropertyValue(value);

        System.out.println(JSON.toJSONString(user));
    }

}
