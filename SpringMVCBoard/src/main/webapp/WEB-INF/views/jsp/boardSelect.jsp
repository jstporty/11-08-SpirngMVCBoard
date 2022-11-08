<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../jsp/includes/header.jsp"%>
<div class="row">
   <div class="col-lg-12">
      <h1 class="page-header">Tables</h1>
   </div>
</div>

<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-heading">
            Board List Page
            <button id='regBtn' type="button" class="btn btn-xs pull-right">Register
               New Board</button>
         </div>

         <div class="panel-body">
            <table class="table table-striped table-bordered table-hover">
               <thead>
                  <tr>
                     <th>#번호</th>
                     <th>제목</th>
                     <th>작성자</th>
                     <th>작성일</th>
                     <th>수정일</th>
                  </tr>
               </thead>

               <c:forEach items="${boardVOList}" var="boardVO">
                  <tr>
                     <td><c:out value="${boardVO.bno}" /></td>
                     <%-- <td><a href='/board/get?bno=<c:out value="${board.bno}"/>'><c:out value="${board.title}"/></a></td> --%>

                     <td>
                     <a class='move' href="${boardVO.bno}">
                           <c:out value="${boardVO.title}" />
                     </a>
                     </td>

                     <td><c:out value="${boardVO.writer}" /></td>
                     <td><fmt:formatDate pattern="yyyy-MM-dd"
                           value="${boardVO.regdate}" /></td>
                     <td><fmt:formatDate pattern="yyyy-MM-dd"
                           value="${boardVO.updateDate}" /></td>
                  </tr>
               </c:forEach>
            </table>

<!--             <div class='row'> -->
<!--                <div class="col-lg-12"> -->

<!--                   <form id='searchForm' action="/board/list" method='get'> -->
<!--                      <select name='type'> -->
<!--                         <option value="" -->
<%--                            <c:out value="${pageMaker.criteria.type == null?'selected':''}"/>>--</option> --%>
<!--                         <option value="T" -->
<%--                            <c:out value="${pageMaker.criteria.type eq 'T'?'selected':''}"/>>제목</option> --%>
<!--                         <option value="C" -->
<%--                            <c:out value="${pageMaker.criteria.type eq 'C'?'selected':''}"/>>내용</option> --%>
<!--                         <option value="W" -->
<%--                            <c:out value="${pageMaker.criteria.type eq 'W'?'selected':''}"/>>작성자</option> --%>
<!--                         <option value="TC" -->
<%--                            <c:out value="${pageMaker.criteria.type eq 'TC'?'selected':''}"/>>제목 --%>
<!--                            or 내용</option> -->
<!--                         <option value="TW" -->
<%--                            <c:out value="${pageMaker.criteria.type eq 'TW'?'selected':''}"/>>제목 --%>
<!--                            or 작성자</option> -->
<!--                         <option value="TWC" -->
<%--                            <c:out value="${pageMaker.criteria.type eq 'TWC'?'selected':''}"/>>제목 --%>
<!--                            or 내용 or 작성자</option> -->
<!--                      </select> <input type='text' name='keyword' -->
<%--                         value='<c:out value="${pageMaker.criteria.keyword}"/>' /> <input --%>
<!--                         type='hidden' name='pageNum' -->
<%--                         value='<c:out value="${pageMaker.criteria.pageNum}"/>' /> <input --%>
<!--                         type='hidden' name='amount' -->
<%--                         value='<c:out value="${pageMaker.criteria.amount}"/>' /> --%>
<!--                      <button class='btn btn-default'>Search</button> -->
<!--                   </form> -->
<!--                </div> -->
<!--             </div> -->


            <div class='pull-right'>
               <ul class="pagination">
                  <c:if test="${pageCalc.prev}">
                     <li class="paginate_button previous">
                     <a    href="${pageCalc.startPage -1}">Previous</a></li>
                  </c:if>

                  <c:forEach var="num" begin="${pageCalc.startPage}"
                     end="${pageCalc.endPage}">
                     <li
                        class="paginate_button  ${pageCalc.criteria.pageNum == num ? 'active':''} ">
                        <a class="pageNumLink" pagenum ="${num}">${num}</a>
                     </li>
                  </c:forEach>

                  <c:if test="${pageCalc.next}">
                     <li class="paginate_button next">
                     <a href="${pageCalc.endPage +1 }">Next</a></li>
                  </c:if>


               </ul>
            </div>
         </div>

         <form id='actionForm' method='get'>
            <input type="hidden" name="action" value="${action}"/>
            <input type='hidden' name='pageNum' value='${pageCalc.criteria.pageNum}'>
            <input type="hidden" name="bno" value="" />
<!--             <input type='hidden' name='type' -->
<%--                value='<c:out value="${ pageMaker.criteria.type }"/>'> <input --%>
<!--                type='hidden' name='keyword' -->
<%--                value='<c:out value="${ pageMaker.criteria.keyword }"/>'> --%>
         </form>

         <!-- Modal  추가 -->
         <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
            aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
               <div class="modal-content">
                  <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                     <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                  </div>
                  <div class="modal-body">처리가 완료되었습니다.</div>
                  <div class="modal-footer">
                     <button type="button" class="btn btn-default"
                        data-dismiss="modal">Close</button>
                     <button type="button" class="btn btn-primary">Save
                        changes</button>
                  </div>
               </div>
            </div>
         </div>


      </div>
   </div>
</div>







<!-- <script type="text/javascript">
   $(document)
         .ready(
               function() {
                  var result = '<c:out value="${result}"/>';
                  checkModal(result);
                  history.replaceState({}, null, null);
                  function checkModal(result) {
                     if (result === '' || history.state) {
                        return;
                     }
                     if (parseInt(result) > 0) {
                        $(".modal-body").html(
                              "게시글 " + parseInt(result)
                                    + " 번이 등록되었습니다.");
                     }
                     $("#myModal").modal("show");
                  }
                  $("#regBtn").on("click", function() {
                     self.location = "/board/boardInsert";
                  });
                  var actionForm = $("#actionForm");
                  $(".paginate_button a").on(
                        "click",
                        function(e) {
                           e.preventDefault();
                           console.log('click');
                           actionForm.find("input[name='pageNum']")
                                 .val($(this).attr("href"));
                           actionForm.submit();
                        });
                  $(".move")
                        .on(
                              "click",
                              function(e) {

                                 e.preventDefault();
                                 actionForm
                                       .append("<input type='hidden' name='bno' value='"
                                             + $(this).attr(
                                                   "href")
                                             + "'>");
                                 actionForm.attr("action",
                                       "/board/select");
                                 actionForm.submit();
                              });

                  var searchForm = $("#searchForm");

                  $("#searchForm button").on(
                        "click",
                        function(e) {

                           if (!searchForm.find("option:selected")
                                 .val()) {
                              alert("검색종류를 선택하세요");
                              return false;
                           }

                           if (!searchForm.find(
                                 "input[name='keyword']").val()) {
                              alert("키워드를 입력하세요");
                              return false;
                           }

                           searchForm.find("input[name='pageNum']")
                                 .val("1");
                           e.preventDefault();

                           searchForm.submit();

                        });

               });
</script> -->






<%@include file="../jsp/includes/footer.jsp"%>