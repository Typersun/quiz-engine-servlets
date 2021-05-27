package services.impl;

import dto.LoginDto;
import dto.RegistrationDto;
import dto.TokenDto;
import dto.errors.ErrorEntity;
import exceptions.QuizEngineException;
import lombok.AllArgsConstructor;
import models.Profile;
import models.User;
import repositories.UserRepository;
import services.UserSecurityService;
import utils.JwtHelper;

import java.util.Optional;

@AllArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final Validator validator;

    @Override
    public TokenDto save(RegistrationDto entity) throws QuizEngineException {
        Optional<ErrorEntity> optionalError = validator.getUserRegisterFormError(entity);
        if (optionalError.isPresent()) {
            throw new QuizEngineException(optionalError.get());
        }
        User user = User.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .profile(new Profile())
                .build();
        userRepository.save(user);
        return new TokenDto(jwtHelper.generateToken(user));
    }

    @Override
    public TokenDto auth(LoginDto entity) throws QuizEngineException {
        Optional<ErrorEntity> optionalError = validator.getLoginFormError(entity);
        if (optionalError.isPresent()) {
            throw new QuizEngineException(optionalError.get());
        }
        User user = User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
        return new TokenDto(jwtHelper.generateToken(user));
    }
}
