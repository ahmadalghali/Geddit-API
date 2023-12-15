package com.geddit.service;

import com.geddit.converter.UserToDTOConverter;
import com.geddit.dto.UserDTO;
import com.geddit.dto.auth.UserRegisterRequestDTO;
import com.geddit.exceptions.UserIdNotFoundException;
import com.geddit.exceptions.UsernameNotFoundException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.repository.UserRepository;
import com.geddit.user.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;


    public AppUser createUser(UserRegisterRequestDTO userRegisterRequestDTO) {

        AppUser appUser =
                new AppUser(userRegisterRequestDTO.email(), passwordEncoder.encode(userRegisterRequestDTO.password()));
        return appUser;
    }

    public AppUser getUserByEmail(String email) {
        AppUser user = userRepository
                .findAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return user;
    }
//    public AppUser getUserByEmail(String email) {
//        return userRepository
//                .findAppUserByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException(email));
//    }

    public Optional<AppUser> getUserOptionalByEmail(String username) {
        return userRepository.findAppUserByEmail(username);
    }
//    public Optional<AppUser> getUserOptionalByEmail(String email) {
//        return userRepository.findAppUserByEmail(email);
//    }

    public AppUser saveUser(AppUser newAppUser) {
        return userRepository.save(newAppUser);
    }

    public AppUser getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException(userId));
    }

    public List<UserDTO> searchUsersByKeyword(String keyword) {
        List<AppUser> users = userRepository.findAllByEmailContainingIgnoreCase(keyword);
        return UserToDTOConverter.toDTOList(users);
    }

    public void followUser(String usernameToFollow, AppUser me) {

        AppUser userToFollow = getUserByEmail(usernameToFollow);

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

    public void unfollowUser(String usernameToUnfollow, AppUser me) {

        AppUser userToUnfollow = getUserByEmail(usernameToUnfollow);

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


    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (AppUser) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }
}
