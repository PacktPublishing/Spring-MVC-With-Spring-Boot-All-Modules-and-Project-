<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
 <div>
  
 </div>
 <br>
 <div class="panel panel-primary">
  <div class="panel-heading">
   <h3>List of TODO's</h3>
  </div>
  <div class="panel-body">
   <table class="table table-striped">
    <thead>
     <tr>
      <th width="40%">Username</th>
      <th width="40%">Delete</th>
      <th width="20%"></th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${users}" var="user">
      <tr>
       <td>${user.username}</td>
       <td>
       <a type="button" class="btn btn-warning"
        href="/delete-user?id=${user.id}">Delete</a></td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </div>
 </div>

</div>
<%@ include file="common/footer.jspf"%>