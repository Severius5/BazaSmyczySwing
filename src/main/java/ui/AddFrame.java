package ui;

import entity.Leash;
import logic.DBManager;
import logic.ImageManager;
import utils.Consts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddFrame extends JFrame
{

    private DefaultTableModel tableModel;
    private FrameHelper frameHelper = new FrameHelper(this);

    public AddFrame(DefaultTableModel tableModel)
    {
        this.tableModel = tableModel;
        setTitle("Dodaj smycz");
        frameHelper.setSettings();
        add(initComponents(), BorderLayout.CENTER);
    }

    private JComponent initComponents()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        frameHelper.addFields(panel);
        frameHelper.addFieldsListener();

        frameHelper.addOKBtn(panel, "Dodaj");
        frameHelper.getOkBtn().addActionListener(new ButtonHandler());
        frameHelper.addCancelBtn(panel);
        frameHelper.getCancelBtn().addActionListener(new ButtonHandler());

        return panel;
    }

    private class ButtonHandler implements ActionListener
    {
        DBManager dbManager = new DBManager();

        @Override
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();

            if (source == frameHelper.getOkBtn())
            {
                Leash leash = frameHelper.newLeashWithFields();

                if (frameHelper.isLeashInvalid(leash) || !isSuccessCopy())
                    return;

                Long ID = dbManager.add(leash);
                Object[] newRow = frameHelper.newObjectWithFields(ID, leash);
                tableModel.addRow(newRow);
                setVisible(false);

            }
            else if (source == frameHelper.getCancelBtn())
            {
                setVisible(false);
            }
        }

        private boolean isSuccessCopy()
        {
            File file = frameHelper.getFile();
            if (file != null)
            {
                String imageName = frameHelper.getImageNameField().getText();
                try
                {
                    new ImageManager().copyImage(file, imageName);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    FrameHelper.showError(null, Consts.cantCopy);
                    return false;
                }
                return true;
            }
            else
            {
                FrameHelper.showWarning(null, Consts.selectFile);
                return false;
            }
        }

    }

}
