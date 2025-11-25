package View;

import service.EntregaService;

import javax.swing.*;
import java.awt.*;

public class EntregaTelaEntregador extends JFrame {
    private EntregaService entregaService = new EntregaService();
    private int idEntrega;
    private JTextArea txt = new JTextArea();

    public EntregaTelaEntregador(int idEntrega) {
        this.idEntrega = idEntrega;
        setTitle("Entrega - " + idEntrega);
        setSize(480, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txt.setEditable(false);
        add(new JScrollPane(txt), BorderLayout.CENTER);

        JPanel b = new JPanel();
        JButton fechar = new JButton("Fechar");
        fechar.addActionListener(e -> dispose());
        b.add(fechar);
        add(b, BorderLayout.SOUTH);


    }


    }

