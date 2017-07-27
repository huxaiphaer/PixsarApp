package company.override.huzykamz.pixsar.model;

/**
 * Created by HUZY_KAMZ on 10/23/2016.
 */

public class Posts {
    private String EventDescription;
    private String EventImage;
    private String EventTitle;
    private String PostId;
    private String EventVideo;



    public Posts(){

        // left out for firebase
    }

    public Posts(String postId) {
        PostId = postId;
    }

    public Posts(String eventDescription, String eventImage, String eventTitle) {
        EventDescription = eventDescription;
        EventImage = eventImage;
        EventTitle = eventTitle;
    }

    public String getEventVideo() {
        return EventVideo;
    }

    public void setEventVideo(String eventVideo) {
        EventVideo = eventVideo;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getEventImage() {
        return EventImage;
    }

    public void setEventImage(String eventImage) {
        EventImage = eventImage;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }
    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }
}
