package logic;

import ui.MainFrame;
import utils.Factory;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Startup {
    public static void main(String[] args) {
        Factory.getSessionFactory().openSession();

        SwingUtilities.invokeLater(() -> new MainFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Factory.getSessionFactory().close();
            }
        }));
    }

}
