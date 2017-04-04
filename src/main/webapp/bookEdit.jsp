<%-- 
    Document   : bookEdit
    Created on : Apr 3, 2017, 11:44:18 AM
    Author     : aschindler1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Update!</h1>
        <form id="goodForm" name="goodForm" method="POST" action="BookC?de=ADD">
            <table>
                <tr>
                    <th>Title</th>
                    <th>ISBN</th>
                    <th>Author</th>
                </tr>
                <tr>
                    <td><input type="text" name="titleEdit" value="${book.getTitle()}"/></td>
                    <td><input type="text" name="isbnEdit" value="${book.getIsbn()}"/></td>
                    <td><input type="text" name="authorEdit" value=""/></td>
                </tr>
            </table>
            Hit the button below to submit it<br><br>
            <input id="SubmitButton" name="SubmitButton" type="submit" value="Add To The List!"><br><br>   
        </form>
        <a href="BookC?de=LIST">Click here</a> to go back to the list.
    </body>
</html>
