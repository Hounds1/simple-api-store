package api.exam.io.read.info.domain.category.application;

import api.exam.io.read.info.domain.auth.error.AuthInfoMismatchException;
import api.exam.io.read.info.domain.category.domain.persist.Category;
import api.exam.io.read.info.domain.category.domain.persist.CategoryRepository;
import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;
import api.exam.io.read.info.domain.category.error.CategoryNotFoundException;
import api.exam.io.read.info.domain.member.domain.persist.Member;
import api.exam.io.read.info.domain.member.domain.persist.MemberRepository;
import api.exam.io.read.info.domain.member.error.MemberNotFoundException;
import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static api.exam.io.read.info.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public SimpleCategoryResponse create(final Category category, final CustomUserDetails principal) throws AuthInfoMismatchException {
        Member findMember = memberRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if (findMember.getStoreName().equals(category.getStoreName())) {
            Category savedCategory = categoryRepository.save(category);

            return SimpleCategoryResponse.of(savedCategory);
        } else throw new AuthInfoMismatchException(AUTH_INFO_MISMATCHED);
    }

    public void remove(final Long id) {
        Category findCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        categoryRepository.delete(findCategory);
    }
}
