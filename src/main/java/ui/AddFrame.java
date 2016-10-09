package ui;

import entity.Leash;
import logic.DBManager;
import logic.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AddFrame extends JFrame {

    private DefaultTableModel tableModel;
    private FrameHelper frameHelper = new FrameHelper(this);

    public AddFrame(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
        setTitle("Dodaj smycz");
        frameHelper.setSettings();
        add(initComponents(), BorderLayout.CENTER);
    }

    private JComponent initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());

        frameHelper.addFields(panel);
        frameHelper.addFieldsListener();

        frameHelper.addOKBtn(panel, "Dodaj");
        frameHelper.getOkBtn().addActionListener(new ButtonHandler());
        frameHelper.addCancelBtn(panel);
        frameHelper.getCancelBtn().addActionListener(new ButtonHandler());

        return panel;
    }

    private class ButtonHandler implements ActionListener {
        DBManager dbManager = new DBManager();

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == frameHelper.getOkBtn()) {
                Leash leash = frameHelper.newLeashWithFields();

                if (frameHelper.isLeashInvalid(leash) || !isSuccessCopy())
                    return;

                Long ID = dbManager.add(leash);
                Object[] newRow = frameHelper.newObjectWithFields(ID, leash);
                tableModel.addRow(newRow);
                setVisible(false);

            } else if (source == frameHelper.getCancelBtn()) {
                setVisible(false);
            }
        }

        private boolean isSuccessCopy() {
            File file = frameHelper.getFile();
            if (file != null) {
                String imageName = frameHelper.getImageNameField().getText();
                try {
                    new FileManager().copyImage(file, imageName);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Nie mozna skopiowac pliku",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Prosze podac plik (dwuklik na image name)",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

    }

}
