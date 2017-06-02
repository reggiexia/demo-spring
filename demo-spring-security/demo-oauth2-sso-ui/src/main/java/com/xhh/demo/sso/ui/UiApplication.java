package com.xhh.demo.sso.ui;

import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * SSO UI
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/4/17 下午3:14
 */
@Log4j2
@SpringBootApplication
@EnableOAuth2Sso
public class UiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }

    @Controller
    static class HomeController {

        @Autowired
        OAuth2RestTemplate restTemplate;

        @Value("${messages.url:http://localhost:8001/auth}/api")
        String messagesUrl;

        @RequestMapping("/")
        String home(Model model) {
            log.debug("===========");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:8001/auth/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            MessageClient messageClient = retrofit.create(MessageClient.class);
            String token = restTemplate.getAccessToken().getValue();
            log.debug("token: {}", token);
            Call<ResponseBody> call = messageClient.get("11111111");
            try {
                ResponseBody list = call.execute().body();
                log.debug("-------: {}", list.string());
                model.addAttribute("messages", list);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "index";
        }

//        @RequestMapping(path = "messages", method = RequestMethod.POST)
//        String postMessages(@RequestParam String text) {
//            Message message = new Message();
//            message.text = text;
//            restTemplate.exchange(RequestEntity
//                    .post(UriComponentsBuilder.fromHttpUrl(messagesUrl).pathSegment("messages").build().toUri())
//                    .body(message), Message.class);
//            return "redirect:/";
//        }
    }

    public interface MessageClient {
        @GET("api/messages")
        Call<ResponseBody> get(@Query("token") String token);
    }

    public static class Message {
        public String text;
        public String username;
        public LocalDateTime createdAt;
    }

    @Bean
    OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
}
