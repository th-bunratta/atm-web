package th.ac.ku.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.data.BankAccountRepository;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Transaction;

import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {
    private CustomerService customerService;
    private RestTemplate restTemplate;
    private BankAccountRepository repository;


    public BankAccountService(RestTemplate restTemplate, BankAccountRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<BankAccount> getCustomerBankAccount(int customerId) {
        String url = "http://localhost:8091/api/bankaccount/customer/" +
                customerId;
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();

        return Arrays.asList(accounts);
    }



    public void openBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount";
        restTemplate.postForObject(url, bankAccount, BankAccount.class);
    }
    

    public void deleteBankAccount(int id) {
        String url = "http://localhost:8091/api/bankaccount/" + id;
        restTemplate.delete(url);
    }

    public String transactAccount(TransactionMode mode, @PathVariable int id, Transaction tx) {
        String modeStr = "deposit";
        switch (mode) {
            case Deposit:
                modeStr = "deposit";
                break;
            case Withdrawal:
                modeStr = "withdraw";
                break;
        }
        String endpoint = String.format("http://localhost:8091/api/bankaccount/%s/%s", modeStr, id);
        BankAccount response = restTemplate.postForObject( endpoint, tx, BankAccount.class);
        return response.toString();
    }

    public List<BankAccount> getBankAccounts() {
        String url = "http://localhost:8091/api/bankaccount/";

        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();
        assert accounts != null;
        return Arrays.asList(accounts);
        //return repository.findAll();
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }
    public BankAccount getBankAccount(int id) {
        String url = "http://localhost:8091/api/bankaccount/" + id;

        ResponseEntity<BankAccount> response =
                restTemplate.getForEntity(url, BankAccount.class);

        return response.getBody();
    }

    public void editBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId();
        restTemplate.put(url, bankAccount);
    }

}
