package models;

import java.time.LocalDateTime;

public class Food {
    private int foodId;
    private String foodName;
    private String category;
    private LocalDateTime createdAt;

    public Food() {

    }

    //For data insertion
    public Food(String foodName, String category, LocalDateTime createdAt) {
        this.foodName = foodName;
        this.category = category;
        this.createdAt = createdAt;
    }

    //For retrieval
    public Food(int foodId, String foodName, String category, LocalDateTime createdAt) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.category = category;
        this.createdAt = createdAt;
    }

    public int getFoodId() {
        return foodId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        createdAt = createdAt;
    }
}
