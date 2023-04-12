package api.exam.io.read.info.domain.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifiedMemberRequest {

    private String password;
    private String storeName;
}
