package com.lambdaschool.usermodel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//53. This is Boiler Plate code that we have to have in here. the whole thing below....it just
// add the swagger endpoints for us because we told spring not to do that automatically.

// Then we are all done however when I run this I get a werid error
//ERROR com.lambdaschool.usermodel.UserModelApplication - Environment Variable OAUTHCLIENTID missing
//ERROR com.lambdaschool.usermodel.UserModelApplication - Environment Variable OAUTHCLIENTSECRET missing

/**
 * The application turns off any automatic web page generate done by Spring. This is done to improve exception handling.
 * However, we do need some web page generate done for Swagger, so we do that here.
 */
@Configuration
public class SwaggerWebMVC
        implements WebMvcConfigurer
{
    /**
     * Adds the Swagger web pages to Spring.
     * This still gives the following warning
     * <p>
     * No mapping for GET /
     * No mapping for GET /csrf
     * <p>
     * All works though
     *
     * @param registry the place that holds the web pages for Spring
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}