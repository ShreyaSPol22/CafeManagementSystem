package main;

import Operations.FoodOperations;
import models.Food;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FoodOperations foodOps = new FoodOperations();
        char ch = 'y';
        while (ch == 'y') {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("1.Add Food");
                System.out.println("2.Add Food by filepath");
                System.out.println("3.Delete Food");
                System.out.println("4.Update Food");
                System.out.println("5.Search Food");
                System.out.println("6.Display Food");
                System.out.println("7.Exit");
                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        Food addObj = new Food();
                        System.out.println("Enter food name: ");
                        //sc.nextLine();
                        addObj.setFoodName(sc.next());
                        System.out.println("Enter food category: ");
                        addObj.setCategory(sc.next());
                    /*System.out.println("Enter food price: ");
                    addObj.setPrice(sc.nextDouble());
                    addObj.setEffectiveDate(LocalDateTime.now());
                     */
                        addObj.setCreatedAt(LocalDateTime.now());
                        foodOps.addFood(addObj);
                        break;
                    case 2:
                        System.out.println("Enter file path: ");
                        sc.nextLine();
                        String path = sc.nextLine();
                        foodOps.addByFile(path);
                        break;
                    case 3:
                        System.out.println("Enter Food id to delete:");
                        int deleteId = sc.nextInt();
                        foodOps.deleteData(deleteId);
                        break;
                    case 4:
                        Food updateObj = new Food();
                        System.out.println("Enter food id to update:");
                        int updateId = sc.nextInt();
                        System.out.println("Enter food name to update: ");
                        sc.nextLine();
                        updateObj.setFoodName(sc.nextLine());
                        System.out.println("Enter category to update: ");
                        updateObj.setCategory(sc.nextLine());
                        updateObj.setCreatedAt(LocalDateTime.now());
                        foodOps.updateData(updateObj);
                        break;
                    case 5:
                        sc.nextLine();
                        System.out.println("Enter food name to search: ");
                        String keyword = sc.nextLine();
                        List<Food> foodList = foodOps.searchFood(keyword);

                        for (Food f : foodList) {
                            System.out.println(f.getFoodId() + "\t" + f.getFoodName() + "\t" + f.getCategory() + "\t" + f.getCreatedAt());
                        }
                        break;
                    case 6:
                        System.out.println("Displaying database");
                        foodOps.displayData();
                        break;
                    case 7:
                        System.out.println("Exit");
                        sc.close();
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
                System.out.println("Do you want to continue (Y/N): ");
                ch = sc.next().charAt(0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
