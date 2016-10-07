package logic;

import ui.MainFrame;
import utils.Factory;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Startup {
    public static void main(String[] args) {
        Factory.getSessionFactory().openSession();

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setTitle("Baza smyczy");
            mainFrame.setSize(800, 600);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);

            mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Factory.getSessionFactory().close();
                }
            });
        });
    }

}
