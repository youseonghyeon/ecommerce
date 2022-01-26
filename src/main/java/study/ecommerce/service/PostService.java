package study.ecommerce.service;

public interface PostService {

    // 리뷰 작성
    Long post(Long memberId, Long itemId, String author, String title, String content, float rating);
    // 리뷰 수정
    // 리뷰 작성 포인트 적립

}
