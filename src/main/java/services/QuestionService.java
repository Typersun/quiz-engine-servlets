package services;

import dto.QuestionDto;
import exceptions.NotFoundException;

import java.util.List;

public interface QuestionService {
    void save(QuestionDto entity);
    QuestionDto findById(int id) throws NotFoundException;
    void update (QuestionDto entity);
    void deleteById(int id);
    List<QuestionDto> findAll() throws NotFoundException;
}
