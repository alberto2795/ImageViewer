package application;

import control.Command;
import control.NextImageCommand;
import control.PrevImageCommand;
import model.Image;
import view.ImageDisplay;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application extends JFrame{
    private Map<String, Command> commands = new HashMap<>();
    private ImageDisplay imageDisplay;
    private final static String imageRoute = "C:\\Users\\Alberto_2\\Pictures\\Fondos de escritorio";
    
    public static void main(String[] args) {
        new Application().setVisible(true);
    }    
    
    public Application() {
        deployUI();
        createCommands();
    }
    
    private void deployUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(components());
        this.setPreferredSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
       
        pack();
    }
    
    private JPanel toolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(prevButton());
        panel.add(nextButton());
        return panel;
    }

    private void createCommands() {
        commands.put("Next", new NextImageCommand(imageDisplay));
        commands.put("Prev", new PrevImageCommand(imageDisplay));
    }

    private ImagePanel imagePanel() {
        ImagePanel panel = new ImagePanel (firstImage());
        this.imageDisplay = panel;
        return panel;
    }

    private Image firstImage() {
        FileImageReader fileReader = new FileImageReader(imageRoute);
        return fileReader.read();
    }

    private JButton nextButton() {
        JButton button = new JButton(">");
        button.addActionListener(doCommand("Next"));
        return button;
    }

    private JButton prevButton()  {
        JButton button = new JButton("<");
        button.addActionListener(doCommand("Prev"));
        return button;
    }

    private ActionListener doCommand(String command) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(command).execute();
            }
        };
    }    

    private JPanel components() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(toolbar(),BorderLayout.SOUTH);
        panel.add(imagePanel(),BorderLayout.CENTER);
        return panel;
    }

}
