package th.ac.ku.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.ac.ku.atm.data.BankAccountRepository;
import th.ac.ku.atm.data.CustomerRepository;
import th.ac.ku.atm.model.BankAccount;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BankAccountService {
    private final BankAccountRepository repository;
    private CustomerService customerService;

    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void createBankAccount(BankAccount account) {
        if (customerService.findCustomer(account.getCustomerId()) != null) {
            repository.save(account);
        } else throw new NoSuchElementException();
    }

    public void deleteBankAccount(int id) {
        try {
            repository.deleteById(id);
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public List<BankAccount> getBankAccounts() {
        return repository.findAll();
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }
    public BankAccount findBankAccount(int id) {
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
