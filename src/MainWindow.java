import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by yinboli on 3/6/16.
 */
public class MainWindow extends JScrollPane implements IView {
    private Fotag fotag;
    private JFrame frame;
    private int frameWidth;
    private int frameHeight;
    private int colPerLine = 3;

    MainWindow(Fotag fotag_, JFrame frame_) {
        fotag = fotag_;
        frame = frame_;
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                frameWidth = frame.getWidth();
                if (fotag.getLayout() == "grid" ) {
                    if (frameWidth < 600 && colPerLine != 1) {
                        colPerLine = 1;
                        gridLayout();
                    } else if (frameWidth < 700 && frameWidth > 600 && colPerLine != 2) {
                        colPerLine = 2;
                        gridLayout();
                    } else if (frameWidth > 700 && colPerLine != 3) {
                        colPerLine = 3;
                        gridLayout();
                    }
                }
            }
        });
    }

    private JLabel createImgLable(ImageModel imgModel) {
        Image img = imgModel.getImg();
        Image scaledImg = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);

        JLabel imgLabel = new JLabel();
        imgLabel.setIcon(new ImageIcon(scaledImg));

        imgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame frame = new JFrame(imgModel.getName());
                Container contentPane = frame.getContentPane();

                Image largeImg = img.getScaledInstance(600,450, Image.SCALE_AREA_AVERAGING);
                JLabel largeImgLabel = new JLabel();
                largeImgLabel.setIcon(new ImageIcon(largeImg));

                frame.add(largeImgLabel);
                frame.pack();
                frame.setLocation(100,100);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });

        return imgLabel;
    }

    private void gridLayout() {
        ArrayList<ImageModel> imageModels = fotag.getImageModels();
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel horiPane = null;
        int i = 0;
        int imgAdded = 0;
        while (i < imageModels.size()) {
            ImageModel imgModel = imageModels.get(i);

            if (imgAdded % colPerLine == 0) { // colPerLine = 1, 2, 3
                horiPane = new JPanel();
                horiPane.setLayout(new BoxLayout(horiPane, BoxLayout.X_AXIS));
                horiPane.setAlignmentX(Box.LEFT_ALIGNMENT);
            }

            if (fotag.getFilter() == 0 || (fotag.getFilter() != 0 && fotag.getFilter() <= imgModel.getRating())) {
                JPanel contentPane = new JPanel();
                contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
                contentPane.add(Box.createVerticalStrut(10));

                // Image
                JLabel imgLable = createImgLable(imgModel);
                contentPane.add(imgLable);
                contentPane.add(Box.createVerticalStrut(10));

                // Name
                JLabel nameLable = new JLabel(imgModel.getName());
                contentPane.add(nameLable);
                contentPane.add(Box.createVerticalStrut(10));

                // Date
                JLabel dateLable = new JLabel(imgModel.getDate());
                contentPane.add(dateLable);
                contentPane.add(Box.createVerticalStrut(10));

                // Star
                JPanel starPane = createStarPane(imgModel);
                contentPane.add(starPane);

                // add content
                horiPane.add(contentPane);
                imgAdded++;
            }
            i++;

            pane.add(horiPane);
        }

        this.add(pane);
        this.setViewportView(pane);
        this.setVisible(true);
    }

    private void listLayout() {
        ArrayList<ImageModel> imageModels = fotag.getImageModels();
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(Box.createVerticalStrut(40));


        for (int i = 0; i < imageModels.size(); i++) {
            ImageModel imgModel =  imageModels.get(i);

            if (fotag.getFilter() == 0 || (fotag.getFilter() != 0 && fotag.getFilter() <= imgModel.getRating())) {

                JPanel horiPane = new JPanel();
                horiPane.setLayout(new BoxLayout(horiPane, BoxLayout.LINE_AXIS));
                horiPane.setAlignmentX(Component.LEFT_ALIGNMENT);
                horiPane.add(Box.createHorizontalStrut(20));

                // Image
                JLabel imgLable = createImgLable(imgModel);
                horiPane.add(imgLable);
                horiPane.add(Box.createHorizontalStrut(40));

                // Image Info
                JPanel infoPane = new JPanel();
                infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
                JLabel nameLable = new JLabel(imgModel.getName());
                infoPane.add(nameLable);
                infoPane.add(Box.createVerticalStrut(10));
                JLabel dateLable = new JLabel(imgModel.getDate());
                infoPane.add(dateLable);
                infoPane.add(Box.createVerticalStrut(10));
                horiPane.add(infoPane);

                // Star
                JPanel starPane = createStarPane(imgModel);
                infoPane.add(starPane);

                pane.add(horiPane);
                pane.add(Box.createVerticalStrut(40));
            }
        }
        this.add(pane);
        this.setViewportView(pane);
        this.setVisible(true);
    }

    private JPanel createStarPane(ImageModel imgModel) {
        JPanel starPane = new JPanel();
        starPane.setLayout(new BoxLayout(starPane, BoxLayout.X_AXIS));
        starPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton starBtn1 = new JButton();
        starBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgModel.setRating(1);
                fotag.redrawMainWin();
            }
        });

        JButton starBtn2 = new JButton();
        starBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgModel.setRating(2);
                fotag.redrawMainWin();
            }
        });

        JButton starBtn3 = new JButton();
        starBtn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgModel.setRating(3);
                fotag.redrawMainWin();
            }
        });

        JButton starBtn4 = new JButton();
        starBtn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgModel.setRating(4);
                fotag.redrawMainWin();
            }
        });

        JButton starBtn5 = new JButton();
        starBtn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgModel.setRating(5);
                fotag.redrawMainWin();
            }
        });

        JButton clearBtn = new JButton("clear");
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgModel.setRating(0);
                fotag.redrawMainWin();
            }
        });

        Image whiteStar = null;
        Image yellowStar = null;
        try {
            Image tempWhiteImg = ImageIO.read(getClass().getResource("whiteStar.png"));
            whiteStar = tempWhiteImg.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
            Image tempYellowImg = ImageIO.read(getClass().getResource("yellowStar.png"));
            yellowStar = tempYellowImg.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imgModel.getRating() == 0) {
            starBtn1.setIcon(new ImageIcon(whiteStar));
            starBtn2.setIcon(new ImageIcon(whiteStar));
            starBtn3.setIcon(new ImageIcon(whiteStar));
            starBtn4.setIcon(new ImageIcon(whiteStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (imgModel.getRating() == 1) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(whiteStar));
            starBtn3.setIcon(new ImageIcon(whiteStar));
            starBtn4.setIcon(new ImageIcon(whiteStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (imgModel.getRating() == 2) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(yellowStar));
            starBtn3.setIcon(new ImageIcon(whiteStar));
            starBtn4.setIcon(new ImageIcon(whiteStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (imgModel.getRating() == 3) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(yellowStar));
            starBtn3.setIcon(new ImageIcon(yellowStar));
            starBtn4.setIcon(new ImageIcon(whiteStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (imgModel.getRating() == 4) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(yellowStar));
            starBtn3.setIcon(new ImageIcon(yellowStar));
            starBtn4.setIcon(new ImageIcon(yellowStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (imgModel.getRating() == 5) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(yellowStar));
            starBtn3.setIcon(new ImageIcon(yellowStar));
            starBtn4.setIcon(new ImageIcon(yellowStar));
            starBtn5.setIcon(new ImageIcon(yellowStar));
        }

        starPane.add(starBtn1);
        starPane.add(starBtn2);
        starPane.add(starBtn3);
        starPane.add(starBtn4);
        starPane.add(starBtn5);
        starPane.add(clearBtn);

        return  starPane;
    }

    public void updateView() {
        if (fotag.getLayout() == "list") {
            listLayout();
        } else if (fotag.getLayout() == "grid") {
            gridLayout();
        } else  {
            System.out.println("List and grid layout only!");
        }
    }
}
