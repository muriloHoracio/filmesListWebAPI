    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.FilmesDAO;
import entity.Filme;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.parser.JSONParser;

/**
 *
 * @author murilo
 */
@WebServlet(name = "ListaFilmes", urlPatterns = {"/ListaFilmes"})
public class ListaFilmes extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        List<Filme> list = null;
        String json_response="";
        String reqType = request.getParameter("reqType");
        if(reqType!=null && !reqType.isEmpty()){
            if(reqType.equals("app_data_count")){
                response.getWriter().write(""+FilmesDAO.filmsCount());
                return;
            }
            if(reqType.equals("app_data")){
                response.getWriter().write(FilmesDAO.partialBackup(Integer.parseInt(request.getParameter("limit")), Integer.parseInt(request.getParameter("offset"))));
                return;
            }
            if(reqType.equals("save")){
                Calendar cal = new GregorianCalendar();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                
                response.setHeader("Content-Disposition","attachment; filename=backup_filmes_db_"+year+"_"+(month+1)+"_"+day+".txt");
                PrintWriter writer = new PrintWriter("backup.txt", "UTF-8");
                writer.println(FilmesDAO.backupData());
                writer.close();
                OutputStream out = response.getOutputStream();
                FileInputStream in = new FileInputStream("backup.txt");
                byte[] buffer = new byte[4096];
                int length;
                while((length = in.read(buffer)) > 0){
                    out.write(buffer, 0, length);
                }
                in.close();
                out.flush();
                out.close();
                return;
            }
            if(reqType.equals("films_count")){
                response.getWriter().write(FilmesDAO.filmsCount()+" >> Filmes");
                return;
            }
            if(reqType.equals("search")){
                if(request.getParameter("search_query")!=null){
                    list = FilmesDAO.buscaFilmes(request.getParameter("search_query"),request.getParameter("type"));
                    response.getWriter().write(jsonListBuild(list));
                    return;
                }
            }
            if(reqType.equals("add_more_data")){
                if(request.getParameter("search_query")!=null){
                    list = FilmesDAO.buscaFilmes(request.getParameter("search_query"),request.getParameter("type"),Integer.parseInt(request.getParameter("data_offset")));
                    response.getWriter().write(jsonListBuild(list));
                    return;
                }
            }
            if(reqType.equals("last_number")){
                response.getWriter().write((Integer.parseInt(FilmesDAO.lastNumber())+1)+"");
                return;
            }
        }
    }
    
    private String jsonListBuild(List<Filme> list){
        if(list!=null){
            String json_response = "[";
            if(!list.isEmpty()){
                for(Filme f:list){
                    if(f.equals(list.get(list.size()-1)))
                        json_response += "{\"id\":"+f.getID()+","
                            + "\"titulo\":\""+f.getTitulo().replace("\"", "\\\"")+"\","
                            + "\"genero\":\""+f.getGenero()+"\","
                            + "\"numero\":"+f.getNumero()+","
                            + "\"net\":"+f.isNet()+","
                            + "\"emcasa\":"+f.isEmcasa()+"}";
                    else
                        json_response += "{\"id\":"+f.getID()+","
                            + "\"titulo\":\""+f.getTitulo().replace("\"", "\\\"")+"\","
                            + "\"genero\":\""+f.getGenero()+"\","
                            + "\"numero\":"+f.getNumero()+","
                            + "\"net\":"+f.isNet()+","
                            + "\"emcasa\":"+f.isEmcasa()+"},";
                }
            }
            json_response += "]";
            return json_response;
        }
        return null;
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
        if(request.getParameter("reqType")!=null){
            if(request.getParameter("reqType").equals("add")) {
                response.getWriter().write(""+
                    FilmesDAO.addMovie(
                        request.getParameter("titulo"),
                        request.getParameter("genero"),
                        Integer.parseInt(request.getParameter("numero")),
                        Boolean.parseBoolean(request.getParameter("net")),
                        Boolean.parseBoolean(request.getParameter("emcasa"))
                    )
                );
            } else if(request.getParameter("reqType").equals("edit")) {
                response.getWriter().write(""+
                    FilmesDAO.editMovie(
                        Integer.parseInt(request.getParameter("id")),
                        request.getParameter("titulo"),
                        request.getParameter("genero"),
                        Integer.parseInt(request.getParameter("numero")),
                        Boolean.parseBoolean(request.getParameter("net")),
                        Boolean.parseBoolean(request.getParameter("emcasa"))
                    )
                );
            } else if(request.getParameter("reqType").equals("delete")) {
                response.getWriter().write(""+FilmesDAO.deleteMovie(Integer.parseInt(request.getParameter("id"))));
            }
        }
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