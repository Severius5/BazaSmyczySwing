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
    private JTextField descField;
    private java.util.List<JTextField> fieldsList = new ArrayList<>();
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

    public JTextField getDescField() {
        return descField;
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
        addDescField(panel);
    }

    public void addCancelBtn(JPanel panel) {
        GridBagConstraints constraints;
        constraints = new GridBagConstraints();
        constraints.gridy = 5;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        cancelBtn = new JButton("Anuluj");
        panel.add(cancelBtn, constraints);
    }

    public void addOKBtn(JPanel panel, String title) {
        GridBagConstraints constraints;
        constraints = new GridBagConstraints();
        constraints.gridy = 5;
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

    public boolean isLeashInvalid(Leash leash) {
        if (dbManager.isImageNameExists(leash)) {
            if (dbManager.isLeashExists(leash)) {
                JOptionPane.showMessageDialog(null, "Smycz istnieje w bazie",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Podana nazwa obrazu juz istnieje",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
            return true;
        }
        return false;
    }

    public Leash newLeashWithFields(final Long ID) {

        final String imageName = getImageNameField().getText();
        final String text = getTextField().getText();
        final String size = getSizeField().getText();
        final String color = getColorField().getText();
        final String desc = getDescField().getText();

        return new Leash(ID, imageName, text, size, color, desc);
    }

    public Leash newLeashWithFields() {

        final String imageName = getImageNameField().getText();
        final String text = getTextField().getText();
        final String size = getSizeField().getText();
        final String color = getColorField().getText();
        final String desc = getDescField().getText();

        return new Leash(imageName, text, size, color, desc);
    }

    public Object[] newObjectWithFields(final Long ID, final Leash leash){
        return new Object[]{ID, leash.getImageName(), leash.getText(), leash.getSize(), leash.getColor(), leash.getDesc()};
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
        fieldsList.add(colorField);
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
        fieldsList.add(sizeField);
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
        fieldsList.add(textField);
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
        fieldsList.add(imageNameField);
    }

    private void addDescField(JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 4;
        panel.add(new JLabel("Description:"), constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.weightx = 1;
        descField = new JTextField();

        panel.add(descField, constraints);
    }

    private boolean checkForEmptyFields() {
        boolean result = true;
        for (JTextField field : fieldsList) {
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
