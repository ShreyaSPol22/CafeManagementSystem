package Operations;

import database.DatabaseConnection;
import models.Food;

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
    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(FoodOperations.class.getName());

    public FoodOperations() {
        this.connection = DatabaseConnection.getInstance();
    }

    public void displayData() {
        String sql1 = "select * from Food";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                String category = rs.getString(3);
                String date = rs.getString(4);
                System.out.println(id + "\t" + name + "\t" + category + "\t" + date);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in Displaying Data" + e.getMessage());
        }
    }

    public void addFood(Food food) {
        String sql = "INSERT INTO Food(FoodName,Category,CreatedAt) VALUES(?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, food.getFoodName());
            st.setString(2, food.getCategory());
            if (food.getCreatedAt() != null) {
                st.setTimestamp(3, Timestamp.valueOf(food.getCreatedAt()));
            } else {
                st.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            }
            st.executeUpdate();//check this again
            LOGGER.info("Data Added Successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in Inserting Data" + e.getMessage());
        }
    }

    public void addByFile(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Food food = new Food();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    String foodName = values[0].trim();
                    String category = values[1].trim();
                    LocalDateTime createdAt = LocalDateTime.now();
                    food.setFoodName(foodName);
                    food.setCategory(category);
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
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            LOGGER.info("Data deleted successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in Deleting Data" + e.getMessage());
        }
    }

    public void updateData(Food food) {
        String sql = "UPDATE Food SET FoodName=?, Category=?, CreatedAt=? WHERE FoodID=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, food.getFoodName());
            st.setString(2, food.getCategory());
            if (food.getCreatedAt() != null) {
                st.setTimestamp(3, Timestamp.valueOf(food.getCreatedAt()));
            } else {
                st.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            }
            st.setInt(4, food.getFoodId());
            st.executeUpdate();
            LOGGER.info("Data updated successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in Updating Data" + e.getMessage());
        }
    }

    public List<Food> searchFood(String keyword) {
        List<Food> foodsList = new ArrayList<>();
        String sql = "Select * From Food WHERE FoodName LIKE ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Food food = new Food(rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getString("Category"),
                        rs.getTimestamp("CreatedAt").toLocalDateTime()
                );
                foodsList.add(food);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in Searching Data" + e.getMessage());
        }
        return foodsList;
    }
}
