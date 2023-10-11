package org.ylab.infrastructure.input;

import java.util.Scanner;


/**
 * infrastructure класс
 * реализающий чтение с консоли
 * @author dayaDanya
 */
public class InputService {
    /**
     * Объект сканнер используемый для чтения
     */
    private final Scanner scanner;

    /**
     * конструктор
     */
    public InputService() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * читает строку с консоли
     * @return строка с удаленными пробелами в начале и конце
     */
    public String scanChoice(){
        return scanner.nextLine().trim();
    }

    /**
     * читает число
     * @return возвращает long число
     */
    public Long scanLong(){return scanner.nextLong();}

}
