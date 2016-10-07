package ui;

import entity.Leash;
import logic.DBManager;
import logic.TableModelHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchFrame extends JFrame {

    private DefaultTableModel tableModel;
    private FrameHelper frameHelper = new FrameHelper(this);
    private DBManager dbManager = new DBManager();

    public SearchFrame(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
        setTitle("Szukaj smycz");
        frameHelper.setSettings();
        add(initComponents(), BorderLayout.CENTER);
    }

    private JComponent initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());

        frameHelper.addFields(panel);

        frameHelper.addOKBtn(panel, "Szukaj");
        frameHelper.getOkBtn().addActionListener(new ButtonHandler());
        frameHelper.getOkBtn().setEnabled(true);
        frameHelper.addCancelBtn(panel);
        frameHelper.getCancelBtn().addActionListener(new ButtonHandler());

        return panel;
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == frameHelper.getOkBtn()) {
                Leash leash = new Leash(frameHelper.getImageNameField().getText(), frameHelper.getTextField().getText(),
                        frameHelper.getSizeField().getText(), frameHelper.getColorField().getText());

                new TableModelHelper(tableModel).updateTable(dbManager.search(leash));

                setVisible(false);
            } else if (source == frameHelper.getCancelBtn()) {
                setVisible(false);
            }
        }


    }
}
