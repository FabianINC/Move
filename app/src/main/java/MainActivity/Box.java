package MainActivity;

public class Box {

    String location,price;
    int image;


    public Box(String location, String price, int image) {
        this.location = location;
        this.price = price;
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
