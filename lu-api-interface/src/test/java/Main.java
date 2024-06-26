import com.lu.client.LuApiClient;
import com.lu.model.User;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/26 10:20
 * @description:
 */
public class Main {

    public static void main(String[] args) {
        String ak = "lu";
        String sk = "youxiao";
        LuApiClient luApiClient = new LuApiClient(ak, sk);
        luApiClient.getNameByGet("http://www.google.com");
        luApiClient.getNameByPost("http://www.google.com");
        User user = new User();
        user.setUsername("hello?");
        luApiClient.getUserNameByPost(user);
    }

}
