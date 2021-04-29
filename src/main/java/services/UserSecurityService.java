package services;

import dto.LoginDto;
import dto.MessageDto;
import dto.RegistrationDto;
import dto.TokenDto;
import exceptions.QuizEngineException;

public interface UserSecurityService {
    TokenDto save(RegistrationDto entity) throws QuizEngineException;
    TokenDto auth(LoginDto entity) throws QuizEngineException;
}
