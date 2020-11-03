package srb.bootstrap;

import java.util.Collection;

import javax.sql.DataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionalCustomerService extends BaseCustomerService {
    public TransactionalCustomerService(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Collection<Customer> save(String... names) {
        return super.save(names);
    }

    @Override
    public Customer findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Collection<Customer> findAll() {
        return super.findAll();
    }
}
