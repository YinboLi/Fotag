import java.awt.*;

/**
 * Created by yinboli on 3/6/16.
 */
public class ImageModel {
    private Image img;
    private String name;
    private String date;
    private int rating = 0;

    public void setImg(Image img_) {
        img = img_;
    }

    public Image getImg() {
        return img;
    }

    public void setName(String name_) {
        name = name_;
    }

    public String getName() {
        return name;
    }

    public void setDate(String date_) {
        date = date_;
    }

    public String getDate() {
        return date;
    }

    public void setRating(int rating_) {
        rating = rating_;
    }

    public int getRating() {
        return rating;
    }
}
