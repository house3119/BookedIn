package backend25.bookedin.model;

import org.springframework.data.repository.CrudRepository;


public interface AccountTypeRepository extends CrudRepository<AccountType, Long> {
  AccountType findByType(String account_type);
}
