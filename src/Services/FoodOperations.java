package Services;

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
import java.lang.String;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FoodOperations implements FoodServices {
    private final Connection connection = DatabaseConnection.getInstance();
    private final Logger LOGGER = Logger.getLogger(FoodOperations.class.getName());

    public FoodOperations() {

    }

    public List<Food> displayData() {
        List<Food> foodList = new ArrayList<>();
        String query = "SELECT * FROM food";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Food food = new Food();
                food.setFoodId(rs.getInt("FoodID"));
                food.setFoodName(rs.getString("FoodName"));
                food.setCategory(rs.getString("Category"));
                food.setPrice(rs.getDouble("Price"));
                food.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                food.setUpdatedDate(rs.getTimestamp("UpdatedDate").toLocalDateTime());
                foodList.add(food);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return foodList;
    }

    public void addFood(Food food) {
        String foodQuery = "INSERT INTO Food(FoodName, Category, Price, CreatedDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(foodQuery)) {
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
        String deleteSql = "DELETE FROM FOOD WHERE FoodID=?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
            LOGGER.info("Data deleted successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void updateData(Food food, int choice) {
        String updateSql;
        PreparedStatement updateStmt;
        try {
            switch (choice) {
                case 1:
                    updateSql = "UPDATE Food SET FoodName=? WHERE FoodID=?";
                    updateStmt = connection.prepareStatement(updateSql);
                    updateStmt.setString(1, food.getFoodName());
                    updateStmt.setInt(2, food.getFoodId());
                    updateStmt.executeUpdate();
                    break;
                case 2:
                    updateSql = "UPDATE Food SET Category=? WHERE FoodID=?";
                    updateStmt = connection.prepareStatement(updateSql);
                    updateStmt.setString(1, food.getCategory());
                    updateStmt.setInt(2, food.getFoodId());
                    updateStmt.executeUpdate();
                    break;
                default:
                    System.out.println("Wrong choice " + choice);
                    return;
            }
            LOGGER.info("Data updated successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in updating data: " + e.getMessage());
        }
    }

    public double getOldPrice(int foodId) {
        PreparedStatement st;
        double oldPrice = 0.0;
        try {
            String fetchPrice = "SELECT Price FROM Food WHERE FoodID=?";
            st = connection.prepareStatement(fetchPrice);
            st.setInt(1, foodId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                oldPrice = rs.getDouble("Price");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return oldPrice;
    }


    public void updatePrice(Food food, double oldPrice) {
        PreparedStatement st;
        try {
            String priceQuery = "UPDATE Food SET Price=?,UpdatedDate=? WHERE FoodID=?";
            st = connection.prepareStatement(priceQuery);
            st.setDouble(1, food.getPrice());
            st.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            st.setInt(3, food.getFoodId());
            st.executeUpdate();

            String priceUpdateQuery = "INSERT INTO PriceUpdates (FoodId, OldPrice, NewPrice, UpdatedDate) VALUES (?, ?, ?, ?)";
            st = connection.prepareStatement(priceUpdateQuery);
            st.setInt(1, food.getFoodId());
            st.setDouble(2, oldPrice);
            st.setDouble(3, food.getPrice());
            st.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            st.executeUpdate();

            String priceDisplay = "select * from PriceUpdates";
            st = connection.prepareStatement(priceDisplay);
            ResultSet rs = st.executeQuery();
            System.out.println("UpdateId\t\tFoodID\t\tOldPrice\t\tNewPrice\t\tUpdatedDate");
            while (rs.next()) {
                int updateId = rs.getInt("UpdateId");
                int foodId = rs.getInt("FoodId");
                double price = rs.getDouble("OldPrice");
                double newPrice = rs.getDouble("NewPrice");
                Timestamp updatedDate = rs.getTimestamp("UpdatedDate");
                System.out.println(updateId + "\t\t\t" + foodId + "\t\t\t" + price + "\t\t\t" + newPrice + "\t\t\t" + updatedDate);
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
                Food food = new Food(rs.getInt("FoodID"), rs.getString("FoodName"), rs.getString("Category"), rs.getDouble("Price"));
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

    public boolean isFoodIdExists(int foodId) {
        String checkSql = "SELECT COUNT(*) as count FROM FOOD WHERE FoodID=?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, foodId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    return true;
                } else {
                    System.out.println("No food id found");
                    return false;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking FoodID: " + e.getMessage());
        }
        return false;
    }

    public void displayWithJson(List<Food> foodsList) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(foodsList);
            System.out.println(jsonData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
