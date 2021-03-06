function init() {
    document.getElementById("question-list").style.display = "block";
    document.getElementById("coding-question").style.display = "block";
    document.getElementById("submit_question_button").style.display = "block";
    document.getElementById("submit_error_message").style.display = "none";
    document.getElementById("assignment-finished").style.display = "none";
    let assignment_id = document.getElementById("assignment_id").getAttribute("data-assignment_id");
    get_next_question(0, assignment_id);
}

function submit_question_listener() {
    let submit_answer_button = document.getElementById("submit_question_button");
    submit_answer_button.addEventListener("click", function () {

        if (!is_every_answer_filled()) {
            let submit_error = document.getElementById("submit_error_message");
            submit_error.innerHTML = "Fill out all input fields to submit your answer!";
            submit_error.style.display = "block";
            return;
        }
        document.getElementById("submit_error_message").style.display = "none";

        let question_id = document.getElementById("question_id").getAttribute("data-question_id");
        let assignment_id = document.getElementById("assignment_id").getAttribute("data-assignment_id");

        let answers = document.getElementsByClassName("answer_part");
        let answer_texts = [];
        for (answer of answers) {
            answer_texts.push(answer.value);
        }

        submit_answer(assignment_id, question_id, answer_texts);
    });
}

function submit_answer(assignment_id, question_id, answers) {
    $.ajax({
        type: "POST",
        url: "/coding-answer",
        data: {
            "question_id": question_id,
            "assignment_id": assignment_id,
            "answers": answers
        },
        success: function (response) {
            console.log(response);
            if (response.correct_answer) {
                fight.playSuccefulAttack(function () {
                    get_next_question(question_id, assignment_id);
                });
            } else {
                if (response.death){
                    fight.playUnsuccessfulAttackWithDeath(function () {
                        create_and_show_html_failed_assignment();
                    });
                } else {
                    fight.playUnsuccessfulAttack(function () {
                        get_next_question(question_id, assignment_id);
                    });
                }
            }
        },
    });
}

function get_next_question(question_id, assignment_id) {
    $.ajax({
        type: "POST",
        url: "/coding-assignment",
        data: {"question_id": question_id, "assignment_id": assignment_id},
        success: function (response) {
            if (response.points_achieved != null) {
                fight.playWin(function () {
                    let max_points = response.max_points;
                    let points_achieved = response.points_achieved;
                    create_and_show_html_completed_assignment(max_points, points_achieved);
                })
            } else {
                let coding_question_id = document.getElementById("question_id");
                coding_question_id.setAttribute("data-question_id", response.question_id);

                let question_parts = response.question_text.split("$");
                let answer_ids = response.answer_ids;
                set_html_next_question(question_parts, answer_ids);
            }
        },
    });
}

function is_every_answer_filled() {
    let answer_parts = document.getElementsByClassName("answer_part");
    for (answer of answer_parts) {
        if (answer.value === "") {
            return false;
        }
    }
    return true;
}

function create_and_show_html_failed_assignment() {
    document.getElementById("question-list").style.display = "none";
    let finished_template = document.getElementById("assignment-finished");
    finished_template.innerHTML = "<div id='assignment_finished_text'>You have failed this assignment!</div>";
    document.getElementById("assignment-finished").style.display = "block";
}

function create_and_show_html_completed_assignment(max_points, points_achieved) {
    document.getElementById("question-list").style.display = "none";
    let score = document.getElementById("assignment-score");
    score.innerHTML = "You have scored " +
        points_achieved + " / " + max_points +
        " points on this assignment.";
    document.getElementById("assignment-finished").style.display = "block";
}

function set_html_next_question(question_parts, answer_ids) {
    let coding_question_body = document.getElementById("question_holder");

    let html_string =
        "<div class='question_part inline-div'>" +
        question_parts[0] +
        "</div>";
    for (let i = 0; i < answer_ids.length; i++) {
        html_string +=
            "<input class='answer_part lots-input-field' type='text' id='" + answer_ids[i] + "'>" +
            "<div class='question_part inline-div'>" +
            question_parts[i + 1] +
            "</div>"
    }
    coding_question_body.innerHTML = html_string;
}

init();
submit_question_listener();