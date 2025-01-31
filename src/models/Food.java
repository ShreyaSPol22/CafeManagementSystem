package models;

import java.time.LocalDateTime;

public class Food {
    private int FoodID;
    private String FoodName;
    private String Category;
    private String CreatedAt;

    public Food(int FoodID, String FoodName, String Category, String CreatedAt) {
        this.FoodID = FoodID;
        this.FoodName = FoodName;
        this.Category = Category;
        this.CreatedAt = CreatedAt;
    }
    public int getFoodID()
    {
        return FoodID;
    }
    public void setFoodID(int FoodID)
    {
        this.FoodID=FoodID;
    }
    public String getFoodName()
    {
        return FoodName;
    }
    public void setFoodName(String FoodName)
    {
        this.FoodName=FoodName;
    }
    public String getCategory()
    {
        return Category;
    }
    public void setCategory(String category) {
        this.Category = category;
    }
    public String getCreatedAt()
    {
        return CreatedAt;
    }
    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }
}
