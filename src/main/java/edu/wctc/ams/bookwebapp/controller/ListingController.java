/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.controller;

import edu.wctc.ams.bookwebapp.model.Author;
import edu.wctc.ams.bookwebapp.model.AuthorDao;
import edu.wctc.ams.bookwebapp.model.AuthorService;
import edu.wctc.ams.bookwebapp.model.DatabaseService;
import edu.wctc.ams.bookwebapp.model.DatabasesEnum;
import edu.wctc.ams.bookwebapp.model.MySQLDbAccessor;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alec
 */
@WebServlet(name = "ListCon", urlPatterns = {"/ListC"})
public class ListingController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String NEXT_PAGE = "";
    private DatabasesEnum de = DatabasesEnum.EDIT_DELETE_CREATE;
    private int editPage = 1;
    private AuthorService ds;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        de = DatabasesEnum.valueOf(request.getParameter("de"));
        
        try {
            ds = new AuthorService(
                    new AuthorDao(new MySQLDbAccessor(),
                    "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
            switch(de){
                case AUTHOR:
//                    ds = new AuthorService(
//                    new AuthorDao(new MySQLDbAccessor(),
//                    "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
//                    List<Author> authorList = ds.createList("author",50);
//                    request.setAttribute("authorList", authorList);
//                    NEXT_PAGE = "/authorList.jsp";
                    loadAuthorList(request);
                break;
                case EDIT_DELETE_CREATE:
//                    ds = new AuthorService(
//                    new AuthorDao(new MySQLDbAccessor(),
//                    "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
                    // if using checkbox you must use request.getParameterValues("authorId")
                    // which returns and array Strings
                    // get value of radio button
                    
                    List<Author> authorListTwo = ds.createList("author",50);
                    Author testAuthor = new Author();
                    
                    String submitEdit = request.getParameter("submitForm");
                    String submitDelete = request.getParameter("submitFormDelete");
                    String submitAdd = request.getParameter("submitFormAdd");
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + submitAdd);
                    
                    
                    if(submitEdit != null && !submitEdit.equals("")){
                        int rdoValue = Integer.parseInt(request.getParameter("authorIdBtn"));
                        request.setAttribute("radioValue", rdoValue);
                        for(Author a: authorListTwo){
                            if(a.getAuthorId() == rdoValue){
                                testAuthor = a;
                                break;
                            }
                        }
                        request.setAttribute("author", testAuthor);
                        request.setAttribute("submitEdit", request.getParameter("submitForm"));
                        request.setAttribute("submitEditDelete", request.getParameter("submitFormDelete"));
                        NEXT_PAGE = "/authorEdit.jsp"; 
                    }
                    if(submitDelete != null && !submitDelete.equals("")){
                        int rdoValue = Integer.parseInt(request.getParameter("authorIdBtn"));
                        request.setAttribute("radioValue", rdoValue);
                        ds.deleteFromList("author", "author_ID", rdoValue);
                        //loadAuthorList(request);
                        NEXT_PAGE = "/authorDelete.jsp";
                    }
                    if(submitAdd != null && !submitAdd.equals("")){
                        NEXT_PAGE = "/authorAdd.jsp";
                    }
                break;
                case UPDATE:
//                    ds = new AuthorService(
//                    new AuthorDao(new MySQLDbAccessor(),
//                    "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
                    //Author a = request.getParameter("author");
                    List parameters = new ArrayList();
                    parameters.add("author_name");
        
                    List<Object> attributes = new ArrayList<>();
                    attributes.add(request.getParameter("nameEdit"));
                    System.out.println("why i outta " + request.getParameter("originalValue"));
                    int idForAuthors = Integer.parseInt(request.getParameter("originalValue"));
                    
                    ds.updateRecord("author", parameters, attributes, "author_ID", idForAuthors);
                    //as.updateRecord("author", parameters, attributes, "author_ID", 4);
                    loadAuthorList(request);
                break;
                case ADD:
//                    ds = new AuthorService(
//                    new AuthorDao(new MySQLDbAccessor(),
//                    "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
//                    loadAuthorList(request);
                    List<String> parametersAdd = new ArrayList();
                    parametersAdd.add("author_name");
                    parametersAdd.add("date_added");
        
                    List<Object> attributesAdd = new ArrayList<>();
                    attributesAdd.add(request.getParameter("nameEdit"));
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    attributesAdd.add(sdf.format(now));
                    
                    ds.insertNew("author", parametersAdd, attributesAdd);
                    loadAuthorList(request);
                break;
            }
            /* TODO output your page here. You may use following sample code. */
        }
        catch(Exception e){
            
        }
        RequestDispatcher view = request.getRequestDispatcher(NEXT_PAGE);
        view.forward(request, response);
    }
    private void loadAuthorList(HttpServletRequest request) throws ClassNotFoundException, SQLException{
//        ds = new AuthorService(
//        new AuthorDao(new MySQLDbAccessor(),
//        "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
        List<Author> authorList = ds.createList("author",50);
        request.setAttribute("authorList", authorList);
        NEXT_PAGE = "/authorList.jsp";
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
