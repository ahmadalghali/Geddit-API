package com.geddit.service;

import com.geddit.converter.UserToDTOConverter;
import com.geddit.dto.UserDTO;
import com.geddit.dto.UserRegisterRequestDTO;
import com.geddit.exceptions.UserIdNotFoundException;
import com.geddit.exceptions.UsernameNotFoundException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser createUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        if (userRegisterRequestDTO.username().contains(" "))
            throw new IllegalArgumentException("Username cannot contain spaces");
        if (userRegisterRequestDTO.password().contains(" "))
            throw new IllegalArgumentException("Password cannot contain spaces");

        AppUser appUser =
                new AppUser(userRegisterRequestDTO.username(), userRegisterRequestDTO.password());
        return saveUser(appUser);
    }

    public AppUser getUserByUsername(String username) {
        return userRepository
                .findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<AppUser> getUserOptionalByUsername(String username) {
        return userRepository.findAppUserByUsername(username);
    }

    private AppUser saveUser(AppUser newAppUser) {
        return userRepository.save(newAppUser);
    }

    public AppUser getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException(userId));
    }

    public List<UserDTO> searchUsersByKeyword(String keyword) {
        List<AppUser> users = userRepository.findAllByUsernameContainingIgnoreCase(keyword);
        return UserToDTOConverter.toDTOList(users);
    }

    public void followUser(String usernameToFollow, String username) {
        AppUser me = getUserByUsername(username);

        AppUser userToFollow = getUserByUsername(usernameToFollow);

        var isSelf = userToFollow.getId().equals(me.getId());

        if (isSelf) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        if (me.getFollowing().contains(userToFollow)) {
            throw new IllegalArgumentException("User already followed");
        }
        me.getFollowing().add(userToFollow);
        userRepository.save(me);
    }

    public void unfollowUser(String usernameToUnfollow, String username) {
        AppUser me = getUserByUsername(username);

        AppUser userToUnfollow = getUserByUsername(usernameToUnfollow);

        var isSelf = userToUnfollow.getId().equals(me.getId());

        if (isSelf) {
            throw new IllegalArgumentException("Cannot unfollow yourself");
        }

        if (me.getFollowing().contains(userToUnfollow)) {
            me.getFollowing().remove(userToUnfollow);
        } else {
            throw new IllegalArgumentException("User not followed");
        }
        userRepository.save(me);
    }
}
