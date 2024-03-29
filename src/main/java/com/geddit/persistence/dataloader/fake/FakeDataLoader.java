package com.geddit.persistence.dataloader.fake;

import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Comment;
import com.geddit.persistence.entity.Community;
import com.geddit.persistence.entity.Post;
import com.geddit.persistence.repository.CommentRepository;
import com.geddit.persistence.repository.CommunityRepository;
import com.geddit.persistence.repository.PostRepository;
import com.geddit.persistence.repository.UserRepository;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class FakeDataLoader implements CommandLineRunner {
  private final CommunityRepository communityRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  Faker faker = new Faker();

  @Autowired
  public FakeDataLoader(
      CommunityRepository communityRepository,
      PostRepository postRepository,
      CommentRepository commentRepository,
      UserRepository userRepository) {
    this.communityRepository = communityRepository;
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) {
    seedDatabase();
  }

  private void seedDatabase() {
    List<AppUser> savedUsers = seedUsers();
    List<Community> savedCommunities = seedCommunities(savedUsers);
    List<Post> savedPosts = seedPosts(savedCommunities, savedUsers);
//    List<Comment> savedComments = seedComments(savedPosts, savedUsers);
  }

  private List<AppUser> seedUsers() {
    List<AppUser> savedUsers = new ArrayList<>();
    int userCount = getRandomNumber(50);
    for (int i = 0; i < userCount; i++) {
      String email = faker.internet().emailAddress();
      String username = faker.funnyName().name().replaceAll("\\s", "");
      AppUser appUser = new AppUser(email,  new BCryptPasswordEncoder().encode("password123"));
      savedUsers.add(userRepository.save(appUser));
    }
    userRepository.save(new AppUser("ahmad@gmail.com", new BCryptPasswordEncoder().encode("password123")));
    userRepository.save(new AppUser("mohie@gmail.com", new BCryptPasswordEncoder().encode("password123")));

    return savedUsers;
  }

  private List<Community> seedCommunities(List<AppUser> users) {

    List<Community> savedCommunities = new ArrayList<>();

    String[] communityNames = {
      "react",
      "islam",
      "cscareerquestions",
      "NBA",
      "NoStupidQuestions",
      "ELI5",
      "finance",
      "webdev",
      "java",
      "golang",
      "memes"
    };

    for (int i = 0; i < communityNames.length; i++) {
      AppUser createdBy = getRandomItem(users);

      String description = faker.lorem().sentence();
      String name = communityNames[i];
      Community community = new Community(name, description, createdBy);
      savedCommunities.add(communityRepository.save(community));
    }

    return savedCommunities;
  }

  private List<Post> seedPosts(List<Community> communities, List<AppUser> users) {
    List<Post> savedPosts = new ArrayList<>();
    int numPosts = getRandomNumber(50);

    for (int i = 0; i < numPosts; i++) {
      AppUser author = getRandomItem(users);
      Community community = getRandomItem(communities);

      String title = faker.lorem().sentence() + "?";
      String body = getRandomText();

      Post post = new Post(title, community, author, body);
      savedPosts.add(postRepository.save(post));
    }

    return savedPosts;
  }

  private List<Comment> seedComments(List<Post> posts, List<AppUser> users) {
    List<Comment> savedComments = new ArrayList<>();

    int numComments = getRandomNumber(50);

    AppUser author = getRandomItem(users);
    Post post = getRandomItem(posts);

    for (int i = 0; i < numComments; i++) {
      String text = getRandomText();
      Comment comment = new Comment(text, author, post);
      savedComments.add(commentRepository.save(comment));
    }
    return savedComments;
  }

  //  Helper methods
  private <T> T getRandomItem(List<T> list) {
    int randomIndexInList = faker.random().nextInt(list.size());
    return list.get(randomIndexInList);
  }

  int getRandomNumber(int upTo) {
    return faker.random().nextInt(upTo);
  }

  String getRandomText() {
    return String.join("\n\n", faker.lorem().paragraphs(getRandomNumber(5)));
  }
}
