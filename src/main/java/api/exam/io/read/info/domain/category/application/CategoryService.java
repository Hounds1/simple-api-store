package api.exam.io.read.info.domain.category.application;

import api.exam.io.read.info.domain.auth.error.AuthInfoMismatchException;
import api.exam.io.read.info.domain.category.domain.persist.Category;
import api.exam.io.read.info.domain.category.domain.persist.CategoryRepository;
import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;
import api.exam.io.read.info.domain.category.error.CategoryNotFoundException;
import api.exam.io.read.info.domain.member.domain.persist.Member;
import api.exam.io.read.info.domain.member.domain.persist.MemberRepository;
import api.exam.io.read.info.domain.member.error.MemberNotFoundException;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public SimpleCategoryResponse create(final Category category, final CustomUserDetails principal) throws AuthInfoMismatchException {
        Member findMember = memberRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 상태의 멤버입니다."));

        if (findMember.getStoreName().equals(category.getStoreName())) {
            Category savedCategory = categoryRepository.save(category);

            return SimpleCategoryResponse.of(savedCategory);
        } else throw new AuthInfoMismatchException("로그인 중인 계정의 정보와 다릅니다.");
    }

    public void remove(final Long id) {
        Category findCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("찾을 수 없는 상태입니다."));

        categoryRepository.delete(findCategory);
    }
}
