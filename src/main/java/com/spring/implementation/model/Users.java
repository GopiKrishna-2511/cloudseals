package com.spring.implementation.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;




@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Users {
    @Id
    private int id;
    private String username;
    private String password;

}