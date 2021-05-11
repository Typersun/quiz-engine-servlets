package services;

import dto.QuestionDto;
import dto.QuestionOptionDto;
import exceptions.NotFoundException;

import java.util.List;

public interface QuestionOptionService {
    void save(QuestionOptionDto entity);
    QuestionOptionDto findById(int id) throws NotFoundException;
    void update (QuestionOptionDto entity);
    void deleteById(int id);
    List<QuestionOptionDto> findAll() throws NotFoundException;
}
