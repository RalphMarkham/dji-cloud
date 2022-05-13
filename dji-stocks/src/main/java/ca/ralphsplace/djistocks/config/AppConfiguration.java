package ca.ralphsplace.djistocks.config;

import ca.ralphsplace.djistocks.ClientFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Dow Jones Index Stock Data API").version(appVersion));
    }

    @Bean
    public FilterRegistrationBean<ClientFilter> tenantFilter() {
        FilterRegistrationBean<ClientFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ClientFilter());
        registrationBean.addUrlPatterns("/api/stock-data/*");

        return registrationBean;
    }
}
