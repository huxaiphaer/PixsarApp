package company.override.huzykamz.pixsar.model;

/**
 * Created by HUZY_KAMZ on 12/31/2016.
 */

public class Advert {

    private String ImageAdvert;
    private String  Title;


    public Advert() {

        // left out for firebase ...

    }


    public String getImageAdvert() {
        return ImageAdvert;
    }

    public void setImageAdvert(String imageAdvert) {
        ImageAdvert = imageAdvert;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
