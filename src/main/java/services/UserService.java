package services;

import dto.MessageDto;
import dto.UserDto;
import exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto findById(int id) throws NotFoundException;
    void update(UserDto entity);
    void deleteById(int id);
    List<UserDto> findAll() throws NotFoundException;
}
