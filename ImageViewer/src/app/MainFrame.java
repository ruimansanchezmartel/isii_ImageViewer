package app;

import control.Command;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.ImageDisplay;
import ui.swing.SwingImageDisplay;

public class MainFrame extends JFrame {

    private Map<String, Command> commands = new HashMap<>();
    private Map<String, String> buttonLabels = new HashMap<>();
    private ImageDisplay imageDisplay;

    public MainFrame() {
        initButtonLabels();
        this.setTitle("Image Viewer");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(image());
        this.add(toolbar(), BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void add(Command command) {
        commands.put(command.name(), command);
    }

    public ImageDisplay getImageDisplay() {
        return imageDisplay;
    }

    private Component image() {
        SwingImageDisplay swingImageDisplay = new SwingImageDisplay();
        imageDisplay = swingImageDisplay;
        return swingImageDisplay;
    }

    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.add(button("previous"));
        panel.add(button("next"));
        return panel;
    }

    private Component button(String name) {
        JButton button = new JButton(buttonLabels.get(name));
        button.addActionListener(execute(name));
        return button;
    }

    private ActionListener execute(String name) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(name).execute();
            }
        };
    }

    private void initButtonLabels() {
        buttonLabels.put("previous", "◄");
        buttonLabels.put("next", "►");
    }
}
