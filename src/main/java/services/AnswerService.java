package services;

import dto.AnswerDto;
import exceptions.NotFoundException;

import java.util.List;

public interface AnswerService {
    void save(AnswerDto entity);
    AnswerDto findById(int id) throws NotFoundException;
    void update(AnswerDto entity);
    void deleteById(int id) throws NotFoundException;
    List<AnswerDto> findAll() throws NotFoundException;
}
