package models;

import java.time.LocalDateTime;

public class Food {
    private int foodId;
    private String foodName;
    private String category;
    private String createdAt;

    public Food(int foodId, String foodName, String category, String createdAt) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.category = category;
        this.createdAt = createdAt;
    }
    public int getFoodId()
    {
        return foodId;
    }
    public void setFoodID(int foodId)
    {
        this.foodId=foodId;
    }
    public String getFoodName()
    {
        return foodName;
    }
    public void setFoodName(String foodName)
    {
        this.foodName=foodName;
    }
    public String getCategory()
    {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCreatedAt()
    {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        createdAt = createdAt;
    }
}
