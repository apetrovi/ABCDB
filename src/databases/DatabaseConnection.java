package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class DatabaseConnection {

    private Connection getConnection() {

        Connection connection = null;
   
        try {
            //jdbc:sqlserver://ryansbooksdb.database.windows.net:1433;database=BookDB;user=bookAdmin@ryansbooksdb;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
            String database_URl = "jdbc:sqlserver://ryansbooksdb.database.windows.net:1433;"
                    + "database=BookDB;user=" 
                    + Input_Data.USERNAME + ";password=" + Input_Data.PW;
            
            connection = DriverManager.getConnection(database_URl);

            return connection;

        } catch (SQLException e) {
            System.out.println("error with connection");
            System.out.println(e);
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;

        }

    }//end of getConnection method

    public TableModel getTitlesByAuthor(String auID) {

        String sql = "select t.title_id,title_name, type, pub_id, pages, " 
                + "price, sales, pubdate, contract"
                + " from titles t inner join title_authors ta "
                + "on ta.title_id = t.title_id "
                + "where ta.au_id = ? ";

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, auID);
            ResultSet rs = ps.executeQuery();
            return DbUtils.resultSetToTableModel(rs);

        } catch (SQLException e) {
            System.out.println("getTitlesbyAuthor");
            System.out.println(e);
            return null;
        }

    }

    public DefaultComboBoxModel buildAuthorList() {
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        String sql = "SELECT au_id, au_fname, au_lname, phone from authors;";

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Author a = new Author();
                a.setAuID(rs.getString(1));
                a.setfName(rs.getString(2));
                a.setlName(rs.getString(3));
                a.setPhone(rs.getString(4));

                comboBoxModel.addElement(a);

            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("buildAuthorList");
            System.out.println(e);
        }
        return comboBoxModel;
    }

    public boolean addBook(Book b) {

        String sql
                = "INSERT INTO titles VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, b.getTitle_id());
            ps.setString(2, b.getTitle_name());
            ps.setString(3, b.getType());
            ps.setString(4, b.getPub_id());
            ps.setInt(5, b.getPages());
            ps.setDouble(6, b.getPrice());
            ps.setInt(7, b.getSales());
            ps.setString(8, b.getPubdate());
            ps.setInt(9, b.getContract());

            ps.executeUpdate();
            
            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;

        }
    }

    public boolean addTileAuthor(Book b, Author a) {

        String sql = "insert into title_authors VALUES (?, ?, 1, 1.00)";

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, b.getTitle_id());
            ps.setString(2, a.getAuID());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;

        }
    }

    public boolean deleteBook(Book b) {

        String sql = "delete from titles where title_id = ?";

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, b.getTitle_id());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteTitleAuthor(String titleid) {

        String sql = "delete from title_authors where title_id = ?";

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, titleid);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean updateTitle(Book b) {
        String sql
                = "update titles "
                + "set title_name = ?,"
                + " type = ?,"
                + " pub_id = ?,"
                + " pages = ?,"
                + " price = ?,"
                + " sales = ?"
                + " where title_id = ?";

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, b.getTitle_name());
            ps.setString(2, b.getType());
            ps.setString(3, b.getPub_id());
            ps.setInt(4, b.getPages());
            ps.setDouble(5, b.getPrice());
            ps.setInt(6, b.getSales());

            ps.setString(7, b.getTitle_id());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;

        }
    }
}//end of class file
