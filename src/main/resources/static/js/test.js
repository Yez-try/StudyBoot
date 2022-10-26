console.log("start");

// =====이벤트 등록==========

// - Event 이름은 JS와 동일

// js -> const a = document.getElementById("a")
// 	    a.addEventListener("click", callback function)

// jquery
// 	1. $("선택자").이벤트명(callback function)
// 			$("#a").click(callback function)
// 	2. $("선택자").on("이벤트명", callback function)

// 	3. 하나의 선택자에 여러개의 이벤트 등록
// 		 $("선택자").on({
// 				이벤트명1:callback function,
// 				이벤트명2:callback function,
// 				...
// 				이벤트명2:callback function
// 		})	

// 	4. 이벤트 위임(이벤트 전달)
// 		$("부모선택자").on("이벤트명", "자식선택자", callback function)


$("#btn").click(function(){
    console.log("click")
});

$(".btns").click(function(){
    console.log("buttons")
})

// 이벤트 전달(위임)하기
// test 엘리먼트의 btn2에 클릭이벤트를 전달해라
$("#test").on("click", "#btn2", function(){

})


// 전체 객체 window
// url관리 location
// 뒤로가기 앞으로가기 history
// 내용 관리 document