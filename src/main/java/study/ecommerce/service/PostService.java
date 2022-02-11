package study.ecommerce.service;

public interface PostService {

    // 리뷰 작성
    Long create(Long memberId, Long itemId, String author, String title, String content, float rating);
    // 리뷰 수정
    void modify(Long memberId, Long postId, String title, String content, float rating);
    // 리뷰 작성 포인트 적립

}
