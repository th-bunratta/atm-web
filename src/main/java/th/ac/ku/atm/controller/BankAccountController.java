package th.ac.ku.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Transaction;
import th.ac.ku.atm.service.BankAccountService;
import th.ac.ku.atm.service.TransactionMode;

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


        @GetMapping("/edit/{id}")
        public String getEditBankAccountPage(@PathVariable int id, Model model) {
            BankAccount account = accountService.getBankAccount(id);
            model.addAttribute("bankAccount", account);
            return "bankaccount-edit";
        }
        @PostMapping("/deposit/{accountId}")
        public String depositIntoAccount(@ModelAttribute Transaction tx, @PathVariable int accountId) {
            accountService.transactAccount(TransactionMode.Deposit, accountId, tx);
            return "redirect:/bankaccount";
        }
        @PostMapping("/withdraw/{accountId}")
        public String withdrawFromAccount(@ModelAttribute Transaction tx, @PathVariable int accountId) {
            accountService.transactAccount(TransactionMode.Withdrawal, accountId, tx);
            return "redirect:/bankaccount";
        }
        @PostMapping("/edit/{id}")
        public String editAccount(@PathVariable int id,
                                  @ModelAttribute BankAccount bankAccount,
                                  Model model) {

            accountService.editBankAccount(bankAccount);
            model.addAttribute("bankaccounts",accountService.getBankAccounts());
            return "redirect:/bankaccount";
        }

}
