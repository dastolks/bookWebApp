<%-- 
    Document   : authorList
    Created on : Feb 6, 2017, 3:06:56 PM
    Author     : Alec
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="StyleSheet" href="setcss.css">
    </head>
    <body>
        <c:out value="${bookList.size()}" /> entries are located here.<br><br>
        <c:set var="selected" value="-1" />
        <form id="form1" name="form1" method="POST" action="BookC?de=EDIT_DELETE_CREATE">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>ISBN</th>
                    <th>Author</th>
                </tr>
                
                <c:forEach var="i" begin="0" end="${bookList.size()-1}">
                    <tr>
                        <td>${bookList.get(i).getBookId()} <input type="radio" name="bookIdBtn" value="${bookList.get(i).getBookId()}"> </td>   
                        <td>${bookList.get(i).getTitle()}</td>
                        <td>${bookList.get(i).getIsbn()}</td>  
                        <td>${bookList.get(i).getAuthorID().getAuthorName()}</td> 
                    </tr>
                </c:forEach>
            </table><br><br>
            <input id="submitForm" name="submitForm" type="submit" value="Edit Book">
            <input id="submitFormDelete" name="submitFormDelete" type="submit" value="Delete Book"> 
            <input id="submitFormAdd" name="submitFormAdd" type="submit" value="Add Book">
        </form>
        <br><br>
        <a href="index.jsp">Click Here</a> to return to the index.
    </body>
</html>
