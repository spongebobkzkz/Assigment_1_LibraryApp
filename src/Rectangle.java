public class Rectangle {

    private double width;
    private double height;
    private int id;
    private static int idGen = 1;

    // 1) Default constructor
    public Rectangle() {
        this.width = 1.0;
        this.height = 1.0;
        this.id = idGen++;
    }

    // 2) Constructor with parameters
    public Rectangle(double width, double height) {
        this(); // call default constructor
        setWidth(width);
        setHeight(height);
    }

    // Getters
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    // Setters with validation
    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be > 0");
        }
        this.width = width;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be > 0");
        }
        this.height = height;
    }

    // area
    public double area() {
        return width * height;
    }

    // perimeter
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return "Rectangle{id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", area=" + area() +
                ", perimeter=" + perimeter() +
                '}';
    }
}