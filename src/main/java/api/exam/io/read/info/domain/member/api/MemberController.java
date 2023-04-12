package api.exam.io.read.info.domain.member.api;

import api.exam.io.read.info.domain.member.application.MemberService;
import api.exam.io.read.info.domain.member.dto.JoinRequest;
import api.exam.io.read.info.domain.member.dto.SimpleMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<SimpleMemberResponse> join(@RequestBody JoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.create(request.toEntity()));
    }


}
