function commentPost() {
    var questionId = $("#question_id").val();
    var content = $("#comment-content").val();
    // console.log(questionId);
    // console.log(content);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/comment",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function(reponse){
            if(reponse.code == 200){
                $("#comment-section").hide()
            }else{
                alert(reponse.message);
            }
            console.log(reponse);
        },
        dataType: "json"
    });
}