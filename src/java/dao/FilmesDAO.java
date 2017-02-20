/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import entity.Filme;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.spi.DirStateFactory;

/**
 *
 * @author murilo
 */
public class FilmesDAO {
    private static Connection con;
    private static PreparedStatement ps;
    private static final String USER = "postgres";
    private static final String PASSWORD = "murilo";
    private static final String DATABASE_CON = "jdbc:postgresql://localhost:5432/filmes";
    private static final String SQL_DRIVER = "org.postgresql.Driver";
    private static final String TABLE_NAME = "filmes";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_GENRE = "genre";
    private static final String COL_NUMBER = "number";
    private static final String COL_NET = "net";
    private static final String COL_ATHOME = "athome";
    private static final String COL_WATCHED = "watched";
    
    public static boolean createTable(){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "CREATE TABLE IF NOT EXISTS \""+TABLE_NAME+"\" ( \""+COL_ID+"\" SERIAL PRIMARY KEY, \""+COL_TITLE+"\" VARCHAR, \""+COL_GENRE+"\" VARCHAR, \""+COL_NUMBER+"\" INTEGER, \""+COL_NET+"\" BOOLEAN, \""+COL_ATHOME+"\" BOOLEAN, \""+COL_WATCHED+"\" BOOLEAN);";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            con.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean dropTable(){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON,USER,PASSWORD);
            String sql = "DROP TABLE IF EXISTS \""+TABLE_NAME+"\";";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            con.close();
            return true;
        } catch(Exception e){
            
        }
        return false;
    }
    
    public static List<Filme> listaFilmes(int idFirst, int limit){
        try{
            List<Filme> list = new ArrayList<>();
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "SELECT * FROM \""+TABLE_NAME+"\" WHERE \""+COL_ID+"\" >= ? LIMIT ?;";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idFirst);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Filme(rs.getInt(COL_ID),rs.getString(COL_TITLE),rs.getString(COL_GENRE),rs.getInt(COL_NUMBER),rs.getBoolean(COL_NET),rs.getBoolean(COL_ATHOME),rs.getBoolean(COL_WATCHED)));
            }
            ps.close();
            con.close();
            return list;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static List<Filme> buscaFilmes(String query, String type){
        try{
            List<Filme> list = new ArrayList<>();
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "";
            if(type.equals("Título")){
                sql = "SELECT * FROM \""+TABLE_NAME+"\" WHERE \""+COL_TITLE+"\" ILIKE ? ORDER BY \""+COL_TITLE+"\" ASC LIMIT 25;";
                ps = con.prepareStatement(sql);
                ps.setString(1,"%"+query+"%");
            } else if(type.equals("Gênero")) {
                sql = "SELECT * FROM \""+TABLE_NAME+"\" WHERE \""+COL_GENRE+"\" ILIKE ? ORDER BY \""+COL_TITLE+"\",\""+COL_GENRE+"\" ASC LIMIT 25;";
                ps = con.prepareStatement(sql);
                ps.setString(1,"%"+query+"%");
            } else if(type.equals("Número")) {
                sql = "SELECT * FROM \""+TABLE_NAME+"\" WHERE \""+COL_NUMBER+"\" = ? ORDER BY \""+COL_TITLE+"\" ASC LIMIT 25;";
                ps = con.prepareStatement(sql);
                ps.setInt(1,Integer.parseInt(query));
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Filme(rs.getInt(COL_ID),rs.getString(COL_TITLE),rs.getString(COL_GENRE),rs.getInt(COL_NUMBER),rs.getBoolean(COL_NET),rs.getBoolean(COL_ATHOME),rs.getBoolean(COL_WATCHED)));
            }
            ps.close();
            con.close();
            return list;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static List<Filme> buscaFilmes(String query, String type, int data_offset){
        try{
            List<Filme> list = new ArrayList<>();
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "";
            if(type.equals("Título")){
                sql = "SELECT * FROM \""+TABLE_NAME+"\" WHERE \""+COL_TITLE+"\" ILIKE ? ORDER BY \""+COL_TITLE+"\" ASC LIMIT 25 OFFSET ?;";
                ps = con.prepareStatement(sql);
                ps.setString(1,"%"+query+"%");
                ps.setInt(2, data_offset*25);
            } else if(type.equals("Gênero")) {
                sql = "SELECT * FROM \""+TABLE_NAME+"\" WHERE \""+COL_GENRE+"\" ILIKE ? ORDER BY \""+COL_TITLE+"\",\""+COL_GENRE+"\" ASC LIMIT 25 OFFSET ?;";
                ps = con.prepareStatement(sql);
                ps.setString(1,"%"+query+"%");
                ps.setInt(2, data_offset*25);
            } else if(type.equals("Número")) {
                sql = "SELECT * FROM \""+TABLE_NAME+"\" WHERE \""+COL_NUMBER+"\" = ? ORDER BY \""+COL_TITLE+"\" ASC LIMIT 25 OFFSET ?;";
                ps = con.prepareStatement(sql);
                ps.setInt(1,Integer.parseInt(query));
                ps.setInt(2, data_offset*25);
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Filme(rs.getInt(COL_ID),rs.getString(COL_TITLE),rs.getString(COL_GENRE),rs.getInt(COL_NUMBER),rs.getBoolean(COL_NET),rs.getBoolean(COL_ATHOME),rs.getBoolean(COL_WATCHED)));
            }
            ps.close();
            con.close();
            return list;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static int lastNumber(){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "SELECT MAX(\""+COL_NUMBER+"\") FROM \""+TABLE_NAME+"\";";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int r = rs.getInt(1);
                ps.close();
                con.close();
                return r;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    
    public static boolean addMovie(String titulo, String genero, int numero, boolean net, boolean emcasa, boolean watched){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "INSERT INTO \""+TABLE_NAME+"\"(\""+COL_TITLE+"\",\""+COL_GENRE+"\",\""+COL_NUMBER+"\",\""+COL_NET+"\",\""+COL_ATHOME+"\",\""+COL_WATCHED+"\") VALUES(?,?,?,?,?,?);";
            ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, genero);
            ps.setInt(3, numero);
            ps.setBoolean(4, net);
            ps.setBoolean(5, emcasa);
            ps.setBoolean(6, watched);
            boolean result = ps.executeUpdate()>0;
            ps.close();
            con.close();
            return (result);
        }catch(Exception e){
            
        }
        return false;
    }
    
    public static boolean editMovie(int id, String titulo, String genero, int numero, boolean net, boolean emcasa, boolean watched){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "UPDATE \""+TABLE_NAME+"\" SET \""+COL_TITLE+"\" = ?, \""+COL_GENRE+"\" = ?, \""+COL_NUMBER+"\" = ?, \""+COL_NET+"\" = ?, \""+COL_ATHOME+"\" = ?, \""+COL_WATCHED+"\" = ? WHERE \""+COL_ID+"\" = ?;";
            ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, genero);
            ps.setInt(3, numero);
            ps.setBoolean(4, net);
            ps.setBoolean(5, emcasa);
            ps.setBoolean(6, watched);
            ps.setInt(7, id);
            boolean result = ps.executeUpdate()>0;
            ps.close();
            con.close();
            return (result);
        }catch(Exception e){
            
        }
        return false;
    }
    
    public static boolean deleteMovie(int id){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "DELETE FROM \""+TABLE_NAME+"\" WHERE \""+COL_ID+"\" = ?;";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            boolean result = ps.executeUpdate()>0;
            ps.close();
            con.close();
            return (result);
        }catch(Exception e){
            
        }
        return false;
    }
    
    public static String backupData(){
        //String backupText = "INSERT INTO \""+TABLE_NAME+"\"(\""+COL_ID+"\", \""+COL_TITLE+"\", \""+COL_GENRE+"\", \""+COL_NUMBER+"\", \""+COL_NET+"\", \""+COL_ATHOME+"\", \""+COL_WATCHED+"\") VALUES";
        String backupText = "";
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            ps = con.prepareStatement("SELECT * FROM \""+TABLE_NAME+"\";");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                backupText += "("+rs.getInt(COL_ID)+",'"+rs.getString(COL_TITLE)+"','"+rs.getString(COL_GENRE)+"',"+rs.getInt(COL_NUMBER)+","+rs.getBoolean(COL_NET)+","+rs.getBoolean(COL_ATHOME)+","+rs.getString(COL_WATCHED)+")";
            }
            while(rs.next()){
                backupText += ",("+rs.getInt(COL_ID)+",'"+rs.getString(COL_TITLE)+"','"+rs.getString(COL_GENRE)+"',"+rs.getInt(COL_NUMBER)+","+rs.getBoolean(COL_NET)+","+rs.getBoolean(COL_ATHOME)+","+rs.getString(COL_WATCHED)+")";
            }
            backupText += ";";
            ps.close();
            con.close();
            return backupText;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static String partialBackup(int limit, int offset){
        //String backupText = "INSERT INTO \""+TABLE_NAME+"\"(\""+COL_ID+"\", \""+COL_TITLE+"\", \""+COL_GENRE+"\", \""+COL_NUMBER+"\", \""+COL_NET+"\", \""+COL_ATHOME+"\", \""+COL_WATCHED+"\") VALUES";
        String backupText = "";
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            ps = con.prepareStatement("SELECT * FROM \""+TABLE_NAME+"\" LIMIT ? OFFSET ?;");
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                backupText += "("+rs.getInt(COL_ID)+",'"+rs.getString(COL_TITLE)+"','"+rs.getString(COL_GENRE)+"',"+rs.getInt(COL_NUMBER)+","+rs.getBoolean(COL_NET)+","+rs.getBoolean(COL_ATHOME)+","+rs.getString(COL_WATCHED)+")";
            } else {
                return "";
            }
            while(rs.next()){
                backupText += ",("+rs.getInt(COL_ID)+",'"+rs.getString(COL_TITLE)+"','"+rs.getString(COL_GENRE)+"',"+rs.getInt(COL_NUMBER)+","+rs.getBoolean(COL_NET)+","+rs.getBoolean(COL_ATHOME)+","+rs.getString(COL_WATCHED)+")";
            }
            backupText += ";";
            ps.close();
            con.close();
            return backupText;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static int filmsCount(){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            ps = con.prepareStatement("SELECT COUNT(*) AS \"FilmesCount\" FROM \""+TABLE_NAME+"\";");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int r = rs.getInt("FilmesCount");
                ps.close();
                con.close();
                return r;
            }
        }catch(Exception e){
            
        }
        return 0;
    }

    public static List<Filme> getAllData(int limit, int offset) {
        List<Filme> films = new ArrayList<>();
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            ps = con.prepareStatement("SELECT * FROM \""+TABLE_NAME+"\" ORDER BY \""+COL_TITLE+"\" LIMIT ? OFFSET ?;");
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                films.add(new Filme(rs.getInt(COL_ID),rs.getString(COL_TITLE),rs.getString(COL_GENRE),rs.getInt(COL_NUMBER),rs.getBoolean(COL_NET),rs.getBoolean(COL_ATHOME),rs.getBoolean(COL_WATCHED)));
            }
            ps.close();
            con.close();
            return films;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static List<Filme> getAllData() {
        List<Filme> films = new ArrayList<>();
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            ps = con.prepareStatement("SELECT * FROM \""+TABLE_NAME+"\" ORDER BY \""+COL_TITLE+"\";");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                films.add(new Filme(rs.getInt(COL_ID),rs.getString(COL_TITLE),rs.getString(COL_GENRE),rs.getInt(COL_NUMBER),rs.getBoolean(COL_NET),rs.getBoolean(COL_ATHOME),rs.getBoolean(COL_WATCHED)));
            }
            ps.close();
            con.close();
            return films;
        }catch(Exception e){
            
        }
        return null;
    }
}
