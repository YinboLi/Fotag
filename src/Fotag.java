import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by yinboli on 3/6/16.
 */

interface IView {
    public void updateView();
}

public class Fotag {
    private ArrayList<IView> views = new ArrayList<IView>();

    private ArrayList<ImageModel> imageModels = new ArrayList<ImageModel>();

    private String savedInfo = "";

    private String layout = "list";

    private int  filter = 0;

    private void saveImageInfo() {
        savedInfo = "";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("imageList.txt"), "utf-8"))) {
            for (int i = 0; i < imageModels.size(); i++) {
                savedInfo = savedInfo + imageModels.get(i).getName() + "\n";
            }
            writer.write(savedInfo);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadImageInfo() {
        File f = new File("imageList.txt");
        try {
            f.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader("imageList.txt"));
            String imageName;
            while ((imageName = reader.readLine()) != null) {
                Image img = ImageIO.read(getClass().getResource(imageName));
                ImageModel imageModel = new ImageModel();
                imageModel.setName(imageName);
                imageModel.setImg(img);
                this.addImage(imageModel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addImage(ImageModel imageModel) throws FileNotFoundException {
        imageModels.add(imageModel);
        saveImageInfo();
        redrawMainWin();
    }

    public ArrayList<ImageModel> getImageModels() {
        return imageModels;
    }

    public void addView(IView view) {
        views.add(view);
        view.updateView();
    }

    public void setLayout(String layout_) {
        layout = layout_;
        notifyObservers();
    }

    public String getLayout() {
        return layout;
    }

    public void setFilter(int filter_) {
        filter = filter_;
        notifyObservers();
    }

    public int getFilter() {
        return filter;
    }

    public void redrawToolBar() {
        views.get(0).updateView();
    }

    public void redrawMainWin() {
        views.get(1).updateView();
    }

    public void redraw() {
        notifyObservers();
    }

    // notify the IView observer
    private void notifyObservers() {
        this.views.forEach(IView::updateView);
    }
}
