package company.override.huzykamz.pixsar.model;

/**
 * Created by HUZY_KAMZ on 10/15/2016.
 */

public class Event {

    private String EventName;
    private String EventLocation;
    private String EventType;
    private String EventDate;
    private String EventImage;

  //  private String EventVideo;

    public Event() {

    }


    public String getEventImage() {
        return EventImage;
    }

    public void setEventImage(String eventImage) {
        EventImage = eventImage;
    }

    public String getEventLocation() {
        return EventLocation;
    }

    public void setEventLocation(String eventLocation) {
        EventLocation = eventLocation;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }





    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }
}
