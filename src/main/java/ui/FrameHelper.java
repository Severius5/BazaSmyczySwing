package ui;

import entity.Leash;
import logic.DBManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class FrameHelper {

    private JFrame frame;
    private JButton cancelBtn;
    private JButton okBtn;
    private JTextField imageNameField;
    private JTextField textField;
    private JTextField sizeField;
    private JTextField colorField;
    private java.util.List<JTextField> list = new ArrayList<>();
    private DBManager dbManager = new DBManager();


    public JButton getOkBtn() {
        return okBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public JTextField getImageNameField() {
        return imageNameField;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JTextField getSizeField() {
        return sizeField;
    }

    public JTextField getColorField() {
        return colorField;
    }

    public FrameHelper(JFrame frame) {
        this.frame = frame;
    }

    public void setSettings() {
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void addFields(JPanel panel) {
        addImageNameField(panel);
        addTextField(panel);
        addSizeField(panel);
        addColorField(panel);
    }

    public void addCancelBtn(JPanel panel) {
        GridBagConstraints constraints;
        constraints = new GridBagConstraints();
        constraints.gridy = 4;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        cancelBtn = new JButton("Anuluj");
        panel.add(cancelBtn, constraints);
    }

    public void addOKBtn(JPanel panel, String title) {
        GridBagConstraints constraints;
        constraints = new GridBagConstraints();
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        okBtn = new JButton(title);
        okBtn.setEnabled(false);
        panel.add(okBtn, constraints);
    }

    public void addFieldsListener() {
        textField.getDocument().addDocumentListener(new FieldListener());
        imageNameField.getDocument().addDocumentListener(new FieldListener());
        sizeField.getDocument().addDocumentListener(new FieldListener());
        colorField.getDocument().addDocumentListener(new FieldListener());
    }

    public boolean isImageNameExists(Leash leash) {
        if (dbManager.isImageNameExists(leash)) {
            JOptionPane.showMessageDialog(null, "Podana nazwa obrazu juz istnieje",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private void addColorField(JPanel panel) {
        GridBagConstraints constraints;
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 3;
        panel.add(new JLabel("Color:"), constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 1;
        colorField = new JTextField();

        panel.add(colorField, constraints);
        list.add(colorField);
    }

    private void addSizeField(JPanel panel) {
        GridBagConstraints constraints;
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 2;
        panel.add(new JLabel("Size:"), constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1;
        sizeField = new JTextField();

        panel.add(sizeField, constraints);
        list.add(sizeField);
    }

    private void addTextField(JPanel panel) {
        GridBagConstraints constraints;
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 1;
        panel.add(new JLabel("Text:"), constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        textField = new JTextField();

        panel.add(textField, constraints);
        list.add(textField);
    }

    private void addImageNameField(JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Image name:"), constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        imageNameField = new JTextField();

        panel.add(imageNameField, constraints);
        list.add(imageNameField);
    }


    private boolean checkForEmptyFields() {
        boolean result = true;
        for (JTextField field : list) {
            if (field.getText().trim().length() == 0) {
                field.setBackground(Color.RED);
                result = false;
            } else {
                field.setBackground(Color.WHITE);
            }
        }
        return result;
    }

    private class FieldListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            okBtn.setEnabled(checkForEmptyFields());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            okBtn.setEnabled(checkForEmptyFields());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            okBtn.setEnabled(checkForEmptyFields());
        }
    }
}
