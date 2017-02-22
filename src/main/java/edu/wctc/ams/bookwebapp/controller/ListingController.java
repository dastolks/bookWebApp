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
    private DatabasesEnum de = DatabasesEnum.EDIT_DELETE;
    private int editPage = 1;
    private AuthorService ds;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        de = DatabasesEnum.valueOf(request.getParameter("de"));
        
        try {
            switch(de){
                case AUTHOR:
                    ds = new AuthorService(
                    new AuthorDao(new MySQLDbAccessor(),
                    "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
                    List<Author> authorList = ds.createList("author",50);
                    request.setAttribute("authorList", authorList);
                    NEXT_PAGE = "/authorList.jsp";
                break;
                case EDIT_DELETE:
                    ds = new AuthorService(
                    new AuthorDao(new MySQLDbAccessor(),
                    "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
                    // if using checkbox you must use request.getParameterValues("authorId")
                    // which returns and array Strings
                    // get value of radio button
                    String rdoValue = request.getParameter("authorIdBtn");
//                    if(){
//                        
//                    }
                    
                break;
            }
            /* TODO output your page here. You may use following sample code. */
        }
        catch(Exception e){
            
        }
        RequestDispatcher view = request.getRequestDispatcher(NEXT_PAGE);
        view.forward(request, response);
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
