package logic;

import entity.Leash;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TableModelHelper {
    private DefaultTableModel tableModel;

    public TableModelHelper(DefaultTableModel tableModel){
        this.tableModel = tableModel;
    }

    public void updateTable(final List<Leash> leashes){
        tableModel.setRowCount(0);
        SwingWorker<Void, Leash> worker = new SwingWorker<Void, Leash>() {
            @Override
            protected Void doInBackground() throws Exception {
                leashes.forEach(this::publish);
                return null;
            }

            @Override
            protected void process(java.util.List<Leash> leashes) {
                for (Leash leash : leashes) {
                    Object[] leashObject = new Object[5];
                    leashObject[0] = leash.getID();
                    leashObject[1] = leash.getImageName();
                    leashObject[2] = leash.getText();
                    leashObject[3] = leash.getSize();
                    leashObject[4] = leash.getColor();
                    tableModel.addRow(leashObject);
                }
            }
        };
        worker.execute();
    }
}
