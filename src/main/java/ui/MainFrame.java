package ui;

import logic.DBManager;
import logic.ImageManager;
import utils.TableModel;
import logic.TableModelHelper;
import utils.Column;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private DBManager dbManager = new DBManager();
    private JTable table;
    private DefaultTableModel tableModel;
    private Action addAction;
    private Action editAction;
    private Action deleteAction;
    private Action refreshAction;
    private Action searchAction;

    private String[] columnNames = {"ID", "Image Name", "Text", "Size", "Color", "Desc"};

    public MainFrame() {
        setTitle("Baza smyczy");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
        setVisible(true);

        initActions();
        initComponents();
        initMouseListener();
    }

    private void initComponents() {
        add(createTable(), BorderLayout.CENTER);
        add(createToolbar(), BorderLayout.PAGE_START);
        add(createCounter(), BorderLayout.PAGE_END);
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

    private void initMouseListener() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (table.getSelectedColumn() == 1) {
                        int row = table.getSelectedRow();
                        String imageName = (String) tableModel.getValueAt(row, Column.imageName);
                        new ImageFrame(imageName);
                    }
                }
            }
        });
    }

    private JComponent createTable() {
        tableModel = new TableModel();
        tableModel.setColumnIdentifiers(columnNames);
        table = new JTable(tableModel);
        table = setColumnsWidth(table);
        return new JScrollPane(table);
    }

    private JTable setColumnsWidth(JTable table) {
        table.getColumnModel().getColumn(Column.ID).setPreferredWidth(5);
        table.getColumnModel().getColumn(Column.text).setPreferredWidth(300);
        table.getColumnModel().getColumn(Column.imageName).setPreferredWidth(30);
        return table;
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

    private JComponent createCounter() {
        JTextField field = new JTextField();
        field.setEditable(false);
        tableModel.addTableModelListener(e -> field.setText(String.valueOf(tableModel.getRowCount())));
        return field;
    }

    private void refreshData() {
        new TableModelHelper(tableModel).updateTable(dbManager.getLeashes());
    }

    private void deleteLeash() {
        final int row = table.getSelectedRow();
        if (row == -1)
            return;
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete?", "Delete", JOptionPane.YES_NO_OPTION)) {
            Long ID = (Long) tableModel.getValueAt(row, Column.ID);
            String imageName = (String) tableModel.getValueAt(row, Column.imageName);
            new ImageManager().deleteImage(imageName);
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

    private void searchLeash() {
        new SearchFrame(tableModel);
    }

}
