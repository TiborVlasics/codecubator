package com.codecool.poop.repository.QuizRepositories;

import com.codecool.poop.model.assignments.quiz.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {
}
