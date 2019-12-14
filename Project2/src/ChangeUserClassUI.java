import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownServiceException;
import java.util.Arrays;

public class ChangeUserClassUI {

    String UserT[] = {"CUSTOMER", "CASHIER", "MANAGER", "ADMIN"};

    public JFrame view;

    public JTextField txtUsername = new JTextField(10);
    public JComboBox BoxUserType = new JComboBox(UserT);

    public JButton btnUpdate = new JButton("Update");

    public ChangeUserClassUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Admin View");
        view.setSize(400, 300);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System - Admin View - Change User Class");

        title.setFont(title.getFont().deriveFont(24.0f));
        view.getContentPane().add(title);

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("Username "));
        line.add(txtUsername);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Usertype "));
        line.add(BoxUserType);
        view.getContentPane().add(line);


        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnUpdate);
        view.getContentPane().add(panelButtons);

        btnUpdate.addActionListener(new ChangeUserClassUI.updateUserActionListener());
    }

    public void run() {
        view.setVisible(true);
    }

    class updateUserActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            String username = txtUsername.getText();
            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be null!");
                return;
            }
            user.mUsername = username;

            String newusertype = (String) BoxUserType.getSelectedItem();
            if (newusertype.length() == 0) {
                JOptionPane.showMessageDialog(null, "UserType cannot be null!");
                return;
            }
            user.mUserType = Arrays.asList(UserT).indexOf(newusertype);

            Gson gson = new Gson();

            MessageModel msg = new MessageModel();
            msg.code = MessageModel.UPDATE_USER_TYPE;
            msg.data = gson.toJson(user);

            SocketNetworkAdapter net = new SocketNetworkAdapter();

            try {
                msg = net.exchange(msg, "localhost", 1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (msg.code == MessageModel.OPERATION_FAILED)
                JOptionPane.showMessageDialog(null, "Usertype NOT updated successfully!");
            else {
                JOptionPane.showMessageDialog(null, "Usertype updated successfully!" + username);
            }
        }
    }



}
