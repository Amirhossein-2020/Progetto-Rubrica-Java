package rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.JToolBar;
import java.util.List;

public class RubricaFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private List<Persona> listaPersone;

    public RubricaFrame() {
        setTitle("Rubrica Telefonica - JDBC Version");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table Setup (Nome, Cognome, Telefono)
        String[] columns = {"Nome", "Cognome", "Telefono"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override // Cells non-editable
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Tool bar
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false); // Keeps it fixed at top
        // Buttons
        JButton btnNuovo = new JButton("Nuovo");
        JButton btnModifica = new JButton("Modifica");
        JButton btnElimina = new JButton("Elimina");

        toolbar.add(btnNuovo);
        toolbar.addSeparator(); // Adds a little line between buttons
        toolbar.add(btnModifica);
        toolbar.addSeparator();
        toolbar.add(btnElimina);

        add(toolbar, BorderLayout.NORTH); // Put at TOP
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Event Listeners
        btnNuovo.addActionListener(e -> apriEditor(null));
        btnModifica.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selezionare una persona per modificare.");
                return;
            }
            apriEditor(listaPersone.get(selectedRow));
        });

        btnElimina.addActionListener(e -> eliminaPersona());

        // Initial Load
        ricaricaDati();
    }

    private void ricaricaDati() {
        listaPersone = DbHandler.getAllPersone();
        tableModel.setRowCount(0); // Clear table
        for (Persona p : listaPersone) {
            tableModel.addRow(new Object[]{p.getNome(), p.getCognome(), p.getTelefono()});
        }
    }

    private void apriEditor(Persona p) {
        EditorPersona editor = new EditorPersona(this, p);
        editor.setVisible(true);
        if (editor.isSalvato()) {
            Persona result = editor.getPersona();
            if (p == null) {
                DbHandler.insertPersona(result);
            } else {
                DbHandler.updatePersona(result);
            }
            ricaricaDati(); // Refresh from DB
        }
    }

private void eliminaPersona() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            // "title" and "message" in Italian
            JOptionPane.showMessageDialog(this, 
                "Selezionare una persona per eliminare.", 
                "Attenzione", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Persona p = listaPersone.get(selectedRow);
        
        // Define custom buttons in Italian
        Object[] opzioni = {"Sì", "No"};
        
        int confirm = JOptionPane.showOptionDialog(
            this,
            "Eliminare la persona " + p.getNome() + " " + p.getCognome() + "?",
            "Conferma Eliminazione",       
            JOptionPane.YES_NO_OPTION,     
            JOptionPane.QUESTION_MESSAGE,  
            null,                          
            opzioni,                       
            opzioni[1]                    
        );

        // Check if the user clicked the first button ("Sì")
        if (confirm == 0) {
            DbHandler.deletePersona(p.getId());
            ricaricaDati();
        }
    }
	public static void main(String[] args) {
	    // Look and Feel Polish
	    try {
	        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } catch (Exception e) {}
	
	    // START WITH LOGIN instead of Main Frame
	    SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
	}
}