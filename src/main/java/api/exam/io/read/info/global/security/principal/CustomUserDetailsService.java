package api.exam.io.read.info.global.security.principal;

import api.exam.io.read.info.domain.member.domain.persist.MemberRepository;
import api.exam.io.read.info.domain.member.error.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findDetailsByUsername(username)
                .orElseThrow( () -> new MemberNotFoundException("찾을 수 없는 상태의 멤버입니다."));
    }
}
