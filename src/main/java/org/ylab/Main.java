package org.ylab;

import org.ylab.application.BalanceService;
import org.ylab.application.PlayerService;
import org.ylab.domain.models.Player;
import org.ylab.domain.models.Transaction;
import org.ylab.domain.models.TransactionType;
import org.ylab.infrastructure.input.DBConfig;
import org.ylab.infrastructure.input.InputService;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        DBConfig dbConfig = new DBConfig();
        dbConfig.connect();
        InputService inputService = new InputService();
        PlayerService playerService = new PlayerService();
        BalanceService balanceService = new BalanceService();

        String choice;
        do {
            System.out.println("Choose action:");
            System.out.println("1. Registration");
            System.out.println("2. Authorization");
            System.out.println("3. Exit");

            choice = inputService.scanChoice().trim();

            switch (choice) {
                case "1":
                    System.out.println("Register a player");
                    System.out.println("Username: ");
                    String usernameToReg = inputService.scanChoice().trim();
                    System.out.println("Password: ");
                    String passwordToReg = inputService.scanChoice().trim();

                    System.out.println(playerService.registerPlayer(usernameToReg, passwordToReg));
                    break;
                case "2":
                    System.out.println("Authorization");
                    System.out.println("Username: ");
                    String usernameToAuth = inputService.scanChoice().trim();
                    System.out.println("Password: ");
                    String passwordToAuth = inputService.scanChoice().trim();

                    Optional<Player> found = playerService.authorizePlayer(usernameToAuth, passwordToAuth);
                    System.out.println(balanceService.login(found));
                    while (found.isPresent()) {
                        
                        if (found.get().getUsername().equals("admin")) {
                            System.out.println("==================");
                            System.out.println("ADMIN PANEL");
                            System.out.println("==================");
                            System.out.println("1. Check history");
                            System.out.println("2. Log out");
                            do {
                                choice = inputService.scanChoice().trim();
                            } while (choice.isEmpty());

                            switch(choice){
                                case "1":
                                    System.out.println(balanceService.checkHistory(found.get()));
                                    break;
                                case "2":
                                    found = Optional.empty();
                                    System.out.println("See you soon<3");
                                default:
                                    System.out.println("Wrong choose try again");
                            }
                        } else {

                            System.out.println("Choose operation:");
                            System.out.println("1. Debit");
                            System.out.println("2. Credit");
                            System.out.println("3. Check Balance");
                            System.out.println("4. Check History");
                            System.out.println("5. Log out");
                            do {
                                choice = inputService.scanChoice().trim();
                            } while (choice.isEmpty());

                            long amount, transId;
                            switch (choice) {
                                case "1" -> {
                                    System.out.println("DEBIT OPERATION");
                                    System.out.println("Enter the withdrawal amount: ");
                                    amount = inputService.scanLong();
                                    System.out.println("Enter a unique transaction id");
                                    transId = inputService.scanLong();
                                    System.out.println(
                                            balanceService.debit( found.get(),
                                                    new Transaction(transId, found.get().getId(), TransactionType.DEBIT, amount)));
                                }
                                case "2" -> {
                                    System.out.println("CREDIT OPERATION");
                                    System.out.println("Enter the deposit amount: ");
                                    amount = inputService.scanLong();
                                    System.out.println("Enter a unique transaction id");
                                    transId = inputService.scanLong();
                                    System.out.println(
                                            balanceService.credit(found.get(),
                                                    new Transaction( transId,  found.get().getId(),TransactionType.CREDIT, amount)));
                                }
                                case "3" -> {
                                    System.out.println("CHECK BALANCE");
                                    System.out.println("Current balance is " + balanceService.checkBalance(found.get()));
                                }
                                case "4" -> {
                                    String history = balanceService.checkHistory(found.get());
                                    if (history.isEmpty())
                                        System.out.println("No transactions yet");
                                    else
                                        System.out.println("Your transactions:");
                                    System.out.println(history);
                                }
                                case "5" -> {
                                    found = balanceService.logout(found.get());
                                    System.out.println("See you soon<3");
                                }
                                default -> System.out.println("Wrong choose, try again");
                            }
                        }
                    }
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Wrong choose, try again");
                    break;
            }

        } while (true);


    }
}