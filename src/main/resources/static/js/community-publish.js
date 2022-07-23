function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if(previous.indexOf(value) == -1){
        if(previous){
            $("#tag").val(previous + ',' + value);
        }else{
            $("#tag").val(value);
        }
    }
}

$(function () {
    $("#tag").focus(function(){
        console.log("获取焦点")
        $(".publish-tag-tab").css("display","block");
    });
    $("#select-tag").blur(function(){
        console.log("失去焦点")
        $(".publish-tag-tab").css("display","none");
    });
})
