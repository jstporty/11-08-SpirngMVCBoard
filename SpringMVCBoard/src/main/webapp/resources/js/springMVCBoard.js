$(function() {

	// form 안의 button은 기본적으로 submit이벤트를 발생시킨다.
	// 기본이벤트를 방지해야한다.
	$("button").click(function(event) {
		event.preventDefault();
	});
	
	// 등록버튼 클릭시 등록화면으로 이동
	$("#regBtn").click(function(event) {
		location.href = "/board/boardInsert";
	});
	
	// 수정버튼 클릭시 수정 처리
	$("#updateBtn").click(function(event){
		if(confirm("정말 수정하실건가요?")) {
			$("#updateForm").submit();
		} else {
			return false;
		}	
	});
	
	// 삭제버튼 클릭시 삭제 처리
	$("#deleteBtn").click(function(event){
		if(confirm("정말 삭제하실건가요?")) {
			location.href = "/board/delete?bno="+$(this).attr("bno");
		} else {
			return false;
		}
	});
	
	// 목록페이지에서 제목 클릭시 조회페이지로 이동
	addClickListener(".selectLink", "selectFromList", "/board/select");
	
	// 목록페이지에서 페이지번호 클릭시 해당페이지로 이동
	addClickListener(".pageNumLink", "listFromPaging", "/board/list"); 
	
	// 조회페이지에서 목록버튼 클릭시 목록페이지로 이동
	addClickListener("#listBtn",  "listFromSelect", "/board/list");
	
	// 목록페이지에서 검색버튼 클릭시 목록페이지로 이동
	addClickListener("#searchBtn", "listFromSearch", "/board/list");
	
	// 입력폼 서밋/리셋
	$(".boardInsertBtns").click(function() {
		if ($(this).attr("id")=="boardInsertSubmitBtn") {
			$("#boardInsertForm").submit();
		} else if ($(this).attr("id")=="boardInsertResetBtn") {
			document.forms["boardInsertForm"].reset();
		}
	});
	
}); // $(function(){})


// 이벤트타겟엘리먼트, 명령, 이동할경로
function addClickListener(element, command, action) {
	$(element).click(function(event){
		event.preventDefault();
		$("input[name='pageNum']").val($(this).attr("pageNum"));
		$("input[name='bno']").val($(this).attr("bno"));
		$("input[name='type']").val($("select[name='type']").val());
		$("input[name='keyword']").val($("input[name='keyword']").val());
		if (command=="listFromSearch") {
			$("input[name='pageNum']").val($("input[name='pn']").val());
		}
		$("#actionForm").attr("action", action);
		$("#actionForm").submit();
	});
}








