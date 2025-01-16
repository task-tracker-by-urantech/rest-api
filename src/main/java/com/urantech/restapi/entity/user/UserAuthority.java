package com.urantech.restapi.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_authority")
public class UserAuthority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @JsonIgnore
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public String getAuthority() {
        return authority.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthority that = (UserAuthority) o;
        return Objects.equals(id, that.id) && authority == that.authority && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority, user);
    }

    public UserAuthority(Authority authority, User user) {
        this.authority = authority;
        this.user = user;
    }

    public enum Authority {
        USER,
        ADMIN
    }
}
