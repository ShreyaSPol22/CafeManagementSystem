package Main;

import Operations.FoodOperations;
import Models.Food;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FoodOperations foodOps = new FoodOperations();
        int flag = 0;
        try (Scanner sc = new Scanner(System.in)) {
            do {
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
                        sc.nextLine();
                        addObj.setFoodName(sc.nextLine());
                        System.out.println("Enter food category: ");
                        addObj.setCategory(sc.nextLine());
                        System.out.println("Enter food price: ");
                        addObj.setPrice(sc.nextDouble());
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
                        updateObj.setFoodId(sc.nextInt());
                        System.out.println("Select what you want to update:");
                        System.out.println("1.Food Name\t2.Category\t3.Price");
                        System.out.println("Enter your choice to update:");
                        int choiceUpdate = sc.nextInt();
                        switch (choiceUpdate) {
                            case 1:
                                System.out.println("Enter food name to update: ");
                                sc.nextLine();
                                updateObj.setFoodName(sc.nextLine());
                                foodOps.updateData(updateObj, choiceUpdate);
                                break;
                            case 2:
                                System.out.println("Enter category to update: ");
                                sc.nextLine();
                                updateObj.setCategory(sc.nextLine());
                                foodOps.updateData(updateObj, choiceUpdate);
                                break;
                            case 3:
                                System.out.println("Enter price to update: ");
                                updateObj.setPrice(sc.nextDouble());
                                foodOps.updateData(updateObj, choiceUpdate);
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }
                        break;
                    case 5:
                        sc.nextLine();
                        System.out.println("Enter food name to search: ");
                        String keyword = sc.nextLine();
                        List<Food> foodList = foodOps.searchFood(keyword);
                        for (Food f : foodList) {
                            System.out.println(f.getFoodId() + "\t" + f.getFoodName() + "\t" + f.getCategory() + "\t");
                        }
                        break;
                    case 6:
                        System.out.println("Displaying database");
                        foodOps.displayData();
                        break;
                    case 7:
                        System.out.println("Exit");
                        sc.close();
                        flag = 0;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }

                if (flag != 0) {
                    do {
                        System.out.println("Do you want to continue (Y/N): ");
                        char ch = sc.next().charAt(0);

                        flag = (ch == 'Y' || ch == 'y') ? 1 : (ch == 'N' || ch == 'n') ? 0 : -1;
                        if (flag == -1) {
                            System.out.println("Invalid choice");
                        }
                    } while (flag == -1);
                }

            } while (flag == 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
