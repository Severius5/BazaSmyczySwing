package ui;

import entity.Leash;
import logic.DBManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditFrame extends JFrame {

    private DefaultTableModel tableModel;
    private int row;
    private FrameHelper frameHelper = new FrameHelper(this);

    public EditFrame(DefaultTableModel tableModel, int row) {
        this.row = row;
        this.tableModel = tableModel;
        setTitle("Edytuj smycz");
        frameHelper.setSettings();
        add(initComponents(), BorderLayout.CENTER);
    }

    private JComponent initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());

        frameHelper.addFields(panel);
        addTexts();
        frameHelper.addFieldsListener();

        frameHelper.addOKBtn(panel, "Zapisz");
        frameHelper.getOkBtn().addActionListener(new ButtonHandler());
        frameHelper.addCancelBtn(panel);
        frameHelper.getCancelBtn().addActionListener(new ButtonHandler());

        return panel;
    }

    private void addTexts() {
        frameHelper.getImageNameField().setText(getValue(1));
        frameHelper.getTextField().setText(getValue(2));
        frameHelper.getSizeField().setText(getValue(3));
        frameHelper.getColorField().setText(getValue(4));
    }

    private String getValue(int column) {
        return (String) tableModel.getValueAt(row, column);
    }

    private Long getID() {
        return (Long) tableModel.getValueAt(row, 0);
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == frameHelper.getOkBtn()) {
                final Long ID = getID();
                final Leash leash = new Leash(ID, frameHelper.getImageNameField().getText(), frameHelper.getTextField().getText(),
                        frameHelper.getSizeField().getText(), frameHelper.getColorField().getText());
                final Object[] newRow = {ID, leash.getImageName(), leash.getText(), leash.getSize(), leash.getColor()};

                if (frameHelper.isImageNameExists(leash))
                    return;

                new DBManager().edit(leash);
                tableModel.removeRow(row);
                tableModel.insertRow(row, newRow);
                setVisible(false);
            } else if (source == frameHelper.getCancelBtn()) {
                setVisible(false);
            }
        }
    }


}
