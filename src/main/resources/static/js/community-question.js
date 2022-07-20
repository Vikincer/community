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
                if(reponse.code == 2003){
                    var isAccepted = confirm(reponse.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=e3922d21609fb22bdc10&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(reponse.message);
                }
            }
            console.log(reponse);
        },
        dataType: "json"
    });
}