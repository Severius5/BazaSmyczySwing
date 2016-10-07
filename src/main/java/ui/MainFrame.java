package ui;

import logic.DBManager;
import logic.TableModel;
import logic.TableModelHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private DBManager dbManager = new DBManager();
    private JTable table;
    private DefaultTableModel tableModel;
    private Action addAction;
    private Action editAction;
    private Action deleteAction;
    private Action refreshAction;
    private Action searchAction;

    private String[] columnNames = {"ID", "Image Name", "Text", "Size", "Color"};

    public MainFrame() {
        initActions();
        initComponents();
    }

    private void initComponents() {
        add(createTable(), BorderLayout.CENTER);
        add(createToolbar(), BorderLayout.PAGE_START);
    }

    private void initActions() {
        refreshAction = new AbstractAction("Refresh") {
            @Override
            public void actionPerformed(final ActionEvent e) {
                refreshData();
            }
        };

        deleteAction = new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLeash();
            }
        };

        editAction = new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editLeash();
            }
        };

        addAction = new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLeash();
            }
        };

        searchAction = new AbstractAction("Search") {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchLeash();
            }
        };
    }

    private JComponent createTable() {
        tableModel = new TableModel();
        tableModel.setColumnIdentifiers(columnNames);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        return new JScrollPane(table);
    }

    private JToolBar createToolbar() {
        final JToolBar toolBar = new JToolBar();
        toolBar.add(refreshAction);
        toolBar.add(addAction);
        toolBar.add(editAction);
        toolBar.addSeparator();
        toolBar.add(deleteAction);
        toolBar.addSeparator();
        toolBar.add(searchAction);
        return toolBar;
    }

    private void refreshData() {
       new TableModelHelper(tableModel).updateTable(dbManager.getLeashes());
    }

    private void deleteLeash() {
        final int row = table.getSelectedRow();
        if (row == -1)
            return;
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete?", "Delete", JOptionPane.YES_NO_OPTION)) {
            Long ID = (Long) tableModel.getValueAt(row, 0);
            tableModel.removeRow(row);
            dbManager.delete(ID);
        }
    }

    private void editLeash() {
        final int row = table.getSelectedRow();
        if (row != -1)
            new EditFrame(tableModel, row);
    }

    private void addLeash() {
        new AddFrame(tableModel);
    }

    private void searchLeash(){
        new SearchFrame(tableModel);
    }
}
