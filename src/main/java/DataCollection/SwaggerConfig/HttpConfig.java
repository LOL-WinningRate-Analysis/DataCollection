package DataCollection.SwaggerConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfig {

    @Bean  //bean이 있는 거를 호출해서 받는 resttemplate를 주입시킨다..?
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }

}
