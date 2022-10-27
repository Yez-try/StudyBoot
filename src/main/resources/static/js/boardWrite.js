console.log("boardWrite.js")


let count=0
//파일추가 생성하기
$("#fileAddBtn").click(function(){
    if(count<5){
        let elmtFile = '<div id="file"'+count+'>'+
        '<label for="files">파일</label><input type="file" id="files" name="files"><button type="button" class="delFileDiv">버튼삭제</button>'
        +'</div>'

        $("#divFiles").append(elmtFile);
        count++;

    }else{
        alert("최대 5개까지 가능합니다")
    }
})

//파일 삭제하기
$("#divFiles").on("click",".delFileDiv", function(event){
    console.log("삭제", event)
    console.log(event.target)
    console.log($(event.target))
    $(event.target).parent().remove()

    count--;
})