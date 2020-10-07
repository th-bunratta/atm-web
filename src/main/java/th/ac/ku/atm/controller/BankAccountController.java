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

        private BankAccountService bankAccountService;

        @Autowired
        public BankAccountController(BankAccountService bankAccountService) {
            this.bankAccountService = bankAccountService;
        }

        @GetMapping
        public String getBankAccountPage(Model model) {
            model.addAttribute("allAccounts", bankAccountService.getBankAccounts());
            return "bankaccount";
        }
        @PostMapping
        public String registerAccount(@ModelAttribute BankAccount account, Model model) {
            bankAccountService.createBankAccount(account);
            model.addAttribute("allAccounts", bankAccountService.getBankAccounts());
            return "redirect:bankaccount";
        }
        @PostMapping(value="/delete/{id}")
        public String deleteAccount(@PathVariable int id) {
            bankAccountService.deleteBankAccount(id);
            return "redirect:/bankaccount";
        }
}
