package th.ac.ku.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.service.BankAccountService;

@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {

        private BankAccountService accountService;

        @Autowired
        public BankAccountController(BankAccountService bankAccountService) {
            this.accountService = bankAccountService;
        }

        @GetMapping
        public String getBankAccountPage(Model model) {
            model.addAttribute("allAccounts", accountService.getBankAccounts());
            return "bankaccount";
        }
        @PostMapping
        public String openAccount(@ModelAttribute BankAccount account, Model model) {
            accountService.openBankAccount(account);
            model.addAttribute("bankaccounts",accountService.getBankAccounts());
            return "redirect:bankaccount";
        }
        @PostMapping(value="/delete/{id}")
        public String deleteAccount(@PathVariable int id) {
            accountService.deleteBankAccount(id);
            return "redirect:/bankaccount";
        }
}
