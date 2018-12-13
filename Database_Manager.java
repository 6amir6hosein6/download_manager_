
package download_manager_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database_Manager {
    private Connection db_connection;
    public void connect_to_database() throws SQLException{
        db_connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/filedownlaod","root","0021979677");
    }
    public void insert(String name, String size , String progress , String url , String dist) throws SQLException{
        Statement statement = db_connection.createStatement();
        statement.execute("INSERT INTO downloads (name,size,progress,url,dist) VALUES ('"+name+"','"+size+"','"+progress+"','"+url+"','"+dist+"')");
    }
    
    public void update(String progress , String name) throws SQLException{
        Statement statement = db_connection.createStatement();
        statement.execute("Update downloads set progress='"+progress+"'where name='"+name+"'");
    }


    public void delete(int id) throws SQLException {
        Statement statement = db_connection.createStatement();
        statement.execute("DELETE FROM downloads WHERE id="+id+"");
    }

    public ArrayList<Files> filess() throws SQLException{
        ArrayList<Files> files = new ArrayList<>();
        Statement statement = db_connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM downloads");
        if(resultSet.first()){
            do{
                Files f =new Files(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(6),
                        resultSet.getInt(5)
                );
                files.add(f);
            }while(resultSet.next());
        }
        return files;
    }


}
