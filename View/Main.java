package View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EscolhaPerfil frame = new EscolhaPerfil();
            frame.setVisible(true);
        });
    }
}
