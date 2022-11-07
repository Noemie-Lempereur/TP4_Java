import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private String url;
    private String user;
    private String password;
    List<Product> products;

    public DatabaseManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        products = new ArrayList<Product>();
    }

    public void printProducts(){
        for(Product p : products){
            System.out.println(p.getId() + " " + p.getName() + " " + p.getQuantity());
        }
        /*Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM articles");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int quantity = resultSet.getInt(3);
                System.out.println(id + " " + name + " " + quantity);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void fetchProducts(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM articles");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int quantity = resultSet.getInt(3);
                Product product = new Product(id, name,quantity);
                products.add(product);
            }
        }
        catch (SQLException e) {
            // gestion des erreurs
        }
    }

    public List<Product> getProducts() {
        return products;
    }
}
