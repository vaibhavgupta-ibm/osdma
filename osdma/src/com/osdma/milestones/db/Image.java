package com.osdma.milestones.db;

public class Image {

    public long id;
    public String image_location;
    public String lattitude;
    public String longitude;
    public String datetime;
    public String issync;

    // constructor
    public Image(long id, String image_location, String lattitude, String longitude, String datetime, String issync) {
        this.id = id;
        this.image_location = image_location;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.datetime = datetime;
        this.issync = issync;
    }
    
    public Image(){
    	
    }

}
