<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<html>
<form id="actionForm" action="/board/list" method="get">
   <input type="hidden" name="pageNum" value="${!empty param.pageNum ? param.pageNum : '1'}" />
   <input type='hidden' name='bno' value="${param.bno}" />
   <input type='hidden' name='type' value='<c:out value="${pageCalc.criteria.type }"/>'>
   <input type='hidden' name='keyword' value='<c:out value="${pageCalc.criteria.keyword }"/>'>
</form>
</body>

</html>