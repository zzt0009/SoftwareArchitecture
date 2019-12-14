import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChangeUserInfoUI {

    public JFrame view;

    public JButton btnUpdate = new JButton("Update");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtOldPassword = new JPasswordField(20);
    public JTextField txtNewPassword = new JTextField(20);
    public JTextField txtNewFullname = new JTextField(20);

    Socket link;
    Scanner input;
    PrintWriter output;

    public ChangeUserInfoUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("ChangeUserInfo");
        view.setSize(600, 400);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Username"));
        line.add(txtUsername);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Old Password"));
        line.add(txtOldPassword);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("New Password"));
        line.add(txtNewPassword);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("New Fullname"));
        line.add(txtNewFullname);
        pane.add(line);

        pane.add(btnUpdate);

        btnUpdate.addActionListener(new ChangeUserInfoUI.UpdateInfoActionListener());

    }

    public static void main(String[] args) {
        int port = 1000;
        ChangeUserInfoUI ui = new ChangeUserInfoUI();
        ui.view.setVisible(true);
    }

    private class UpdateInfoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UpdatedUserInfoModel info = new UpdatedUserInfoModel();
            info.mUsername = txtUsername.getText();
            info.mOldPassword = txtOldPassword.getText();
            info.mNewPassword = txtNewPassword.getText();
            info.mNewFullname = txtNewFullname.getText();

            if (info.mUsername.length() == 0 || info.mOldPassword.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username or Old password cannot be null!");
                return;
            }

            if (info.mNewPassword.length() == 0 && info.mNewFullname.length() == 0) {
                JOptionPane.showMessageDialog(null, "Please input the new information!");
                return;
            }

            Gson gson = new Gson();

            MessageModel msg = new MessageModel();
            msg.code = MessageModel.CHECK_USER;
            msg.data = gson.toJson(info);

            SocketNetworkAdapter net = new SocketNetworkAdapter();

            try {
                msg = net.exchange(msg, "localhost", 1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (msg.code == MessageModel.OPERATION_FAILED)
                JOptionPane.showMessageDialog(null, "Invalid username or password! Access denied!");
            else {
                JOptionPane.showMessageDialog(null, "Updated successful!");
            }

        }
    }


}
