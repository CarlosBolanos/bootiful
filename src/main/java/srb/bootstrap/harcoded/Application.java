package srb.bootstrap.harcoded;

import javax.sql.DataSource;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import srb.bootstrap.CustomerService;
import srb.bootstrap.DataSourceConfiguration;
import srb.bootstrap.Demo;
import srb.bootstrap.SpringUtils;

@Configuration
@EnableTransactionManagement
@ComponentScan
@Import(DataSourceConfiguration.class)
public class Application {

    @Bean
    PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    TransactionTemplate transactionTemplate(PlatformTransactionManager tm) {
        return new TransactionTemplate(tm);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringUtils.run(Application.class, "prod");
        CustomerService cs = ac.getBean(CustomerService.class);

        // DataSource dataSource = new
        // EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        // DataSource initializeDataSource = DataSourceUtils.initializeDdl(dataSource);
        // CustomerService cs = new DataSourceCustomerService(initializeDataSource);
        // PlatformTransactionManager dstxManager = new
        // DataSourceTransactionManager(initializeDataSource);
        // TransactionTemplate tt = new TransactionTemplate(dstxManager);
        // TransactionTemplateCustomerService cs = new
        // TransactionTemplateCustomerService(dataSource, tt);
        Demo.workWithCustomerService(Application.class, cs);
    }
}
