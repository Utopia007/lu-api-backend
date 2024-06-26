import com.lu.client.LuApiClient;
import com.lu.model.User;
import com.lu.project.MyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/26 19:53
 * @description:
 */
@SpringBootTest(classes = MyApplication.class)
public class LuApiInterfaceApplicationTests {

    // 注入一个名为yuApiClient的Bean
    @Resource
    private LuApiClient luApiClient;


    // 表示这是一个测试方法
    @Test
    public void contextLoads() {
        // 调用yuApiClient的getNameByGet方法，并传入参数"yupi"，将返回的结果赋值给result变量
        String result = luApiClient.getNameByGet("yupi");
        // 创建一个User对象
        User user = new User();
        // 设置User对象的username属性为"liyupi"
        user.setUsername("liyupi");
        // 调用yuApiClient的getUserNameByPost方法，并传入user对象作为参数，将返回的结果赋值给usernameByPost变量
        String usernameByPost = luApiClient.getUserNameByPost(user);
        System.out.println("``````````````````````````````````````````````````````");
        // 打印result变量的值
        System.out.println(result);
        // 打印usernameByPost变量的值
        System.out.println(usernameByPost);
    }

}
