package com.majorproject.CareerforIT.Config;

import com.majorproject.CareerforIT.Filters.JwtTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> jwtTokenFilter() {
        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtTokenFilter());  // Register the JwtTokenFilter here
        registrationBean.addUrlPatterns("/login/student/");  // Apply the filter to all incoming requests
        System.out.println(registrationBean.getUrlPatterns());
        return registrationBean;
    }
}
