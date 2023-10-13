package sv.gob.interfaces01;

public class Dispositivos {

    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;

    public Dispositivos(Integer id, String title, Double price, String description, String category, String image) {
        this.id=id;
        this.title=title;
        this.price=price;
        this.description=description;
        this.category=category;
        this.image=image;
    }


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }




}
