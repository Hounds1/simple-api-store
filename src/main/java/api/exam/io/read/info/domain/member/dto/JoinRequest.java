package api.exam.io.read.info.domain.member.dto;

import api.exam.io.read.info.domain.member.domain.persist.Member;
import api.exam.io.read.info.domain.member.domain.vo.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class JoinRequest {

    private String username;

    private String password;

    private String storeName;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .storeName(storeName)
                .role(RoleType.USER)
                .build();
    }
}
