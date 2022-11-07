import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MyButtonListener implements ActionListener {

    private InventoryWindow inventoryWindow;
    JTextField url;
    JTextField user;
    JTextField password;


    public MyButtonListener(InventoryWindow inventoryWindow, JTextField url, JTextField user, JTextField password){
        this.inventoryWindow=inventoryWindow;
        this.url=url;
        this.user=user;
        this.password=password;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String urlStr = url.getText();
        String userStr = user.getText();
        String passwordStr = password.getText();
        System.out.println(urlStr+" _ "+userStr+" _ "+passwordStr);
        DatabaseManager dbManager = new DatabaseManager(urlStr, userStr, passwordStr);
        dbManager.fetchProducts();
        dbManager.printProducts();
        inventoryWindow.dispose();
        InventoryWindow w = new InventoryWindow(dbManager.getProducts());
    }
}
