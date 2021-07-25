package com.example.socialmedia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {
    private static final long SerialVersionUID = 4236784L;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private final List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "users_follower",
            joinColumns = @JoinColumn(name = "follower_id")
    )
    private final List<User> following = new ArrayList<>();

}
