package rubrica;

import javax.swing.*;
import java.awt.*;

public class EditorPersona extends JDialog {
    
    private static final long serialVersionUID = 1L;
    private JTextField txtNome = new JTextField(20);
    private JTextField txtCognome = new JTextField(20);
    private JTextField txtIndirizzo = new JTextField(20);
    private JTextField txtTelefono = new JTextField(20);
    private JTextField txtEta = new JTextField(5);
    private JButton btnSalva = new JButton("Salva");
    private JButton btnAnnulla = new JButton("Annulla");
    
    private Persona personaCorrente;
    private boolean salvato = false;

    public EditorPersona(Frame owner, Persona p) {
        super(owner, "Editor Persona", true);
        this.personaCorrente = p;
        setupUI();
        if (p != null) riempiCampi();
    }

    private void setupUI() {
        setLayout(new GridLayout(6, 2, 10, 10));
        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("Cognome:")); add(txtCognome);
        add(new JLabel("Indirizzo:")); add(txtIndirizzo);
        add(new JLabel("Telefono:")); add(txtTelefono);
        add(new JLabel("Età:")); add(txtEta);
        add(btnSalva); add(btnAnnulla);

        btnSalva.addActionListener(e -> salva());
        btnAnnulla.addActionListener(e -> dispose());
        
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void riempiCampi() {
        txtNome.setText(personaCorrente.getNome());
        txtCognome.setText(personaCorrente.getCognome());
        txtIndirizzo.setText(personaCorrente.getIndirizzo());
        txtTelefono.setText(personaCorrente.getTelefono());
        txtEta.setText(String.valueOf(personaCorrente.getEta()));
    }

    private void salva() {
        // CHECK FOR EMPTY FIELDS
        if (txtNome.getText().trim().isEmpty() || 
            txtCognome.getText().trim().isEmpty() || 
            txtIndirizzo.getText().trim().isEmpty() || 
            txtTelefono.getText().trim().isEmpty() || 
            txtEta.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Tutti i campi sono obbligatori!", 
                "Errore Dati", 
                JOptionPane.ERROR_MESSAGE);
            return; // STOP execution here
        }

        // CHECK AGE AND SAVE
        try {
            int eta = Integer.parseInt(txtEta.getText().trim());
            
            if (personaCorrente == null) {
                // New Persona (ID 0 placeholder, DB assigns real ID)
                personaCorrente = new Persona(0, txtNome.getText(), txtCognome.getText(), txtIndirizzo.getText(), txtTelefono.getText(), eta);
            } else {
                // Update existing
                personaCorrente.setNome(txtNome.getText());
                personaCorrente.setCognome(txtCognome.getText());
                personaCorrente.setIndirizzo(txtIndirizzo.getText());
                personaCorrente.setTelefono(txtTelefono.getText());
                personaCorrente.setEta(eta);
            }
            salvato = true;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "L'età deve essere un numero valido!", 
                "Errore Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSalvato() { return salvato; }
    public Persona getPersona() { return personaCorrente; }
}