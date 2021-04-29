package services.impl;

import dto.LoginDto;
import dto.RegistrationDto;
import dto.errors.ErrorEntity;
import lombok.RequiredArgsConstructor;
import models.User;
import repositories.UserRepository;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class Validator {
    private final int MIN_PASSWORD_LENGTH = 4;

    private final UserRepository userRepository;

    public Optional<ErrorEntity> getLoginFormError(LoginDto form) {
        Optional<User> optionalUserEntity = userRepository.findOneByUsername(form.getUsername());
        if (optionalUserEntity.isPresent() == false) {
            return Optional.of(ErrorEntity.USER_NOT_FOUND);
        }
        User userEntity = optionalUserEntity.get();
        if (userEntity.getPassword().equals(form.getPassword()) == false) {
            return Optional.of(ErrorEntity.INCORRECT_PASSWORD);
        }
        return Optional.empty();
    }

    public Optional<ErrorEntity> getUserRegisterFormError(RegistrationDto form) {
        if (form.getUsername() == null) {
            return Optional.of(ErrorEntity.INVALID_USERNAME);
        }
        if (form.getPassword() == null || form.getPassword().length() < MIN_PASSWORD_LENGTH) {
            return Optional.of(ErrorEntity.PASSWORD_TOO_SHORT);
        }
        if (userRepository.findOneByUsername(form.getUsername()).isPresent()) {
            return Optional.of(ErrorEntity.USERNAME_ALREADY_TAKEN);
        }
        return Optional.empty();
    }
}
