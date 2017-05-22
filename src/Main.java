import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yinboli on 3/6/16.
 */
public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fotag");

        Fotag fotag = new Fotag();

        ToolBar toolBar = new ToolBar(fotag);
        fotag.addView(toolBar);

        MainWindow mainWindow = new MainWindow(fotag, frame);
        fotag.addView(mainWindow);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(mainWindow, BorderLayout.CENTER);

        fotag.loadImageInfo();

        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(500, 300));
        frame.setMaximumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

}
