package api.exam.io.read.info.domain.member.api;

import api.exam.io.read.info.domain.member.application.MemberReadService;
import api.exam.io.read.info.domain.member.application.MemberService;
import api.exam.io.read.info.domain.member.dto.JoinRequest;
import api.exam.io.read.info.domain.member.dto.ModifiedMemberRequest;
import api.exam.io.read.info.domain.member.dto.SimpleMemberResponse;
import api.exam.io.read.info.global.common.PageCustomResponse;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;
    private final MemberReadService memberReadService;

    @PostMapping("/public/members")
    public ResponseEntity<SimpleMemberResponse> join(@RequestBody JoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.create(request.toEntity()));
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> unActivated() {
        memberService.unActivated(getPrincipal());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/members")
    public ResponseEntity<SimpleMemberResponse> modified(@RequestBody ModifiedMemberRequest request) {
        return ResponseEntity.ok().body(memberService.modified(request, getPrincipal()));
    }

    @GetMapping("/members/search/username")
    public ResponseEntity<PageCustomResponse<SimpleMemberResponse>> findAllByUsername(@RequestParam(name = "username") String username,
                                                                                      @PageableDefault(sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(memberReadService.findAllByUsername(username, pageable));
    }

    @GetMapping("/members/search/store")
    public ResponseEntity<PageCustomResponse<SimpleMemberResponse>> findAllByStoreName(@RequestParam(name = "storeName") String storeName,
                                                                                       @PageableDefault(sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(memberReadService.findAllByStoreName(storeName, pageable));
    }

    public CustomUserDetails getPrincipal() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
