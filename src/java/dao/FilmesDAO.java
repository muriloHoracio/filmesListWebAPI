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
            String sql = "CREATE TABLE IF NOT EXISTS ?(? SERIAL PRIMARY KEY, ? VARCHAR, ? VARCHAR, ? INTEGER, ? BOOLEAN, ? BOOLEAN, ? BOOLEAN);";
            ps = con.prepareStatement(sql);
            ps.setString(1, TABLE_NAME);
            ps.setString(2, COL_ID);
            ps.setString(3, COL_TITLE);
            ps.setString(4, COL_GENRE);
            ps.setString(5, COL_NUMBER);
            ps.setString(6, COL_NET);
            ps.setString(7, COL_ATHOME);
            ps.setString(8, COL_WATCHED);
            int r = ps.executeUpdate();
            ps.close();
            con.close();
            return (r>0);
        } catch(Exception e) {
            
        }
        return false;
    }
    
    public static boolean dropTable(){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON,USER,PASSWORD);
            String sql = "DROP TABLE IF EXISTS ?;";
            ps = con.prepareStatement(sql);
            ps.setString(1, TABLE_NAME);
            int r = ps.executeUpdate();
            return (r>0);
        } catch(Exception e){
            
        }
        return false;
    }
    
    public static List<Filme> listaFilmes(int idFirst, int limit){
        try{
            List<Filme> list = new ArrayList<>();
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "SELECT * FROM filme WHERE \"ID\" >= ? LIMIT ?;";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idFirst);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Filme(rs.getInt("ID"),rs.getString("Titulo"),rs.getString("Genero"),rs.getInt("Numero"),rs.getBoolean("Net"),rs.getBoolean("EmCasa")));
            }
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
                sql = "SELECT * FROM filme WHERE \"Titulo\" ILIKE ? ORDER BY \"Titulo\" ASC LIMIT 25;";
                ps = con.prepareStatement(sql);
                ps.setString(1,"%"+query+"%");
            } else if(type.equals("Gênero")) {
                sql = "SELECT * FROM filme WHERE \"Genero\" ILIKE ? ORDER BY \"Titulo\",\"Genero\" ASC LIMIT 25;";
                ps = con.prepareStatement(sql);
                ps.setString(1,"%"+query+"%");
            } else if(type.equals("Número")) {
                sql = "SELECT * FROM filme WHERE \"Numero\" = ? ORDER BY \"Titulo\" ASC LIMIT 25;";
                ps = con.prepareStatement(sql);
                ps.setInt(1,Integer.parseInt(query));
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Filme(rs.getInt("ID"),rs.getString("Titulo"),rs.getString("Genero"),rs.getInt("Numero"),rs.getBoolean("Net"),rs.getBoolean("EmCasa")));
            }
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
                sql = "SELECT * FROM filme WHERE \"Titulo\" ILIKE ? ORDER BY \"Titulo\" ASC LIMIT 25 OFFSET ?;";
                ps = con.prepareStatement(sql);
                ps.setString(1,"%"+query+"%");
                ps.setInt(2, data_offset*25);
            } else if(type.equals("Gênero")) {
                sql = "SELECT * FROM filme WHERE \"Genero\" ILIKE ? ORDER BY \"Titulo\",\"Genero\" ASC LIMIT 25 OFFSET ?;";
                ps = con.prepareStatement(sql);
                ps.setString(1,"%"+query+"%");
                ps.setInt(2, data_offset*25);
            } else if(type.equals("Número")) {
                sql = "SELECT * FROM filme WHERE \"Numero\" = ? ORDER BY \"Titulo\" ASC LIMIT 25 OFFSET ?;";
                ps = con.prepareStatement(sql);
                ps.setInt(1,Integer.parseInt(query));
                ps.setInt(2, data_offset*25);
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Filme(rs.getInt("ID"),rs.getString("Titulo"),rs.getString("Genero"),rs.getInt("Numero"),rs.getBoolean("Net"),rs.getBoolean("EmCasa")));
            }
            con.close();
            return list;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static String lastNumber(){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "SELECT MAX(\"Numero\") FROM filme;";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                con.close();
                return rs.getInt(1)+"";
            }
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static boolean addMovie(String titulo, String genero, int numero, boolean net, boolean emcasa){
        try{
            System.out.println("titulo: "+titulo);
            System.out.println("genero: "+genero);
            System.out.println("numero: "+numero);
            System.out.println("net: "+net);
            System.out.println("emcasa: "+emcasa);
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "INSERT INTO filme(\"Titulo\",\"Genero\",\"Numero\",\"Net\",\"EmCasa\") VALUES(?,?,?,?,?);";
            ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, genero);
            ps.setInt(3, numero);
            ps.setBoolean(4, net);
            ps.setBoolean(5, emcasa);
            boolean result = ps.executeUpdate()>0;
            con.close();
            return (result);
        }catch(Exception e){
            
        }
        return false;
    }
    
    public static boolean editMovie(int id, String titulo, String genero, int numero, boolean net, boolean emcasa){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            String sql = "UPDATE filme SET \"Titulo\" = ?, \"Genero\" = ?, \"Numero\" = ?, \"Net\" = ?, \"EmCasa\" = ? WHERE \"ID\" = ?;";
            ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, genero);
            ps.setInt(3, numero);
            ps.setBoolean(4, net);
            ps.setBoolean(5, emcasa);
            ps.setInt(6, id);
            boolean result = ps.executeUpdate()>0;
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
            String sql = "DELETE FROM filme WHERE \"ID\" = ?;";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            boolean result = ps.executeUpdate()>0;
            con.close();
            return (result);
        }catch(Exception e){
            
        }
        return false;
    }
    
    public static String backupData(){
        String backupText = "INSERT INTO filme(\"ID\", \"EmCasa\", \"Genero\", \"Net\", \"Numero\", \"Titulo\") VALUES";
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            ps = con.prepareStatement("SELECT * FROM filme;");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                backupText += "("+rs.getInt("ID")+","+rs.getBoolean("EmCasa")+",'"+rs.getString("Genero")+"',"+rs.getBoolean("Net")+","+rs.getInt("Numero")+",'"+rs.getString("Titulo")+"')";
            }
            while(rs.next()){
                backupText += ",("+rs.getInt("ID")+","+rs.getBoolean("EmCasa")+",'"+rs.getString("Genero")+"',"+rs.getBoolean("Net")+","+rs.getInt("Numero")+",'"+rs.getString("Titulo")+"')";
            }
            backupText += ";";
            return backupText;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static String partialBackup(int limit, int offset){
        String backupText = "INSERT INTO filme(\"ID\", \"EmCasa\", \"Genero\", \"Net\", \"Numero\", \"Titulo\") VALUES";
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            ps = con.prepareStatement("SELECT * FROM filme LIMIT ? OFFSET ?;");
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                backupText += "("+rs.getInt("ID")+","+(rs.getBoolean("EmCasa")?1:0)+",'"+rs.getString("Genero")+"',"+(rs.getBoolean("Net")?1:0)+","+rs.getInt("Numero")+",'"+rs.getString("Titulo")+"')";
            } else {
                return "";
            }
            while(rs.next()){
                backupText += ",("+rs.getInt("ID")+","+(rs.getBoolean("EmCasa")?1:0)+",'"+rs.getString("Genero")+"',"+(rs.getBoolean("Net")?1:0)+","+rs.getInt("Numero")+",'"+rs.getString("Titulo")+"')";
            }
            backupText += ";";
            return backupText;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public static int filmsCount(){
        try{
            Class.forName(SQL_DRIVER);
            con = DriverManager.getConnection(DATABASE_CON, USER, PASSWORD);
            ps = con.prepareStatement("SELECT count(*) AS \"FilmesCount\" FROM filme;");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("FilmesCount");
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
            ps = con.prepareStatement("SELECT * FROM filme ORDER BY \"Titulo\" LIMIT ? OFFSET ?;");
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                films.add(new Filme(rs.getInt("ID"), rs.getString("Titulo"), rs.getString("Genero"), rs.getInt("Numero"), rs.getBoolean("Net"), rs.getBoolean("EmCasa")));
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
            ps = con.prepareStatement("SELECT * FROM filme ORDER BY \"Titulo\";");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                films.add(new Filme(rs.getInt("ID"), rs.getString("Titulo"), rs.getString("Genero"), rs.getInt("Numero"), rs.getBoolean("Net"), rs.getBoolean("EmCasa")));
            }
            ps.close();
            con.close();
            return films;
        }catch(Exception e){
            
        }
        return null;
    }
}
