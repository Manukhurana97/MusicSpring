package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String authority;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "username", nullable = false)
    public Users users;


    public int getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }


}
