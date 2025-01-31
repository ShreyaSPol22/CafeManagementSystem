package main;

import dao.FoodDao;
import models.Food;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FoodDao foodDao = new FoodDao();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1.Add Food");
            System.out.println("2.Delete Food");
            System.out.println("3.Update Food");
            System.out.println("4.Search Food");
            System.out.println("5.Display Food");
            System.out.println("6.Exit");
            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter food id: ");
                    int foodId = sc.nextInt();
                    System.out.println("Enter food name: ");
                    String foodname = sc.nextLine();
                    sc.nextLine();
                    System.out.println("Enter food category: ");
                    String foodcategory = sc.nextLine();
                    System.out.println("Enter Date: ");
                    String createdAt = sc.nextLine();

                    Food newFood = new Food(foodId, foodname, foodcategory, createdAt);
                    foodDao.addFood(newFood);
                    break;
                case 2:
                    System.out.println("Enter Food id to delete:");
                    int deleteid = sc.nextInt();
                    foodDao.deleteData(deleteid);
                    System.out.println("Data deleted successfully");
                    break;
                case 3:
                    System.out.println("Enter food id to update: ");
                    int updateid = sc.nextInt();
                    System.out.println("Enter food name to update: ");
                    String updatename = sc.nextLine();
                    sc.nextLine();
                    System.out.println("Enter category to update: ");
                    String updatecategory = sc.nextLine();
                    System.out.println("Enter Date to update: ");
                    String updatedAt = sc.nextLine();
                    Food updatefood = new Food(updateid, updatename, updatecategory, updatedAt);
                    foodDao.updateData(updatefood);
                    break;
                case 4:
                    System.out.println("Enter food to search: ");
                    String keyword = sc.nextLine();
                    List<Food> foodlist = foodDao.searchFood(keyword);

                    for (Food f : foodlist) {
                        System.out.println(f.getFoodID() + "\t" + f.getFoodName() + "\t" + f.getCategory() + "\t" + f.getCreatedAt());
                    }
                    break;
                case 5:
                    System.out.println("Displaying database");
                    foodDao.DisplayData();
                    break;
                case 6:
                    System.out.println("Exit");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
