package board.board.service;

import board.board.converter.PostConverter;
import board.board.domain.Member;
import board.board.domain.Post;
import board.board.dto.post.PostRequest;
import board.board.dto.post.PostResponse;
import board.board.repository.MemberRepository;
import board.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponse.PostDto create(PostRequest.CreateDto createDto){
        Member member = memberRepository.findByEmail(createDto.getEmail()).orElseThrow(() -> new Error());

        Post post = PostConverter.CreateDtoToPost(createDto,member);
        postRepository.save(post);

        return PostConverter.PostToPostDto(post);
    }

    @Transactional
    public PostResponse.PostDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new Error());
        return PostConverter.PostToPostDto(post);
    }

    @Transactional
    public PostResponse.PostDto update(PostRequest.UpdateDto updateDto){
        Post post = postRepository.findById(updateDto.getPostId()).orElseThrow(() -> new Error());

        post.updateTitleAndContent(updateDto.getTitle(),updateDto.getContent());

        postRepository.save(post);
        return PostConverter.PostToPostDto(post);
    }
}
