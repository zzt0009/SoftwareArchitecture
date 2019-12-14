import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;
import java.util.Arrays;

public class AddUserUI {

    String UserT[] = {"CUSTOMER", "CASHIER", "MANAGER", "ADMIN"};

    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtUsername = new JTextField(10);
    public JTextField txtPassword = new JTextField(10);
    public JTextField txtFullname = new JTextField(10);
    public JComboBox BoxUserType = new JComboBox(UserT);
    public JTextField txtCustomerID = new JTextField(10);

    public JLabel labUsername = new JLabel("Username: ");
    public JLabel labPassword = new JLabel("Password: ");
    public JLabel labFullname = new JLabel("Fullname: ");
    public JLabel labUserType = new JLabel("UserTyoe");
    public JLabel labCustomerID = new JLabel("CustomerID: ");

    CustomerModel customer;
    UserModel user;



    public AddUserUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Admin - Add User");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(labUsername);
        line.add(txtUsername);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labPassword);
        line.add(txtPassword);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labFullname);
        line.add(txtFullname);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labUserType);
        line.add(BoxUserType);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labCustomerID);
        line.add(txtCustomerID);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(btnAdd);
        line.add(btnCancel);
        view.getContentPane().add(line);

       // txtCustomerID.addFocusListener(new AddUserUI.CustomerIDFocusListener());
        //txtQuantity.getDocument().addDocumentListener(new AddPurchaseUI.QuantityChangeListener());

        btnAdd.addActionListener(new AddUserUI.AddButtonListerner());
    }

    public void run() {
        user = new UserModel();
        customer = new CustomerModel();
        view.setVisible(true);
    }


//    private class CustomerIDFocusListener implements FocusListener {
//        @Override
//        public void focusGained(FocusEvent focusEvent) {
//
//        }
//
//        @Override
//        public void focusLost(FocusEvent focusEvent) {
//            process();
//        }
//
//        private void process() {
//            String s = txtCustomerID.getText();
//
//            if (s.length() == 0) {
//                labFullname.setText("Fullname: [not specified!]");
//                return;
//            }
//
//            System.out.println("CustomerID = " + s);
//
//            try {
//                customer.mCustomerID = Integer.parseInt(s);
//
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(null,
//                        "Error: Invalid CustomerID", "Error Message",
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            customer = StoreClient.getInstance().getDataAdapter().loadCustomer(customer.mCustomerID);
//
//            if (customer == null) {
//                JOptionPane.showMessageDialog(null,
//                        "Error: No customer with id = " + customer.mCustomerID + " in store!", "Error Message",
//                        JOptionPane.ERROR_MESSAGE);
//                labFullname.setText("Customer Name: ");
//
//                return;
//            }
//
//            labFullname.setText("Fullname: " + customer.mName);
//        }
//
//    }


    class AddButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String username = txtUsername.getText();
            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be null!");
                return;
            }
            else{
                user.mUsername = username;
            }

            String password = txtPassword.getText();
            if (password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Password cannot be null!");
                return;
            }
            else{
                user.mPassword = password;
            }

            String fullname = txtFullname.getText();
            if (fullname.length() == 0) {
                JOptionPane.showMessageDialog(null, "Fullname cannot be null!");
                return;
            }
            else{
                user.mFullname = fullname;
            }

            String usertype = (String) BoxUserType.getSelectedItem();
            if (usertype.length() == 0) {
                JOptionPane.showMessageDialog(null, "UserType cannot be null!");
                return;
            }
            else{
                user.mUserType = Arrays.asList(UserT).lastIndexOf(usertype);
            }

            String customerid = txtCustomerID.getText();
            customerid = txtCustomerID.getText();
            try {
                user.mCustomerID = Integer.parseInt(customerid);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

            Gson gson = new Gson();

            MessageModel msg = new MessageModel();
            msg.code = MessageModel.PUT_USER;
            msg.data = gson.toJson(user);

            SocketNetworkAdapter net = new SocketNetworkAdapter();

            try {
                msg = net.exchange(msg, "localhost", 1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (msg.code == MessageModel.OPERATION_FAILED)
                JOptionPane.showMessageDialog(null, "User NOT added successfully!");
            else {
                JOptionPane.showMessageDialog(null, "User added successfully!" + user);
            }

        }
    }


}
