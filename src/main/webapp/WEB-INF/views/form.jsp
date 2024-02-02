<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>Form</title>
  </head>
  <body>
      <a href="/">Add Expense</a> &nbsp&nbsp&nbsp&nbsp&nbsp
      <a href="/expenses">Expense List</a>

      <h2> Expense Form </h2>
      <form:form method="post" modelAttribute="expense" action="/handleSubmit">
      <form:hidden path="id"/>
        <h5>Description :
        <form:input placeholder="Description" path="description" /> <form:errors path="description" /> <br>
        </h5><br>

        <h5>Amount :
        <form:input type ="number" step="1" placeholder="Amount" path="amount" /> <form:errors path="amount" /> <br>
          </h5><br>

        <h5>Month :
            <form:input type ="number" step="1" placeholder="Month" path="month" /> <form:errors path="month" /> <br>
          </h5><br>

        <h5>Year :
          <form:input type ="number" step="1" placeholder="Year" path="year" /> <form:errors path="year" /> <br>
          </h5><br>
        <br><br>
        <input type="submit" value="Submit">
        </form:form>
    </div>
</body>
</html>