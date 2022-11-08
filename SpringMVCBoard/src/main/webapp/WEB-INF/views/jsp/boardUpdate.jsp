<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../jsp/includes/header.jsp"%>

<script src="/resources/js/reply.js"></script>
<script>

	// reply.js 테스트용
	$(function() {
		
		var bnoValue = "<c:out value='${boardVO.bno}' />";
		var replyUL = $(".chat");
		
		showList(1);
		
		function showList(page) {
			replyService.listReplyVO(
					{bno:bnoValue, page:page||1},
					function(replyCnt, list) {
						if (page == -1) {
							pageNum = Math.ceil(replyCnt/10.0);
							showList(pageNum);
							return;
						}
						var str = "";		
						
						if(list == null || list.length == 0){
							return ;
						}
						var listLength = list.length;
						for (var i=0, len=listLength; i<len; i++) {
				           str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
				           str +="  <div><div class='header'><strong class='primary-font'>["
				        	   +list[i].rno+"] "+list[i].replyer+"</strong>"; 
				           str +="    <small class='pull-right text-muted'>"
				               +replyService.displayTime(list[i].replydate)+"</small></div>";
				           str +="    <p>"+list[i].reply+"</p></div></li>";							
						}
						replyUL.html(str);
						showReplyPage(replyCnt);
					}
			);
		}
		
		/* modal */
	    var modal = $(".modal");
	    var modalInputReply = modal.find("input[name='reply']");
	    var modalInputReplyer = modal.find("input[name='replyer']");
	    var modalInputReplyDate = modal.find("input[name='replyDate']");
	    var modalModBtn = $("#modalModBtn");
	    var modalRemoveBtn = $("#modalRemoveBtn");
	    var modalRegisterBtn = $("#modalRegisterBtn");
	    
	    // 모달창 닫기 버튼
	    $("#modalCloseBtn").on("click", function(e){
	    	modal.modal('hide');
	    });
	    
	    // 댓글 추가 버튼
	    $("#addReplyBtn").on("click", function(e){
	        modal.find("input").val("");
	        modal.find("input[name='replyer']").val("temp_replyer");
	        modalInputReplyDate.closest("div").hide();
	        modal.find("button[id !='modalCloseBtn']").hide();
	        modalRegisterBtn.show();
	        $(".modal").modal("show");
	      });
	    
	    modalRegisterBtn.on("click",function(e){
	        var reply = {
	              reply: modalInputReply.val(),
	              replyer:modalInputReplyer.val(),
	              bno:bnoValue
	        };
	        replyService.insertReplyVO(
	        	reply,
	        	function(result){
	          		alert(result);
	          		modal.find("input").val("");
	          		modal.modal("hide");
	          		showList(1);
	        	}
	        );
		});
	    
	    // UL 클릭시 LI에 이벤트 전달(위임)
	    $(".chat").on("click", "li", function(){
	    	var rno = $(this).data("rno");
	    	replyService.selectReplyVO(rno, function(reply){
	            modalInputReply.val(reply.reply);
	            modalInputReplyer.val(reply.replyer);
	            modalInputReplyDate.val(replyService.displayTime( reply.replydate))
	            	.attr("readonly","readonly");
	            modal.data("rno", reply.rno);
	            modal.find("button[id !='modalCloseBtn']").hide();
	            modalModBtn.show();
	            modalRemoveBtn.show();
	            $(".modal").modal("show");	    		
	    	});
	    });
	    
	    // 댓글 수정 버튼 처리
	    modalModBtn.on("click", function(e){
	    	var reply = {
	    		      rno:modal.data("rno"), 
	    		      reply: modalInputReply.val(),
	    		      replyer: modalInputReplyer.val()
	    	};
	    	replyService.updateReplyVO(reply, function(result){
	  			alert(result);
	    		modal.modal("hide");
	    		showList(1);
	    	});
	    });	
	    
	    // 댓글 삭제 처리
	    modalRemoveBtn.on("click", function(e){
	    	var rno = modal.data("rno");
	    	replyService.deleteReplyVO(rno, function(result){
	  			alert(result);
	    		modal.modal("hide");
	    		showList(1);
	    	});
	    });	
	    
	    var pageNum = 1;
	    var replyPageFooter = $(".panel-footer");
	    
	    function showReplyPage(replyCnt){
	      
	      var endNum = Math.ceil(pageNum / 10.0) * 10;  
	      var startNum = endNum - 9; 
	      
	      var prev = startNum != 1;
	      var next = false;
	      
	      if(endNum * 10 >= replyCnt){
	        endNum = Math.ceil(replyCnt/10.0);
	      }
	      
	      if(endNum * 10 < replyCnt){
	        next = true;
	      }
	      
	      var str = "<ul class='pagination pull-right'>";
	      
	      if(prev){
	        str+= "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
	      }
	      
	       
	      
	      for(var i = startNum ; i <= endNum; i++){
	        
	        var active = pageNum == i? "active":"";
	        
	        str+= "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
	      }
	      
	      if(next){
	        str+= "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li>";
	      }
	      
	      str += "</ul></div>";
	      
	      console.log(str);
	      
	      replyPageFooter.html(str);
	    }
	     
	    replyPageFooter.on("click","li a", function(e){
	        e.preventDefault();
	        console.log("page click");
	        
	        var targetPageNum = $(this).attr("href");
	        
	        console.log("targetPageNum: " + targetPageNum);
	        
	        pageNum = targetPageNum;
	        
	        showList(pageNum);
	      });     
		
		// insert 테스트
		/*
		replyService.insertReplyVO(
				{reply:"JS Test", replyer:"tester", bno:bnoValue},
				function(result) {
					alert("result : " + result);
				}
		);
		*/

		// list 테스트
		/*
		replyService.listReplyVO(
				{bno:bnoValue, page:1},
				function(list) {
					var listLength = list.length;
					for (var i=0, len=listLength||0; i<len; i++) {
						console.log(list[i]);
					}
				}
		);
		 */

		// delete 테스트
		/*
		replyService.deleteReplyVO(
				9,
				function(count) {
					console.log(count);
				}
		);
		 */

		// update 테스트
		/*
		replyService.updateReplyVO(
				{rno:2, bno:bnoValue, reply:"수정된댓글", replyer:"수정한사람"},
				function(result) {
					alert(result);
				}
		);
		 

		// select 테스트
		replyService.selectReplyVO(6, function(data) {
			console.log(data);
		});
	    */

	});
	
</script>

<!-- Title -->
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Update</h1>
	</div>
</div>

<!-- Board -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Update</div>
			<div class="panel-body">
				<form id="updateForm" role="form" action="/board/updateProc"
					method="post">
					<input type='hidden' name='pageNum'
						value='<c:out value="${cri.pageNum }"/>'> <input
						type='hidden' name='amount'
						value='<c:out value="${cri.amount }"/>'> <input
						type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
					<input type='hidden' name='keyword'
						value='<c:out value="${cri.keyword }"/>'> <input
						type="hidden" name="mappingURI" value="update" />
					<div class="form-group">
						<label>Bno</label> <input class="form-control" name='bno'
							value='<c:out value="${boardVO.bno }"/>' readonly="readonly">
					</div>

					<div class="form-group">
						<label>Title</label> <input class="form-control" name='title'
							value='<c:out value="${boardVO.title }"/>'>
					</div>

					<div class="form-group">
						<label>Text area</label>
						<textarea class="form-control" rows="3" name='content'><c:out
								value="${boardVO.content}" /></textarea>
					</div>

					<div class="form-group">
						<label>Writer</label> <input class="form-control" name='writer'
							value='<c:out value="${boardVO.writer}"/>' readonly="readonly">
					</div>

					<div class="form-group">
						<label>RegDate</label> <input class="form-control" name='regDate'
							value='<fmt:formatDate pattern = "yyyy/MM/dd" value = "${boardVO.regdate}" />'
							readonly="readonly">
					</div>

					<div class="form-group">
						<label>Update Date</label> <input class="form-control"
							name='updateDate'
							value='<fmt:formatDate pattern = "yyyy/MM/dd" value = "${boardVO.updateDate}" />'
							readonly="readonly">
					</div>
					<button id="updateBtn" class="btn btn-default">Update</button>
					<button id="deleteBtn" bno="${boardVO.bno }" class="btn btn-danger">Delete</button>
					<button id="listBtn" pagenum="${param.pageNum}"
						class="btn btn-info">List</button>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- Reply -->
<div class='row'>
	<div class="col-lg-12">
		<div class="panel panel-default">
        <i class="fa fa-comments fa-fw"></i> Reply
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> Reply
				<sec:authorize access="isAuthenticated()">
					<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New
						Reply</button>
				</sec:authorize>
			</div>
			<div class="panel-body">
				<ul class="chat">
				</ul>
			</div>
			<div class="panel-footer"></div>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label> <input class="form-control" name='reply'
						value='New Reply!!!!'>
				</div>
				<div class="form-group">
					<label>Replyer</label> <input class="form-control" name='replyer'
						value='replyer'>
				</div>
				<div class="form-group">
					<label>Reply Date</label> <input class="form-control"
						name='replyDate' value='2018-01-01 13:13'>
				</div>
			</div>
			<div class="modal-footer">
				<button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
				<button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
				<button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
				<button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
			</div>
		</div>
	</div>
</div>

<%@include file="../jsp/includes/footer.jsp"%>





