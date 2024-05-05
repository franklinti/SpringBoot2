package franklinit.com.br.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
/**
 * Class padrão pageble e sort
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
      PageableHandlerMethodArgumentResolver pageHandler =  new PageableHandlerMethodArgumentResolver();
      pageHandler.setFallbackPageable(PageRequest.of(0,2));
      resolvers.add(pageHandler);
    }
}
