package com.song.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void saveLoadTest() {
        // Given
        String title = "Test post!";
        String content = "Test content!!";

        postsRepository.save(
                Posts.builder()
                    .title(title)
                    .content(content)
                    .author("song@example.com")
                    .build()
        );

        // When
        List<Posts> postsList = postsRepository.findAll();

        // Then
        Posts actualPost = postsList.get(0);
        assertThat(actualPost.getTitle()).isEqualTo(title);
        assertThat(actualPost.getContent()).isEqualTo(content);
    }

    @Test
    public void baseTimeEntityTest() {
        // Given
        LocalDateTime past = LocalDateTime.of(2022, 1, 1, 0,0,0);
        postsRepository.save(Posts.builder()
                .title("Title")
                .author("Author")
                .content("Content")
                .build());

        // When
        List<Posts> postsList = postsRepository.findAll();

        // Then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>>>> createDate: "+posts.getCreatedDate()
                +", modifiedDate: "+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(past);
        assertThat(posts.getModifiedDate()).isAfter(past);
    }
}