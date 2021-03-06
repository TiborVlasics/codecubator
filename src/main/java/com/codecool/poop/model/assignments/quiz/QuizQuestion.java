package com.codecool.poop.model.assignments.quiz;

import com.codecool.poop.model.assignments.Assignment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String questionText;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<QuizAnswer> quizAnswers = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name = "assignment_id")
    private Set<Assignment> assignments = new HashSet<>();

    public QuizQuestion() {}

    public QuizQuestion(String questionText) {
        this.questionText = questionText;
    }

    private Boolean isAnswersValid(List<QuizAnswer> answers) {
        if (answers == null || answers.size() < 2) return false;
        for (QuizAnswer answer : answers) {
            if (answer.getAnswerValidity()) return true;
        }
        return false;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<QuizAnswer> getQuizAnswers() {
        return quizAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addAnswer(QuizAnswer answer) {
//        if (!quizAnswers.contains(answer)){
            quizAnswers.add(answer);
//        }
    }

    public void addAssignment(Assignment assignment){
        assignments.add(assignment);
    }

    public void removeAssignment(Assignment assignment){
        assignments.remove(assignment);
    }

    public Boolean isQuestionValid() {
        return isAnswersValid(quizAnswers);
    }

    public int getMaxPoints() {
        return (int) quizAnswers.stream()
                .filter(QuizAnswer::getAnswerValidity)
                .count();
    }


}
