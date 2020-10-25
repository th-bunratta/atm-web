package th.ac.ku.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Transaction;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {
    private th.ac.ku.atm.service.CustomerService customerService;
    private RestTemplate restTemplate;
    private URL baseUrl = new URL("http://bankaccount-api:8091/api/bankaccount/");


    public BankAccountService(RestTemplate restTemplate) throws MalformedURLException {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCustomerService(th.ac.ku.atm.service.CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<BankAccount> getCustomerBankAccount(int customerId)  {
        String url = null;
        try {
            url = new URL( baseUrl,"customer/" + customerId).toString();
            ResponseEntity<BankAccount[]> response =
                    restTemplate.getForEntity(url, BankAccount[].class);

            BankAccount[] accounts = response.getBody();
            return Arrays.asList(accounts);
        } catch (MalformedURLException e) {
            e.printStackTrace(); 
            return null;
        }
    }
    
    public void openBankAccount(BankAccount bankAccount) {
        restTemplate.postForObject(baseUrl.toString(), bankAccount, BankAccount.class);
    }

    public void deleteBankAccount(int id) {
        try {
            String url = new URL(baseUrl,Integer.toString(id)).toString();
            restTemplate.delete(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
        try {
            String  endpoint = new URL(baseUrl, String.format("%s/%s", modeStr, id)).toString();
            BankAccount response = restTemplate.postForObject( endpoint, tx, BankAccount.class);
            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
       
       
    }

    public List<BankAccount> getBankAccounts() {
        String url = baseUrl.toString();
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);
        BankAccount[] accounts = response.getBody();
        assert accounts != null;
        return Arrays.asList(accounts);
    }

    public BankAccount getBankAccount(int id) {
        try {
            String url = new URL(baseUrl, (Integer.toString(id))).toString();
            ResponseEntity<BankAccount> response =
                    restTemplate.getForEntity(url, BankAccount.class);

            return response.getBody();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public void editBankAccount(BankAccount bankAccount) {
        String url = null;
        try {
            url = new URL(baseUrl, (Integer.toString(bankAccount.getId()))).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        restTemplate.put(url, bankAccount);
    }

}
