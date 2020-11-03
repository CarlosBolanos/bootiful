package srb.bootstrap;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public abstract class DataSourceUtils {
    public static DataSource initializeDdl(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("Schema.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
        return dataSource;
    }
}
