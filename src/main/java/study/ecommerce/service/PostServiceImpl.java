package study.ecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.Item;
import study.ecommerce.entity.Member;
import study.ecommerce.entity.Post;
import study.ecommerce.repository.ItemRepository;
import study.ecommerce.repository.MemberRepository;
import study.ecommerce.repository.PostRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long create(Long memberId, Long itemId, String author, String title, String content, float rating) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalMember.isEmpty()) throw new IllegalStateException("해당 회원이 존재하지 않습니다.");
        if (optionalItem.isEmpty()) throw new IllegalStateException("해당 상품이 존재하지 않습니다.");
        Member member = optionalMember.get();
        Item item = optionalItem.get();

        Post post = new Post(member.getAlias(), title, content, rating, item);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional
    public void modify(Long memberId, Long postId, String title, String content, float rating) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) throw new IllegalStateException("해당 게시물이 존재하지 않습니다.");

        Post findPost = optionalPost.get();
        Long postMemberId = findPost.getMember().getId();

        if (!postMemberId.equals(memberId)) throw new IllegalStateException("게시물 수정 권한이 없습니다.");

        findPost.postModify(title, content, rating);
    }
}
