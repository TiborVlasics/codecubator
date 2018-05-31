package com.codecool.poop.ORM;

import com.codecool.poop.model.Skills;
import com.codecool.poop.model.assignments.coding.CodingAnswer;
import com.codecool.poop.model.assignments.coding.CodingAssignment;
import com.codecool.poop.model.assignments.coding.CodingQuestion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CodingQuesttManagerTest {

    @BeforeAll
    private static void setup() {
        Map<Skills, Integer> reward = new HashMap<>();
        reward.put(Skills.HTML_BASIC, 5);
        reward.put(Skills.CSS_BASIC, 8);
        Set<CodingQuestion> questions = new HashSet<>();
        CodingQuestion question11 = new CodingQuestion("What?");
        CodingQuestion question12 = new CodingQuestion("Why?");
        Set<CodingQuestion> questions2 = new HashSet<>();
        CodingQuestion question21 = new CodingQuestion("How?");
        CodingQuestion question22 = new CodingQuestion("Who?");

        List<CodingAnswer> answers11 = new ArrayList<>();
        answers11.add(new CodingAnswer("Th is", question11));
        answers11.add(new CodingAnswer("Is", question11));
        answers11.add(new CodingAnswer("Spar    ta", question11));
        List<CodingAnswer> answers12 = new ArrayList<>();
        answers11.add(new CodingAnswer("Just", question12));
        answers11.add(new CodingAnswer("Be cause", question12));
        List<CodingAnswer> answers21 = new ArrayList<>();
        answers11.add(new CodingAnswer("Fourty", question21));
        answers11.add(new CodingAnswer("Two", question21));
        List<CodingAnswer> answers22 = new ArrayList<>();
        answers11.add(new CodingAnswer("Me", question22));

        questions.add(question11);
        questions.add(question12);
        questions2.add(question21);
        questions2.add(question22);

        CodingAssignment assignment = new CodingAssignment("Number one",
                "Description",
                reward,
                3,
                questions);
        for (CodingQuestion question : questions) {
            question.addAssignment(assignment);
        }
        CodingAssignment assignment2 = new CodingAssignment("Number two",
                "Other",
                reward,
                2,
                questions);
        for (CodingQuestion question : questions2) {
            question.addAssignment(assignment2);
        }

        CodingQuestManager manager = CodingQuestManager.getInstance();

        manager.addCodingAssignmentToDB(assignment);
        manager.addCodingAssignmentToDB(assignment2);

        for (CodingQuestion question : questions) {
            manager.addCodingQuestionToDB(question);
        }
        for (CodingQuestion question : questions2) {
            manager.addCodingQuestionToDB(question);
        }

        for (CodingAnswer answer : answers11) {
            manager.addCodingAnswerToDB(answer);
        }
        for (CodingAnswer answer : answers12) {
            manager.addCodingAnswerToDB(answer);
        }
        for (CodingAnswer answer : answers21) {
            manager.addCodingAnswerToDB(answer);
        }
        for (CodingAnswer answer : answers22) {
            manager.addCodingAnswerToDB(answer);
        }

    }

    @Test
    void test_get_coding_assignment_happy_path() {

        CodingQuestManager manager = CodingQuestManager.getInstance();
        CodingAssignment assignment = manager.getCodingAssignemntByID(1);

        assertEquals("Number one", assignment.getName());
    }

    @Test
    void test_get_coding_assignment_invalid_id() {

        CodingQuestManager manager = CodingQuestManager.getInstance();
        assertThrows(NullPointerException.class, () -> manager.getCodingAssignemntByID(0).getName());
    }

    @Test
    void test_get_coding_question_happy_path() {

        CodingQuestManager manager = CodingQuestManager.getInstance();
        CodingQuestion question = manager.getCodingQuestionByID(1);

        assertEquals("What?", question.toString());
    }

    @Test
    void test_get_coding_question_invalid_id() {

        CodingQuestManager manager = CodingQuestManager.getInstance();
        assertThrows(NullPointerException.class, () -> manager.getCodingQuestionByID(0).toString());
    }

    @Test
    void test_get_coding_answer_happy_path() {

        CodingQuestManager manager = CodingQuestManager.getInstance();
        CodingAnswer answer = manager.getCodingAnswerByID(1);

        assertEquals("This", answer.getAnswer());

    }

    @Test
    void test_get_coding_answer_invalid_id() {

        CodingQuestManager manager = CodingQuestManager.getInstance();
        assertThrows(NullPointerException.class, () -> manager.getCodingAnswerByID(0).toString());
    }

    @Test
    void test_get_all_coding_assignments_happy_path(){

        CodingQuestManager manager = CodingQuestManager.getInstance();
        assertEquals(2, manager.getAllCodingAssignments().size());
    }

    @Test
    void test_get_all_coding_questions_happy_path(){

        CodingQuestManager manager = CodingQuestManager.getInstance();
        assertEquals(4, manager.getAllCodingQuestions().size());
    }

}