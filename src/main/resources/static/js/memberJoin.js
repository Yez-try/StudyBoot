console.log("memberJoin.js")


//==element 의 GET==
// 1. Text Node 반환
// 		$("선택자").text();
// 	 js - innerText

// 2. HTML Node 반환
// 	 $("선택자").html();
// 	 js - innerHTML	

// 3. Attribute 반환
// 	 js - 선택자.속성명, 선택자.getAttribute()

// 	 $("선택자").attr("속성명")
// 	 - id, title, data-*** 등 상태가 변해도 값이 변하지 않는 속성

// 	 $("선택자").prop("속성명")
// 	 - selected, checked 등 상태가 변할 때 값도 변하는 속성

// 4. input 태그의 value 반환
// 	 js - 선택자.value
// 	 $("선택자").val()


//==element 의 SET==

// 1. Text node
// 	 $("선택자").text("값")

// 2. HTML node
// 	 $("선택자").html("html code")

// 3. Attribute
// 	 $("선택자").attr("속성명", "값")
// 	 $("선택자").prop("속성명", "값")

// 4. input 태그의 value
// 	 $("선택자").val("값")	

// 자기 자신을 뜻하는 this를 사용할 수 있다.
$("#all").click(function(c){
    let ch = $(this).prop("checked")
    console.log("ch: ",ch)
    // console.log(c) : 매개변수를 주면 이벤트가 전달된다.

    // all을 클릭하면 check의 checked element에 똑같이 check값을 줘라
    $(".check").prop("checked", ch) 
})

/* Jquery 반복문
1. for(let i=0;i<..;i++){}
2. for in
3. for of

4. jquery 반복문
	 - each()
	 $("선택자").each(function(index, item){
				-- index = index번호
				-- item  = 배열에서 꺼내온 Element
		})

	let ar = [1,2,3]
	let ar2 = [3,4,5]
    배열 반복문은 $ 로 씀. 배열이 두개일때는 어떻게 구분하지? 배열명을 써준다.
	$.each(배열명, (function(index, item){
				-- index = index번호
				-- item  = 배열에서 꺼내온 Element
	})

*/


$(".check").click(function(){
    let ch = $(this).prop("checked")
    console.log(this);

    // 체크값이 true면 false가 있는지 체크한다
    if(ch){
        $(".check").each(function(idx, item){
            //체크값이 false면 false를 저장해라
            ch = $(item).prop("checked")
            console.log("ch: ", ch);
            return ch; // ch가 false면 반복문 종료
        })
    }

    //ch값을 all 에 set
    $("#all").prop("checked", ch)
})

//회원가입 input값을 전부 입력해야 form.submit하도록
$(".valid").blur(function(){

})



let results = [false, false, false, false]

$("#joinButton").click(function(){

    console.log($("#id").val().length)
    let val = true

    $(".valid").each(function(idx, item){
        if($(item).val().length>2){
            // val = true
            results[idx]= true
            console.log(results)
        }else{
            // val = false
            // return false
        }
    })

    if(results.includes(false)){
        val = false
        console.log(val)
    }

    if(val){
    //evnet 강제 실행
    $("#joinForm").submit()
    }
})



//Element 추가
// - 기존의 내용에 추가
// 1. $("선택자").append("추가할 HTML Code") : 선택 요소 내에 마지막에 추가
// 2.$("선택자").prepend("추가할 HTML Code") : 선택 요소 내에 맨위에 추가
let count=3;
$("#s1Add").click(function(){
    console.log("add")
    let add = '<option class="abc" id="abc"'+count+'>'+count+'</option>'
    $("#s1").append(add);
    count++;
})

// 3. $("선택자").after("추가할 HTML Code")   : 선택요소 다음 추가
// 3. $("선택자").before("추가할 HTML Code")  : 선택요소 이전 추가
$("#before").click(function(){
    let add = '<label>클릭하면 늘어나요</label>'
    $("#s1").before(add);
})

// 1. $("선택자").remove()   : 선택자 포함, 하위 자식들 까지 모두 삭제
// 2. $("선택자").empty()    : 선택자 제외, 자식들 삭제
$("#del").click(function(){
    $("#s1").remove();
})



// 아이디 중복체크를 해보자
$("#idChkBtn").click(function(){
    let idVal = $("#id").val()

    //jQuery
    //응답으로 온 데이터가 내가 쓴 변수에 담긴다
    $.get("/member/idCheck?id="+idVal, function(data){
        console.log("Data:", data)

        if(data=='1'){
            alert("중복된 아이디 값입니다")
            $("#id").val("")
        }else{
            alert("사용할 수 있는 아이디입니다.")
        }
    });
    // const xttp = new XMLHttpRequest();
    // xttp.open("GET", "/member/idCheck?id="+idVal);
    // xttp.send();
    // xttp.addEventListener("readystatechange", function(){
    //     if(this.readyState==4&&this.status==200){
    //         if(xttp.responseText=='1'){
    //             alert("중복된 아이디 값입니다")
    //             $("#id").val("")
    //         }else{
    //             alert("사용할 수 있는 아이디입니다.")
    //         }
    //     }
    // })
})





$("#test").click(function(){
    let id="123"
    let name = "iu"

    $.post("test", {
        id:id,
        name:name
    }, function(result){
        console.log("Result : ", result) //json형태로 온다
        console.log("Name : ", result.name)
    })

})

$("#test2").click(function(){
    let id="abcd";
    $.ajax({
        type:"GET",
        url:"idCheck",
        data:{
            id:id
        },
        success:function(data){
            console.log("Data :", data);
        },
        error:function(xhr, status, error){
            console.log("xhr은 XMLHttpRequest객체   | status는 상태 404 500같은 | error")
            console.log("xhr : ", xhr)
            console.log("status :",status)
            console.log("error: ", error)
        }
    })
})

$("#test3").click(function(){
    let id="1234"
    let name="iu"
    let ar = [1,2,3] //이런값은 어떻게 넘기지????????? traditional값을 true로 준다
    $.ajax({
        type:"POST",
        url:"test",
        traditional:true, //배열을 전송할 때 사용(파라미터 이름 하나로 여러개를 보내야 할때)
        data:{
            id:id,
            name:name,
            ar:ar
        },
        success:function(result){
            console.log("result: ", result);
        }
    })
})

