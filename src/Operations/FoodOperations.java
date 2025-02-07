package Operations;

import Database.DatabaseConnection;
import Models.Food;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class FoodOperations {
    private final Connection connection = DatabaseConnection.getInstance();
    private static final Logger LOGGER = Logger.getLogger(Operations.FoodOperations.class.getName());

    public FoodOperations() {

    }

    public void displayData() {
        String query = "SELECT * FROM food";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            System.out.println("FoodId\tFoodName\tCategory\t\tPrice\t\tCreatedDate\t\tUpdatedDate");
            while (rs.next()) {
                int foodId = rs.getInt("FoodID");
                String foodName = rs.getString("FoodName");
                String category = rs.getString("Category");
                double price = rs.getDouble("Price");
                Timestamp createdDate = rs.getTimestamp("CreatedDate");
                Timestamp updatedDate = rs.getTimestamp("UpdatedDate");
                System.out.println(foodId + "\t\t" + foodName + "\t\t\t" + category + "\t\t" + price + "\t\t" + createdDate + "\t\t" + updatedDate);
                System.out.println("------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

    }

    public void addFood(Food food) {
        String foodQuery = "INSERT INTO Food(FoodName, Category, Price, CreatedDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(foodQuery)){
            st.setString(1, food.getFoodName());
            st.setString(2, food.getCategory());
            st.setDouble(3, food.getPrice());
            st.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            st.executeUpdate();
            System.out.println("Database inserted Successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

    }

    public void addByFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            Food food = new Food();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    String foodName = values[0].trim();
                    String category = values[1].trim();
                    double price = Double.parseDouble(values[2].trim());
                    LocalDateTime createdDate = LocalDateTime.now();
                    food.setFoodName(foodName);
                    food.setCategory(category);
                    food.setPrice(price);
                    addFood(food);
                } else {
                    System.out.println("Wrong line format " + line);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in Reading File" + e.getMessage());
        }
    }

    public void deleteData(int id) {
        String sql = "DELETE FROM FOOD WHERE FoodID=?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
            LOGGER.info("Data deleted successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in Deleting Data" + e.getMessage());
        }
    }

    public void updateData(Food food, int choice) {
        PreparedStatement st = null;
        try {
            switch (choice) {
                case 1:
                    String foodNameQuery = "UPDATE Food SET FoodName=? WHERE FoodID=?";
                    st = connection.prepareStatement(foodNameQuery);
                    st.setString(1, food.getFoodName());
                    st.setInt(2, food.getFoodId());
                    st.executeUpdate();
                    break;
                case 2:
                    String categoryQuery = "UPDATE Food SET Category=? WHERE FoodID=?";
                    st = connection.prepareStatement(categoryQuery);
                    st.setString(1, food.getCategory());
                    st.setInt(2, food.getFoodId());
                    st.executeUpdate();
                    break;
                case 3:
                    String priceQuery = "UPDATE Food SET Price=?,UpdatedDate=? WHERE FoodID=?";
                    st = connection.prepareStatement(priceQuery);
                    st.setDouble(1, food.getPrice());
                    st.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    st.setInt(3, food.getFoodId());
                    st.executeUpdate();
                    break;
                default:
                    System.out.println("Wrong choice " + choice);
                    break;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

    }

    public List<Food> searchFood(String keyword) {
        List<Food> foodsList = new ArrayList<>();

        String sql = "Select * From Food WHERE FoodName LIKE ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();
            boolean found = false;
            while (rs.next()) {
                Food food = new Food(rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getString("Category"),
                        rs.getDouble("Price")
                );
                foodsList.add(food);
            }
            for (Food f : foodsList) {
                if (f.getFoodName().equalsIgnoreCase(keyword)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No food found");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in Searching Data" + e.getMessage());
        }
        return foodsList;
    }
}
