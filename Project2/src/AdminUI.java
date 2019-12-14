import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI {
    public JFrame view;

    public JButton btnAddUser = new JButton("Add New User");
    public JButton btnChangeUserClass = new JButton("Change User Class");

    public AdminUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Admin View");
        view.setSize(400, 300);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont(title.getFont().deriveFont(24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAddUser);
        panelButtons.add(btnChangeUserClass);

        view.getContentPane().add(panelButtons);

        btnAddUser.addActionListener(new AddUserActionListener());
        btnChangeUserClass.addActionListener(new ChangeUserClassActionListener());
    }


    private class AddUserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AddUserUI au = new AddUserUI();
            au.run();
        }
    }

    private class ChangeUserClassActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ChangeUserClassUI cu = new ChangeUserClassUI();
            cu.run();
        }
    }

}