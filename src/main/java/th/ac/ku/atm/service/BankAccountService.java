package th.ac.ku.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Transaction;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {
    private th.ac.ku.atm.service.CustomerService customerService;
    private RestTemplate restTemplate;
    private URI baseUrl = new URI("http://bankaccount-api:8091/api/bankaccount");


    public BankAccountService(RestTemplate restTemplate) throws URISyntaxException {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCustomerService(th.ac.ku.atm.service.CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<BankAccount> getCustomerBankAccount(int customerId) {
        String url = baseUrl.resolve( "customer/" + customerId).toString();
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();

        return Arrays.asList(accounts);
    }
    
    public void openBankAccount(BankAccount bankAccount) {
        restTemplate.postForObject(baseUrl.toString(), bankAccount, BankAccount.class);
    }

    public void deleteBankAccount(int id) {
        String url = baseUrl.resolve(Integer.toString(id)).toString();
        restTemplate.delete(url);
    }

    public String transactAccount(th.ac.ku.atm.service.TransactionMode mode, @PathVariable int id, Transaction tx) {
        String modeStr = "deposit";
        switch (mode) {
            case Deposit:
                modeStr = "deposit";
                break;
            case Withdrawal:
                modeStr = "withdraw";
                break;
        }
        String endpoint = baseUrl.resolve(String.format("%s/%s", modeStr, id)).toString();
        BankAccount response = restTemplate.postForObject( endpoint, tx, BankAccount.class);
        return response.toString();
    }

    public List<BankAccount> getBankAccounts() {
        String url = baseUrl.toString();
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);
        BankAccount[] accounts = response.getBody();
        assert accounts != null;
        return Arrays.asList(accounts);
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }
    public BankAccount getBankAccount(int id) {
        String url = baseUrl.resolve(Integer.toString(id)).toString();
        ResponseEntity<BankAccount> response =
                restTemplate.getForEntity(url, BankAccount.class);

        return response.getBody();
    }

    public void editBankAccount(BankAccount bankAccount) {
        String url = baseUrl.resolve(Integer.toString(bankAccount.getId())).toString();
        restTemplate.put(url, bankAccount);
    }

}
