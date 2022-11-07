import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.sql.*;
import java.util.List;


public class InventoryWindow extends JFrame{

    public InventoryWindow(List<Product> products){
        super("Inventory");

        // fake data -------------------------------------------
        String[] columnNames = {"id", "name", "quantity"};
        int i = 0;
        Object[][] data = new Object[products.size()][3];
        for(Product p : products){
            data[i][0] = p.getId();
            data[i][1] = p.getName();
            data[i][2] = p.getQuantity();
            i++;
        }

        JTable inventoryTable = new JTable(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==2){
                    return true;
                }else{
                    return false;
                }
            }
        };

        TableModelListener tml = new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if(e.getColumn()==2){
                    Object valueQuantity = inventoryTable.getValueAt(e.getFirstRow(), 2);
                    Object valueId = inventoryTable.getValueAt(e.getFirstRow(), 0);
                    int quantity;
                    int id;
                    if(valueQuantity != null){
                        quantity = Integer.parseInt(valueQuantity.toString());
                    }else{
                        quantity = 0;
                    }
                    if(valueId != null){
                        id = Integer.parseInt(valueId.toString());
                    }else{
                        id = 0;
                    }
                    final String UPDATE_QUERY = "UPDATE articles SET quantite="+quantity+" WHERE id="+id+";";
                    System.out.println(UPDATE_QUERY);
                    String url = "jdbc:mysql://localhost/inventaire";
                    String user = "guest";
                    String password = "guest";
                    DatabaseManager dbManager = new DatabaseManager(url, user, password);

                    Connection connection = null;
                    try {
                        connection = DriverManager.getConnection(url, user, password);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Statement statement = null;
                    try {
                        statement = connection.createStatement();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        statement.execute(UPDATE_QUERY);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };
        inventoryTable.getModel().addTableModelListener(tml);




        JPanel ConnectionPanel = new JPanel();
        JPanel FenetrePanel = new JPanel();

        ConnectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        ConnectionPanel.add(new Label("URL"));
        JTextField textURL = new JTextField(8);
        ConnectionPanel.add(textURL);
        ConnectionPanel.add(new Label("User"));
        JTextField textUser = new JTextField(8);
        ConnectionPanel.add(textUser);
        ConnectionPanel.add(new Label("Password"));
        JTextField textPassword = new JTextField(8);
        ConnectionPanel.add(textPassword);
        JButton button1 = new JButton("Connect");
        MyButtonListener buttonListener = new MyButtonListener(this,textURL,textUser,textPassword);
        button1.addActionListener(buttonListener);
        ConnectionPanel.add(button1);

        FenetrePanel.setLayout( new BorderLayout (5, 5));

        FenetrePanel.add(inventoryTable.getTableHeader(), BorderLayout.NORTH);
        FenetrePanel.add(inventoryTable, BorderLayout.CENTER);



        this.add(ConnectionPanel,BorderLayout.NORTH);
        this.add(FenetrePanel,BorderLayout.CENTER);

        this.setSize(500,500);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

}
