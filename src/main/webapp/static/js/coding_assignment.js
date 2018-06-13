function take_assignment_listener() {
    let take_asssignment_button = document.getElementById("take-assignment-button");
    take_asssignment_button.addEventListener("click", function () {
        let assignment_start = document.getElementById("assignment-start");
        assignment_start.style.visibility = "hidden";
        let question_list = document.getElementById("question-list");
        question_list.style.visibility = "visible";

        let assignment_id = document.getElementById("assignment_id").getAttribute("data-assignment_id");
        get_next_question(0, assignment_id);
    })
}

function submit_question_listener() {
    let coding_questions = document.getElementsByClassName("submit-question-button");
    for (let current_question of coding_questions) {
        current_question.addEventListener("click", function () {
            get_next_question(2, 1);
        })
    }
}

function get_next_question(question_id, assignment_id) {
    let question_data;

    $.ajax({
        type: "POST",
        url: "/coding-assignment",
        data: {"question_id": question_id, "assignment_id": assignment_id},
        success: function (response) {
            console.log("success");
            if (response === "Last question") {
                console.log("last question");
            } else {
                console.log("next question");
                console.log(response);
                let coding_question_id = document.getElementById("question_id");
                coding_question_id.setAttribute("data-question_id", response.question_id);

                let question_parts = response.question_text.split("$");
                let answer_ids = response.answer_ids;
                let coding_question_body = document.getElementById("question_holder");

                let html_string =
                    "<div class='question_part'>" +
                    question_parts[0] +
                    "</div>";
                for (let i = 0; i < answer_ids.length; i++) {
                    html_string +=
                        "<input class='answer_part' type='text' id='" + answer_ids[i] + "'>" +
                        "<div class='question_part'>" +
                        question_parts[i + 1] +
                        "</div>"
                }

                coding_question_body.innerHTML = html_string;

            }
        },
    });
}

take_assignment_listener();
submit_question_listener();