import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductSearchUI {

        public JFrame view;
        public JTable productTable;
        public UserModel user = null;

    public JButton btnSearch = new JButton("Search");

    public JTextField txtName = new JTextField(20);
    public JTextField txtminPrice= new JTextField(20);
    public JTextField txtmaxPrice= new JTextField(20);

    ProductSearchModel product = new ProductSearchModel();


        public ProductSearchUI(UserModel user) {
            this.user = user;

            this.view = new JFrame();

            view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            view.setTitle("Search Product");
            view.setSize(600, 400);
            view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

            JLabel title = new JLabel("Search results for " + user.mFullname);

            title.setFont (title.getFont().deriveFont (24.0f));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            view.getContentPane().add(title);

            JPanel panelButtons = new JPanel(new FlowLayout());
            panelButtons.add(btnSearch);
            view.getContentPane().add(panelButtons);

            JPanel line1 = new JPanel(new FlowLayout());
            line1.add(new JLabel("Product Name "));
            line1.add(txtName);
            view.getContentPane().add(line1);

            line1 = new JPanel(new FlowLayout());
            line1.add(new JLabel("minPrice "));
            line1.add(txtminPrice);
            view.getContentPane().add(line1);


            line1 = new JPanel(new FlowLayout());
            line1.add(new JLabel("maxPrice "));
            line1.add(txtmaxPrice);
            view.getContentPane().add(line1);

            btnSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    String name = txtName.getText();
                    if (name.length() == 0) {
                        JOptionPane.showMessageDialog(null, "Name cannot be null!");
                        return;
                    }
                    else{
                        product.mName = name;
                    }

                    try {
                        double minp = Double.parseDouble(txtminPrice.getText());
                        product.mMinprice = minp;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "minPrice is invalid!");
                        return;
                    }

                    try {
                        double maxp = Double.parseDouble(txtmaxPrice.getText());
                        product.mMaxprice = maxp;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "maxPrice is invalid!");
                        return;
                    }


                    Gson gson = new Gson();

                    MessageModel msg = new MessageModel();
                    msg.code = MessageModel.SEARCH_PRODUCT;
                    msg.data = gson.toJson(user);

                    SocketNetworkAdapter net = new SocketNetworkAdapter();

                    try {
                        msg = net.exchange(msg, "localhost", 1000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                    ProductListModel list = gson.fromJson(msg.data, ProductListModel.class); // DEFAULT VALUES
                    DefaultTableModel tableData = new DefaultTableModel();

                    tableData.addColumn("ProductID");
                    tableData.addColumn("Product Name");
                    tableData.addColumn("Price");
                    tableData.addColumn("Quantity");

                    for (ProductModel p : list.products) {
                        Object[] row = new Object[tableData.getColumnCount()];

                        row[0] = p.mProductID;
                        row[1] = p.mName;
                        row[2] = p.mPrice;
                        row[3] = p.mQuantity;
                        tableData.addRow(row);
                    }

//        purchases = new JList(data);

                    productTable = new JTable(tableData);

                    JScrollPane scrollableList = new JScrollPane(productTable);

                    view.getContentPane().add(scrollableList);
                }
            });
        }
    }

