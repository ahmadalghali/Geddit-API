package com.geddit.service;

import com.geddit.config.JwtService;
import com.geddit.dto.auth.UserRegisterRequestDTO;
import com.geddit.dto.auth.UserSignInRequestDTO;
import com.geddit.dto.auth.UserSignInResponseDTO;
import com.geddit.exceptions.GedditException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.token.Token;
import com.geddit.token.TokenRepository;
import com.geddit.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UsersService usersService;
//    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

        public UserSignInResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO) {

            Optional<AppUser> userOptional =
                    usersService.getUserOptionalByEmail(userRegisterRequestDTO.email());

            if (userOptional.isPresent()) throw new GedditException("Email already exists.");
            AppUser user = usersService.createUser(userRegisterRequestDTO);

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            var savedUser = usersService.saveUser(user);
//            saveUserToken(savedUser, jwtToken);

            return new UserSignInResponseDTO(jwtToken, refreshToken);
        }

    public UserSignInResponseDTO signIn(UserSignInRequestDTO userSignInRequestDTO) {

        var userOptional = usersService.getUserOptionalByEmail(userSignInRequestDTO.email());

        if (userOptional.isEmpty()) {
            // Handle the case where the user does not exist
            // You can throw an exception or return an appropriate response
            // For now, let's throw an exception as an example
            throw new GedditException("Please register.");
        }

        var user = userOptional.get();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userSignInRequestDTO.email(),
                            userSignInRequestDTO.password()
                    )
            );

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

//            revokeAllUserTokens(user);
//            saveUserToken(user, jwtToken);

            return new UserSignInResponseDTO(jwtToken, refreshToken);

        } catch (AuthenticationException e) {
            // Handle authentication failure (e.g., invalid credentials)
            // You can throw an exception or return an appropriate response
            // For now, let's throw an exception as an example
            throw new GedditException("Authentication failed");
        }
    }


//    private void saveUserToken(AppUser user, String jwtToken) {
//            var token = Token.builder()
//                    .user(user)
//                    .token(jwtToken)
//                    .tokenType(TokenType.BEARER)
//                    .expired(false)
//                    .revoked(false)
//                    .build();
//            tokenRepository.save(token);
//        }

//        private void revokeAllUserTokens(AppUser user) {
//            var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//            if (validUserTokens.isEmpty())
//                return;
//            validUserTokens.forEach(token -> {
//                token.setExpired(true);
//                token.setRevoked(true);
//            });
//            tokenRepository.saveAll(validUserTokens);
//        }

        public Optional<AppUser> getCurrentUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return this.usersService.getUserOptionalByEmail(userDetails.getUsername());
            }
            return Optional.empty();
        }

//        public void refreshToken(
//                HttpServletRequest request,
//                HttpServletResponse response
//  )  {
//            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//            final String refreshToken;
//            final String userEmail;
//            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//                return;
//            }
//            refreshToken = authHeader.substring(7);
//            userEmail = jwtService.extractUsername(refreshToken);
//            if (userEmail != null) {
//                var user = this.usersService.getUserByEmail(userEmail);
//
//                if (jwtService.isTokenValid(refreshToken, user)) {
//                    var accessToken = jwtService.generateToken(user);
//                    revokeAllUserTokens(user);
//                    saveUserToken(user, accessToken);
//
//                    new UserSignInResponseDTO(accessToken, refreshToken);
//                }
//            }
//        }

//    public UserDTO register(UserRegisterRequestDTO userRegisterRequestDTO) {
//        Optional<AppUser> userOptional =
//                usersService.getUserOptionalByUsername(userRegisterRequestDTO.username());
//
//        if (userOptional.isPresent()) throw new IllegalArgumentException("Username already exists");
//        AppUser savedUser = usersService.createUser(userRegisterRequestDTO);
//        return UserToDTOConverter.toDTO(savedUser);
//    }
//
//    public UserDTO signIn(UserSignInRequestDTO userSignInRequestDTO) {
//        String userSignInUsername = userSignInRequestDTO.username();
//        String userSignInPassword = userSignInRequestDTO.password();
//
//        AppUser user = usersService.getUserByUsername(userSignInUsername);
//        if (userSignInPassword.equals(user.getPassword())) {
//            return UserToDTOConverter.toDTO(user);
//        }
//        throw new IllegalArgumentException("Password is incorrect");
//    }

}
