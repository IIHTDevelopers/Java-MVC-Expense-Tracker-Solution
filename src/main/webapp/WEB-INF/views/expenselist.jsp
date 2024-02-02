<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE>
<html>
  <head> 
    <title>Expense Tracker</title>
  </head>
  <body>
      <a href="/">Add Expense</a>&nbsp&nbsp&nbsp&nbsp&nbsp
      <a href="/expenses">Expense List</a>

      <h2 > Expenses </h2>
             <h2> Search Expense </h2>
                  <form:form action="/search?page=0&size=5" method="POST">
    			    <input type="text" placeholder="Search By Desc" name="theSearchName" value = "${theSearchName}">
                    <select name="searchMonth" placeholder="Select Month">
                       <c:forEach var="month" items="${months}">
                             <option value="${month}" ${month == searchMonth ? 'selected="selected"' : ''}>${month}</option>
                       </c:forEach>
                    </select>
                    <input type="number" placeholder="Search By Year" name="theSearchYear" step = "1" value = "${theSearchYear}">
                     <input type="submit" value="Search"/>
      </form:form>


      <table id="table" border="1">
        <tr>
          <th>Sno. </th>
          <th>Month
	        &nbsp &nbsp <a href= "/expenses?page=0&size=5&theSearchName=${theSearchName}&searchMonth=${searchGrade}&theSearchYear=${theSearchYear}&sort=month,desc"> Desc </a>
            &nbsp &nbsp <a href= "/expenses?page=0&size=5&theSearchName=${theSearchName}&searchMonth=${searchGrade}&theSearchYear=${theSearchYear}&sort=month"> Asc </a>
          </th>
          <th>Year
	        &nbsp &nbsp <a href= "/expenses?page=0&size=5&theSearchName=${theSearchName}&searchMonth=${searchGrade}&theSearchYear=${theSearchYear}&sort=year,desc"> Desc </a>
            &nbsp &nbsp <a href= "/expenses?page=0&size=5&theSearchName=${theSearchName}&searchMonth=${searchGrade}&theSearchYear=${theSearchYear}&sort=year"> Asc </a>
          </th>
          <th>Amount
	        &nbsp &nbsp <a href= "/expenses?page=0&size=5&theSearchName=${theSearchName}&searchMonth=${searchGrade}&theSearchYear=${theSearchYear}&sort=amount,desc"> Desc </a>
            &nbsp &nbsp <a href= "/expenses?page=0&size=5&theSearchName=${theSearchName}&searchMonth=${searchGrade}&theSearchYear=${theSearchYear}&sort=amount"> Asc </a>
          </th>
          <th>Description
	        &nbsp &nbsp <a href= "/expenses?page=0&size=5&theSearchName=${theSearchName}&searchMonth=${searchGrade}&theSearchYear=${theSearchYear}&sort=description,desc"> Desc </a>
            &nbsp &nbsp <a href= "/expenses?page=0&size=5&theSearchName=${theSearchName}&searchMonth=${searchGrade}&theSearchYear=${theSearchYear}&sort=description"> Asc </a>
          </th>
        </tr>

        <c:set var="index" value="${page * 5 + 1}" />

        <c:forEach var="expense" items="${expenses}">
            <tr>
            <td>${index}</td>
          <td>${expense.month}</td>
          <td>${expense.year}</td>
          <td>${expense.amount}</td>
          <td>${expense.description}</td>
          <td><a href="/?id=${expense.id}">Update</a> </td>
          <td><a href="/delete/${expense.id}">Delete</a> </td>
          <c:set var="index" value="${index + 1}" />
        </tr
        </c:forEach>
      </table>

    <br><br>
    Total Expenses : ${totalExpense}
	<br><br>
        	<c:choose>
                <c:when test="${totalPage == 0}">
                    No Record Found
                </c:when>
                <c:otherwise>
                    <c:forEach begin="0" end="${totalPage-1}" varStatus="loop">
                            &nbsp &nbsp<a href="/expenses?page=${loop.index}&size=5&theSearchName=${theSearchName}&searchMonth=${searchMonth}&theSearchYear=${theSearchYear}&sort=${sortBy}">${loop.index + 1}</a></li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>


  </body>
</html>