package org.vikinc.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.vikinc.community.dto.DTOAccessToken;
import org.vikinc.community.dto.DTOGithubUser;

import java.io.IOException;

/**
 * 验证Github OAuth 的工具类
 */

@Component
public class GithubProvider {
    //获取access_token令牌
    public String getAccessToken(DTOAccessToken DTOAccessToken){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(DTOAccessToken),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //默认情况下 response.body().string()的值为：
            // access_token=gho_16C7e42F292c6912E7710c838347Ae178B4a&scope=repo%2Cgist&token_type=bearer
            String accessToken = response.body().string().split("&")[0].split("=")[1];
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }

    //获取github用户信息
    public DTOGithubUser getGithubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            DTOGithubUser DTOGithubUser = JSON.parseObject(string, DTOGithubUser.class);
            return DTOGithubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
