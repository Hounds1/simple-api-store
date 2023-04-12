package api.exam.io.read.info.domain.member.domain.persist;

import api.exam.io.read.info.domain.member.domain.vo.RoleType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String storeName;

    private boolean activated;

    private RoleType role;

    public void setEncodePass(final String encodePass) {
        this.password = encodePass;
    }

    public void unActivated() {
        this.activated = false;
    }
}
