package com.developol.polchatex.persistence;


import javax.persistence.*;

@Entity
public class ChatDto {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private UserDto user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private UserDto user2;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUser1() {
        return user1;
    }

    public void setUser1(UserDto user1) {
        this.user1 = user1;
    }

    public UserDto getUser2() {
        return user2;
    }

    public void setUser2(UserDto user2) {
        this.user2 = user2;
    }
}
