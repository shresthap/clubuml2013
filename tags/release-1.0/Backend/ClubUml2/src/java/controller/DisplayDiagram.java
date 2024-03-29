/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Domain.Diagram;
import Domain.Comment;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.Service;

/**
 *
 * @author wintor12
 */
public class DisplayDiagram extends HttpServlet {
   

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String[] checked;// selected checkbox list from display jsp page.
    private String option; // function button from display jsp page.
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        checked = (String[])request.getParameterValues("check");
        option = request.getParameter("submit");
        
        if(option.equals("Go to compare")){
            goToCompare(checked,request,response);
            
        }
        if(option.equals("Display")){
            displayDiagram(checked,request,response);
            
        }
        if(option.equals("Download")){
            downloadDiagram(checked,request,response);
        
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
    /*
     * function to get two diagrams'id and path in diagram list , stroe them in ruquest and go to the compare page.
     */
    public void goToCompare(String[] checked,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        int diagramId1 = Integer.parseInt(checked[0]); 
        int diagramId2 = Integer.parseInt(checked[1]);
        
        Service g = new Service();
        
        request.setAttribute("diagramId1", diagramId1);
        request.setAttribute("diagramId2", diagramId2);
        request.setAttribute("path1", "upload/" + g.getDiagramName(diagramId1));
        request.setAttribute("path2", "upload/" + g.getDiagramName(diagramId2));
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/JSP/compare.jsp");
        dispatcher.forward(request, response);
    
    }
    
    /*
     * function to display the selected diagram and its comments. 
     */
    
    public void displayDiagram(String[] checked,HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
        int diagramId1 = Integer.parseInt(checked[0]);
        
        Service g = new Service();
        
        ArrayList<Diagram> diagrams = g.getDiagramList();
        ArrayList<Comment> comments = g.getComments(diagramId1);
        
        request.setAttribute("diagramId1", diagramId1);
        HttpSession session = request.getSession();
        session.setAttribute("diagrams", diagrams);
        request.setAttribute("path1", "upload/" + g.getDiagramName(diagramId1));
        request.setAttribute("comments", comments);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/JSP/display.jsp");
        dispatcher.forward(request, response);
        
    }
    /*
     * function to download the selected diagram.
     */
     public void downloadDiagram(String[] checked,HttpServletRequest request, HttpServletResponse response) 
            throws FileNotFoundException, IOException{
        
         int id =Integer.parseInt(checked[0]);
         String fileName = new Service().getDiagramName(id);
        // the absolute path of folder where all diagrams store. 
         String path = "C:\\Users\\wintor12\\Documents\\NetBeansProjects\\ClubUml2\\web\\upload\\";

        try {
            OutputStream ops = response.getOutputStream();
            byte bytes[] = new byte[1024];
            int length   = 0;
            
            File fileLoad = new File(path,fileName);
            
            ServletContext context  = getServletConfig().getServletContext();
            String mimetype = context.getMimeType( fileName );
            response.setHeader("Content-disposition", "attachment;filename="+fileName);         
            response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
            response.setContentLength( (int)fileLoad.length() );

            DataInputStream in = new DataInputStream(new FileInputStream(fileLoad));
            
            while ((in != null) && ((length = in.read(bytes)) != -1))
            {
            ops.write(bytes,0,length);
            }
            ops.flush();
            ops.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(DisplayDiagram.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}
