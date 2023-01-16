package com.herb.userapp.entity;

import com.herb.userapp.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", users=" + users +
                '}';
    }
}
