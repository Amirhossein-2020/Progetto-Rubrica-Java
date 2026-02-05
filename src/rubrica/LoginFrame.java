package rubrica;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");

    public LoginFrame() {
        setTitle("Login Rubrica");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Username:")); add(txtUser);
        add(new JLabel("Password:")); add(txtPass);
        add(new JLabel(""));          add(btnLogin);

        btnLogin.addActionListener(e -> eseguiLogin());
        
        // Allow pressing "Enter" key to login
        getRootPane().setDefaultButton(btnLogin);
    }

    private void eseguiLogin() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        if (DbHandler.login(user, pass)) {
            // Login Success: Close this and open Main App
            dispose();
            new RubricaFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Username o Password errati!", 
                "Errore Login", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}