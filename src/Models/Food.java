package Models;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Food {
    private int foodId;
    private String foodName;
    private String category;
    private double price;
    private String createdDate;
    private String updatedDate;

    public Food() {

    }

    //For retrieval
    public Food(int foodId, String foodName, String category, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.category = category;
        this.price = price;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate.toString();
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate.toString();
    }
}
