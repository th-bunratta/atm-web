package th.ac.ku.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.service.BankAccountService;
import th.ac.ku.atm.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {

        private BankAccountService bankAccountService;

        @Autowired
        public BankAccountController(BankAccountService bankAccountService) {
            this.bankAccountService = bankAccountService;
        }
        List<BankAccount> accounts = new ArrayList<>();

        @GetMapping
        public String getCustomerPage(Model model) {
            model.addAttribute("allAccounts", accounts);
            return "bankaccount";
        }
        @PostMapping
        public String registerRegister(@ModelAttribute BankAccount account, Model model) {
            accounts.add(account);
            model.addAttribute("allAccounts", accounts);
            return "redirect:bankaccount";
        }
}
