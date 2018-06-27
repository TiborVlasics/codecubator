package com.codecool.poop.service.quiz_services;

import com.codecool.poop.model.assignments.quiz.QuizAssignment;
import com.codecool.poop.repository.quiz_repositories.QuizAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizAssignmentService {

    @Autowired
    private QuizAssignmentRepository quizAssignmentRepository;

    public void addQuizAssignment(QuizAssignment assignment) {
        quizAssignmentRepository.save(assignment);
    }

    public List<QuizAssignment> getAllQuizAssignment() {
        return quizAssignmentRepository.findAll();
    }

    public QuizAssignment getQuizAssignmentById(Integer id) {
        return quizAssignmentRepository.getOne(id);
    }
}
