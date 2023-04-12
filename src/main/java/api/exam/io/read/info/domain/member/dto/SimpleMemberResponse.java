package api.exam.io.read.info.domain.member.dto;

import api.exam.io.read.info.domain.member.domain.persist.Member;
import api.exam.io.read.info.domain.member.domain.vo.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SimpleMemberResponse {

    private String username;
    private RoleType role;

    public static SimpleMemberResponse of(final Member member) {
        return new SimpleMemberResponse(member.getUsername(), member.getRole());
    }
}
