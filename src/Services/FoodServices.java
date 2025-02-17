package Services;

import Models.Food;

import java.util.List;

public interface FoodServices {
    public List<Food> displayData();

    public void addFood(Food food);

    public void addByFile(String path);

    public void deleteData(int id);

    public void updateData(Food food, int choice);

    public double getOldPrice(int foodId);

    public void updatePrice(Food food, double oldPrice);

    public List<Food> searchFood(String keyword);

    public boolean isFoodIdExists(int id);

    public void displayWithJson(List<Food> foods);
}
