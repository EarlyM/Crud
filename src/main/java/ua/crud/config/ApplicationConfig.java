package ua.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySources;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.crud.*")
@PropertySource("classpath:sql.properties")
public class ApplicationConfig {

    @Bean
    public DataSource dataSource(){
        JndiDataSourceLookup jndi = new JndiDataSourceLookup();
        jndi.setResourceRef(true);
        DataSource dataSource = jndi.getDataSource("jdbc/crud");
        return dataSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
