package services;

import dto.QuizDto;
import exceptions.NotFoundException;

import java.util.List;

public interface QuizService {
    void save(QuizDto entity);
    QuizDto findById(int id) throws NotFoundException;
    void update(QuizDto entity);
    void deleteById(int id);
    List<QuizDto> findAll() throws NotFoundException;
}
