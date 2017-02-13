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
    </head>
    <body>
        if you made it here, it worked. <c:out value="${authorList.size()}" /> entries are located here<br><br>
        <table text-align="center">
            <tr>
                <td>ID</td>
                <td>Name</td>
                <td>Date Added</td>
            </tr>
            <c:forEach var="i" begin="0" end="${authorList.size()-1}">
                <tr>
                    <td>${authorList.get(i).getAuthorId()}</td>   
                    <td>${authorList.get(i).getAuthorName()}</td>
                    <td>${authorList.get(i).getDateAdded()}</td>  
                </tr>
            </c:forEach>
            
        </table>
    </body>
</html>
