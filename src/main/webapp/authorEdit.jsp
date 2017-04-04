<%-- 
    Document   : authorEdit
    Created on : Feb 22, 2017, 12:11:06 PM
    Author     : aschindler1
--%>

<%@page import="edu.wctc.ams.bookwebapp.model.Book"%>
<%@page import="java.util.List"%>
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
        <h1>Update</h1>
        Edit the table below, if you don't want to, you can hit the back link below.
        <form id="formUpdate" name="formUpdate" method="POST" action="ListC?de=UPDATE">
            <table>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Date Added</th>
                    <th>Books Associated</th>
                </tr>
                <tr>
                    <td>${author.getAuthorID()} <input id="originalValue" name="originalValue" type="hidden" value="${author.getAuthorID()}"</td>
                    <td><input type="text" name="nameEdit" value="${author.getAuthorName()}"/></td>
                    <td>${author.getDateAdded()}</td>
                    <td>
                        <select name="books">
                            <c:forEach items="${author.getBookSet()}" var="i">
                                <option name="${i}">${i.getTitle()}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            <input id="SubmitButton" name="SubmitButton" type="submit" value="Update Entry!"><br><br>
            
            <a href="ListC?de=AUTHOR">Return To The List</a>       
        </form>
        
    </body>
</html>
