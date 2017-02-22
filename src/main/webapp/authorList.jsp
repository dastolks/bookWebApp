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
        if you made it here, it worked. <c:out value="${authorList.size()}" /> entries are located here<br><br>
        <c:set var="selected" value="-1" />
        asdgsd
        <form id="form1" name="form1" method="POST" action="ListC?de=EDIT_DELETE">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Date Added</th>
                </tr>
                
                <c:forEach var="author" items="authorList">
                    <tr>
                        <td>${author.authorId} <input type="radio" name="authorIdBtn" value="${author.authorId}"> </td>   
                        <td>${author.authorName}</td>
                        <td>${author.dateAdded}</td>  
                    </tr>
                </c:forEach>
            </table>
            <input id="submitForm" name="submitForm" type="submit" value="Edit Author">
            <input id="submitFormDelete" name="submitFormDelete" type="submit" value="Delete Author">
        </form>
    </body>
</html>
