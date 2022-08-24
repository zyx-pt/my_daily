package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Account {
    private String name;
    private int age;
    private String address;
    private BigDecimal wallet;
    private Long weight;

    public Account() {
    }

    public Account(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Account(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Account(String name, int age, String address, BigDecimal wallet) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.wallet = wallet;
    }
}