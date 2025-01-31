package dao;

import database.DatabaseConnection;
import models.Food;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class FoodDao {
    private Connection connection;

    public FoodDao()
    {
        this.connection=DatabaseConnection.getConnection();
    }

    public void displayData()
    {
        String sql1="select * from Food";

        try(Statement st=connection.createStatement())
        {
            ResultSet rs= st.executeQuery(sql1);
            while(rs.next())
            {
                Integer id=rs.getInt(1);
                String name=rs.getString(2);
                String category=rs.getString(3);
                String date=rs.getString(4);
                System.out.println(id + "\t" + name + "\t" + category + "\t" + date);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void addFood(Food food)
    {
        String sql="INSERT INTO Food(FoodID,FoodName,Category,CreatedAt) VALUES(?, ?, ?, ?)";

        try(PreparedStatement st=connection.prepareStatement(sql))
        {
            st.setInt(1, food.getFoodID());
            st.setString(2,food.getFoodName());
            st.setString(3,food.getCategory());
            st.setString(4,food.getCreatedAt());
            st.executeUpdate();//check this again
            System.out.println("Data Added Successfully");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteData(int id)
    {
        String sql="DELETE FROM FOOD WHERE FoodID=?";
        try(PreparedStatement st=connection.prepareStatement(sql))
        {
            st.setInt(1,id);
            st.executeUpdate();
            System.out.println("Data deleted successfully");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateData(Food food)
    {
        String sql="UPDATE Food SET FoodName=?, Category=?, CreatedAt=? WHERE FoodID=?";
        try(PreparedStatement st=connection.prepareStatement(sql))
        {
            st.setString(1,food.getFoodName());
            st.setString(2,food.getCategory());
            st.setString(3,food.getCreatedAt());
            st.setInt(4,food.getFoodID());
            st.executeUpdate();
            System.out.println("Data updated successfully");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<Food> searchFood(String keyword)
    {
        List<Food> foodsList=new ArrayList<>();
        String sql="Select * From Food WHERE FoodName LIKE ?";
        try(PreparedStatement st=connection.prepareStatement(sql))
        {
            st.setString(1,"%" + keyword + "%");
            ResultSet rs=st.executeQuery();
            while(rs.next())
            {
                Food food=new Food(rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getString("Category"),
                        rs.getString("CreatedAt")
                        );
                foodsList.add(food);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return foodsList;
    }
}
