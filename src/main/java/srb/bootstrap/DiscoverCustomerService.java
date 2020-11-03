package srb.bootstrap;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

@Service
public class DiscoverCustomerService extends TransactionalCustomerService {
    public DiscoverCustomerService(DataSource dataSource) {
        super(dataSource);
    }
}
