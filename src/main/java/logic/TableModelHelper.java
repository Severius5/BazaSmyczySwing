package logic;

import entity.Leash;
import utils.Column;

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
            protected void process(List<Leash> leashes) {
                for (Leash leash : leashes) {
                    Object[] leashObject = new Object[6];
                    leashObject[Column.ID] = leash.getID();
                    leashObject[Column.imageName] = leash.getImageName();
                    leashObject[Column.text] = leash.getText();
                    leashObject[Column.size] = leash.getSize();
                    leashObject[Column.color] = leash.getColor();
                    leashObject[Column.desc] = leash.getDesc();
                    tableModel.addRow(leashObject);
                }
            }
        };
        worker.execute();
    }
}
