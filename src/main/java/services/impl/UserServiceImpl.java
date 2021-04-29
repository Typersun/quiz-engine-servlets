package services.impl;

import dto.MessageDto;
import dto.UserDto;
import exceptions.NotFoundException;
import models.User;
import repositories.UserRepository;
import services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto findById(int id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = UserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .build();
            return userDto;
        }
        else throw new NotFoundException("Dear father (otec), the user with the given id was not found");
    }

    @Override
    public void update(UserDto entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        userRepository.updateWithoutPassword(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);

    }

    @Override
    public List<UserDto> findAll() throws NotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("Otec, ne naideno");
        }
        else {
            List<UserDto> userDtos = new ArrayList<>();
            for (int i = 0; i < users.size(); i++) {
                 userDtos.add(UserDto.builder()
                         .id(users.get(i).getId())
                         .username(users.get(i).getUsername())
                         .build());
            }
            return userDtos;
        }
    }
}
