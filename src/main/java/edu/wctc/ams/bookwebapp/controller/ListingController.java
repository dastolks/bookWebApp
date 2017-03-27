/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.controller;

import edu.wctc.ams.bookwebapp.model.Author;
import edu.wctc.ams.bookwebapp.model.AuthorDao;
import edu.wctc.ams.bookwebapp.model.AuthorDaoInterface;
import edu.wctc.ams.bookwebapp.model.AuthorService;
import edu.wctc.ams.bookwebapp.model.DatabaseService;
import edu.wctc.ams.bookwebapp.model.DatabasesEnum;
import edu.wctc.ams.bookwebapp.model.DbAccessor;
import edu.wctc.ams.bookwebapp.model.MySQLDbAccessor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

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
    
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private String jndiName;
    
    private String AUTHOR_TABLENAME = "author";
    private String AUTHOR_ID = "author_ID";
    private final String AUTHOR_NAME = "author_name";
    private final String RADIO_VALUE = "radioValue";
    private final String NAME_EDIT = "nameEdit";
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        de = DatabasesEnum.valueOf(request.getParameter("de"));
        
        try {
//            ds = new AuthorService(
//                    new AuthorDao(new MySQLDbAccessor(),driverClass, url, userName, password));
            ds = injectDependenciesAndGetAuthorService();
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
                    
                    List<Author> authorListTwo = ds.createList(AUTHOR_TABLENAME,50);
                    Author testAuthor = new Author();
                    
                    String submitEdit = request.getParameter("submitForm");
                    String submitDelete = request.getParameter("submitFormDelete");
                    String submitAdd = request.getParameter("submitFormAdd");
                    //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + submitAdd);
                    
                    
                    if(submitEdit != null && !submitEdit.equals("")){
                        int rdoValue = Integer.parseInt(request.getParameter("authorIdBtn"));
                        request.setAttribute(RADIO_VALUE, rdoValue);
                        for(Author a: authorListTwo){
                            if(a.getAuthorId() == rdoValue){
                                testAuthor = a;
                                break;
                            }
                        }
                        request.setAttribute(AUTHOR_TABLENAME, testAuthor);
                        request.setAttribute("submitEdit", request.getParameter("submitForm"));
                        request.setAttribute("submitEditDelete", request.getParameter("submitFormDelete"));
                        NEXT_PAGE = "/authorEdit.jsp"; 
                    }
                    if(submitDelete != null && !submitDelete.equals("")){
                        int rdoValue = Integer.parseInt(request.getParameter("authorIdBtn"));
                        request.setAttribute(RADIO_VALUE, rdoValue);
                        ds.deleteFromList(AUTHOR_TABLENAME, AUTHOR_ID, rdoValue);
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
                    parameters.add(AUTHOR_NAME);
        
                    List<Object> attributes = new ArrayList<>();
                    attributes.add(request.getParameter(NAME_EDIT));
                    //System.out.println("why i outta " + request.getParameter("originalValue"));
                    int idForAuthors = Integer.parseInt(request.getParameter("originalValue"));
                    
                    ds.updateRecord(AUTHOR_TABLENAME, parameters, attributes, AUTHOR_ID, idForAuthors);
                    //as.updateRecord("author", parameters, attributes, "author_ID", 4);
                    loadAuthorList(request);
                break;
                case ADD:
//                    ds = new AuthorService(
//                    new AuthorDao(new MySQLDbAccessor(),
//                    "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
//                    loadAuthorList(request);
                    List<String> parametersAdd = new ArrayList();
                    parametersAdd.add(AUTHOR_NAME);
                    parametersAdd.add("date_added");
        
                    List<Object> attributesAdd = new ArrayList<>();
                    attributesAdd.add(request.getParameter(NAME_EDIT));
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    attributesAdd.add(sdf.format(now));
                    
                    ds.insertNew(AUTHOR_TABLENAME, parametersAdd, attributesAdd);
                    loadAuthorList(request);
                break;
            }
            /* TODO output your page here. You may use following sample code. */
        }
        catch(Exception e){
//            JOptionPane.showMessageDialog(null, e);
            System.out.println(e.getMessage());
        }
        RequestDispatcher view = request.getRequestDispatcher(NEXT_PAGE);
        view.forward(request, response);
    }
    private AuthorService injectDependenciesAndGetAuthorService() throws Exception {
        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DBStrategy based on the class name retrieved
        // from web.xml
        Class dbClass = Class.forName(dbStrategyClassName);
        // Use Java reflection to instanntiate the DBStrategy object
        // Note that DBStrategy classes have no constructor params
        DbAccessor db = (DbAccessor) dbClass.newInstance();
        //System.out.println("Made it here 1");
        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DAO based on the class name retrieved above.
        // This one is trickier because the available DAO classes have
        // different constructor params
        AuthorDaoInterface authorDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor = null;
        //System.out.println("Made it here 2");
        //System.out.println("The value of daoClass is " + daoClass);
        // This will only work for the non-pooled AuthorDao
        try {
            constructor = daoClass.getConstructor(new Class[]{
                DbAccessor.class, String.class, String.class, String.class, String.class
            });
        } catch(NoSuchMethodException nsme) {
            // do nothing, the exception means that there is no such constructor,
            // so code will continue executing below
        }
        //System.out.println("Made it here 3");
        //System.out.println("the value of constructor is " + constructor);

        // constructor will be null if using connectin pool dao because the
        // constructor has a different number and type of arguments
        
        if (constructor != null) {
            // conn pool NOT used so constructor has these arguments
            Object[] constructorArgs = new Object[]{
                db, driverClass, url, userName, password
            };
            authorDao = (AuthorDaoInterface) constructor
                    .newInstance(constructorArgs);

        } else {
            /*
             Here's what the connection pool version looks like. First
             we lookup the JNDI name of the Glassfish connection pool
             and then we use Java Refletion to create the needed
             objects based on the servlet init params
             */
            Context ctx = new InitialContext();
//            Context envCtx = (Context) ctx.lookup("java:comp/env");
            //System.out.println("Made it here 4");
            //System.out.println("JndiName is " + jndiName);
//            System.out.println("values of ctx and envCtx are " + ctx + " and " + envCtx);
            DataSource ds = (DataSource) ctx.lookup(jndiName);
            //System.out.println("Made it here 5");
            constructor = daoClass.getConstructor(new Class[]{
                DataSource.class, DbAccessor.class
            });
            
            Object[] constructorArgs = new Object[]{
                ds, db
            };

            authorDao = (AuthorDaoInterface) constructor
                    .newInstance(constructorArgs);
        }
        System.out.println("Made it here past if");
        return new AuthorService(authorDao);
    }
    
    private void loadAuthorList(HttpServletRequest request) throws ClassNotFoundException, SQLException{
//        ds = new AuthorService(
//        new AuthorDao(new MySQLDbAccessor(),
//        "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
        List<Author> authorList = ds.createList(AUTHOR_TABLENAME,50);
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
    public void init() throws ServletException{
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        userName = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");
        dbStrategyClassName = getServletContext().getInitParameter("dbStrategyClass");
        daoClassName = getServletContext().getInitParameter("authorDao");
        jndiName = getServletContext().getInitParameter("connPoolName");
    }
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
