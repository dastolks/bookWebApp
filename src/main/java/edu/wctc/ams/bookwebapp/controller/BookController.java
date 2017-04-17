/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.controller;

import edu.wctc.ams.bookwebapp.entity.Author;
import edu.wctc.ams.bookwebapp.entity.BookEnum;
import edu.wctc.ams.bookwebapp.entity.Book;
import edu.wctc.ams.bookwebapp.service.AuthorService;
import edu.wctc.ams.bookwebapp.service.BookService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alec
 */
@WebServlet(name = "BookCon", urlPatterns = {"/BookC"})
public class BookController extends HttpServlet {

    
    private BookEnum de = BookEnum.LIST;
    private String NEXT_PAGE;
    

    private BookService bookService;
    private AuthorService authService;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        de = BookEnum.valueOf(request.getParameter("de"));
        System.out.println("IS THE ERROR HERE?");
        System.out.println(de);
        //HttpSession session = request.getSession();
        //ServletContext ctx = request.getServletContext();
        System.out.println("HOW ABOUT HERE?");
        try {
            switch(de){
                case LIST:
                    System.out.println("ERROR HERE?");
                    loadBookList(request);
                break;
                case EDIT_DELETE_CREATE:
                    String submitEdit = request.getParameter("submitForm");
                    String submitDelete = request.getParameter("submitFormDelete");
                    String submitAdd = request.getParameter("submitFormAdd");
                    String rdoValue;
                    if(submitEdit != null){
                        rdoValue = request.getParameter("bookIdBtn");
                        int newRdoBtn = Integer.parseInt(rdoValue);
                        request.setAttribute("rdoBtnValue", newRdoBtn);
                        Book bkForEditing = findBook(newRdoBtn);
                        List<Author> authorList = authService.findAll();
                        System.out.println(authorList);
                        request.setAttribute("book", bkForEditing);
                        request.setAttribute("authorList", authorList);
                        NEXT_PAGE = "bookEdit.jsp";
                    }
                    if(submitDelete != null){
                        rdoValue = request.getParameter("bookIdBtn");
                        bookService.deleteById(rdoValue);
                        NEXT_PAGE = "bookDelete.jsp";
                    }
                    if(submitAdd != null){
                        NEXT_PAGE = "bookAdd.jsp";
                    }
                break;
                case ADD:
                    String title = request.getParameter("titleEdit");
                    String isbn = request.getParameter("isbnEdit");
                    String idString = request.getParameter("authorEdit");
                    int trueId = Integer.parseInt(idString);
                    Author foundFromId = findAuthor(trueId);     
                    bookService.addNew(title, isbn, foundFromId);
                    loadBookList(request);
                break;
                case UPDATE:
                    String newTitle = request.getParameter("titleEdit");
                    String newIsbn = request.getParameter("isbnEdit");
                    bookService.update(request.getParameter("bookId"), newTitle, newIsbn);
                    loadBookList(request);
                break;
                
            }
        }
        catch(Exception e){
            
        }
        RequestDispatcher view = request.getRequestDispatcher(NEXT_PAGE);
        view.forward(request, response);
    }
    private void loadBookList(HttpServletRequest request) throws ClassNotFoundException, SQLException{
//        ds = new AuthorService(
//        new AuthorDao(new MySQLDbAccessor(),
//        "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
        System.out.println("OKAY GOING TO FIND ALL");
        List<Book> bookList = bookService.findAll();
        System.out.println("book service found all");
        request.setAttribute("bookList", bookList);
        System.out.println("sent");
        NEXT_PAGE = "/bookList.jsp";
        System.out.println("next page");
    }
    private Author findAuthor(int authorId){
        Author toSet = null;
        List<Author> authorList = authService.findAll();
        for(Author a: authorList){
            if(a.getAuthorID() == authorId){
                toSet = a;
                break;
            }
        }
        return toSet;
    }
    private Book findBook(int bookId){
        Book toSet = null;
        List<Book> bookList = bookService.findAll();
        for(Book b: bookList){
            if(b.getBookId() == bookId){
                toSet = b;
                break;
            }
        }
        return toSet;
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
