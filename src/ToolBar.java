import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by yinboli on 3/6/16.
 */
public class ToolBar extends JToolBar implements IView {
    private Fotag fotag;

    private JButton starBtn1, starBtn2, starBtn3, starBtn4, starBtn5;

    final private int starSize = 13;

    ToolBar(Fotag fotag_) {
        fotag = fotag_;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JButton gridBtn, listBtn, loadBtn;
        JPanel starPane;

        // Grid
        gridBtn = new JButton();
        gridBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fotag.setLayout("grid");
            }
        });

        Image gridImg = null;
        try {
            Image temp = ImageIO.read(getClass().getResource("grid.png"));
            gridImg = temp.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gridBtn.setIcon(new ImageIcon(gridImg));
        this.add(gridBtn);
        this.add(Box.createHorizontalStrut(5));


        // List
        listBtn = new JButton();
        listBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fotag.setLayout("list");
            }
        });

        Image listImg = null;
        try {
            Image temp = ImageIO.read(getClass().getResource("list.png"));
            listImg = temp.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listBtn.setIcon(new ImageIcon(listImg));
        this.add(listBtn);
        this.add(Box.createHorizontalGlue());


        // Load
        loadBtn = new JButton();
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageModel imageModel = new ImageModel();
                Image img = null;
                String name = "";
                String date = "";
                File f = null;

                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Load Image");

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    f = fc.getSelectedFile();
                    name = f.getName();
                    try {
                        img = ImageIO.read(f);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    Path path = Paths.get(f.getAbsolutePath());
                    try {
                        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                        date = attr.creationTime().toString();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    imageModel.setImg(img);
                    imageModel.setName(name);
                    imageModel.setDate(date);

                    try {
                        fotag.addImage(imageModel);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        Image loadImg = null;
        try {
            Image temp = ImageIO.read(getClass().getResource("load.png"));
            loadImg = temp.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadBtn.setIcon(new ImageIcon(loadImg));
        this.add(loadBtn);
        this.add(Box.createHorizontalStrut(15));

        // Star
        starPane = new JPanel();
        starPane.setLayout(new BoxLayout(starPane, BoxLayout.X_AXIS));

        starBtn1 = new JButton();
        starBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fotag.setFilter(1);
                fotag.redraw();
            }
        });

        starBtn2 = new JButton();
        starBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fotag.setFilter(2);
                fotag.redraw();
            }
        });

        starBtn3 = new JButton();
        starBtn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fotag.setFilter(3);
                fotag.redraw();
            }
        });

        starBtn4 = new JButton();
        starBtn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fotag.setFilter(4);
                fotag.redraw();
            }
        });

        starBtn5 = new JButton();
        starBtn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fotag.setFilter(5);
                fotag.redraw();
            }
        });

        Image whiteStar = null;
        try {
            Image tempWhiteImg = ImageIO.read(getClass().getResource("whiteStar.png"));
            whiteStar = tempWhiteImg.getScaledInstance(starSize, starSize, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        starBtn1.setIcon(new ImageIcon(whiteStar));
        starBtn2.setIcon(new ImageIcon(whiteStar));
        starBtn3.setIcon(new ImageIcon(whiteStar));
        starBtn4.setIcon(new ImageIcon(whiteStar));
        starBtn5.setIcon(new ImageIcon(whiteStar));

        starPane.add(starBtn1);
        starPane.add(starBtn2);
        starPane.add(starBtn3);
        starPane.add(starBtn4);
        starPane.add(starBtn5);
        this.add(starPane);
        this.add(Box.createHorizontalStrut(5));

        // Clear
        JButton clearBtn = new JButton("Clear");
        this.add(clearBtn);
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fotag.setFilter(0);
            }
        });
    }


    public void updateView() {
        Image whiteStar = null;
        Image yellowStar = null;
        try {
            Image tempWhiteImg = ImageIO.read(getClass().getResource("whiteStar.png"));
            whiteStar = tempWhiteImg.getScaledInstance(starSize, starSize, Image.SCALE_SMOOTH);
            Image tempYellowImg = ImageIO.read(getClass().getResource("yellowStar.png"));
            yellowStar = tempYellowImg.getScaledInstance(starSize, starSize, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fotag.getFilter() == 0) {
            starBtn1.setIcon(new ImageIcon(whiteStar));
            starBtn2.setIcon(new ImageIcon(whiteStar));
            starBtn3.setIcon(new ImageIcon(whiteStar));
            starBtn4.setIcon(new ImageIcon(whiteStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (fotag.getFilter() == 1) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(whiteStar));
            starBtn3.setIcon(new ImageIcon(whiteStar));
            starBtn4.setIcon(new ImageIcon(whiteStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (fotag.getFilter() == 2) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(yellowStar));
            starBtn3.setIcon(new ImageIcon(whiteStar));
            starBtn4.setIcon(new ImageIcon(whiteStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (fotag.getFilter() == 3) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(yellowStar));
            starBtn3.setIcon(new ImageIcon(yellowStar));
            starBtn4.setIcon(new ImageIcon(whiteStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (fotag.getFilter() == 4) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(yellowStar));
            starBtn3.setIcon(new ImageIcon(yellowStar));
            starBtn4.setIcon(new ImageIcon(yellowStar));
            starBtn5.setIcon(new ImageIcon(whiteStar));
        } else if (fotag.getFilter() == 5) {
            starBtn1.setIcon(new ImageIcon(yellowStar));
            starBtn2.setIcon(new ImageIcon(yellowStar));
            starBtn3.setIcon(new ImageIcon(yellowStar));
            starBtn4.setIcon(new ImageIcon(yellowStar));
            starBtn5.setIcon(new ImageIcon(yellowStar));
        }
    }
}
