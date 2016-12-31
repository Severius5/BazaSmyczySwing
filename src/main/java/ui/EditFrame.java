package ui;

import entity.Leash;
import logic.DBManager;
import logic.ImageManager;
import utils.Column;
import utils.Consts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EditFrame extends JFrame {

    private DefaultTableModel tableModel;
    private int row;
    private FrameHelper frameHelper = new FrameHelper(this);
    private String oldImageName;

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
        setTexts();
        frameHelper.addFieldsListener();

        frameHelper.addOKBtn(panel, "Zapisz");
        frameHelper.getOkBtn().addActionListener(new ButtonHandler());
        frameHelper.addCancelBtn(panel);
        frameHelper.getCancelBtn().addActionListener(new ButtonHandler());

        return panel;
    }

    private void setTexts() {
        frameHelper.getImageNameField().setText(getValue(Column.imageName));
        frameHelper.getTextField().setText(getValue(Column.text));
        frameHelper.getSizeField().setText(getValue(Column.size));
        frameHelper.getColorField().setText(getValue(Column.color));
        frameHelper.getDescField().setText(getValue(Column.desc));

        oldImageName = frameHelper.getImageNameField().getText();
    }

    private String getValue(int column) {
        return (String) tableModel.getValueAt(row, column);
    }

    private Long getID() {
        return (Long) tableModel.getValueAt(row, Column.ID);
    }

    private class ButtonHandler implements ActionListener {
        private String newImageName;

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == frameHelper.getOkBtn()) {
                final Long ID = getID();
                newImageName = frameHelper.getImageNameField().getText();
                final Leash leash = frameHelper.newLeashWithFields(ID);
                final Object[] newRow = frameHelper.newObjectWithFields(ID, leash);
                DBManager dbManager = new DBManager();

                if (dbManager.isLeashExists(leash)) {
                    FrameHelper.showWarning(null, Consts.leashExists);
                    return;
                }
                if (isImageNameChanged()) {
                    if (dbManager.isImageNameExists(leash)) {
                        FrameHelper.showWarning(null, Consts.leashImageExists);
                        return;
                    }
                    try {
                        new ImageManager().renameImageFile(oldImageName, newImageName);
                    } catch (IOException e1) {
                        FrameHelper.showError(null, Consts.cantRename);
                        return;
                    }
                }

                new DBManager().edit(leash);
                tableModel.removeRow(row);
                tableModel.insertRow(row, newRow);
                setVisible(false);
            } else if (source == frameHelper.getCancelBtn()) {
                setVisible(false);
            }
        }

        private boolean isImageNameChanged() {
            return !oldImageName.equals(newImageName);
        }
    }


}
