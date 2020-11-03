package srb.bootstrap.bootiful;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import srb.bootstrap.TransactionalCustomerService;

@Service
public class BootifulCustomerService extends TransactionalCustomerService {
    public BootifulCustomerService(DataSource dataSource) {
        super(dataSource);
    }
}
