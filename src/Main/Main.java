package Main;

import Services.FoodOperations;
import Models.Food;
import Services.FoodServices;

import java.util.Scanner;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        FoodServices foodOps = new FoodOperations();
        int flag = 0;
        try (Scanner sc = new Scanner(System.in)) {
            do {
                System.out.println("1.Add Food");
                System.out.println("2.Add Food by filepath");
                System.out.println("3.Delete Food");
                System.out.println("4.Update Food");
                System.out.println("5.Search Food");
                System.out.println("6.Display Food");
                System.out.println("7.Display in Json");
                System.out.println("Enter your choice: ");

                int choice = InputValidator.getIntInput(sc, "Invalid input. Please enter a number:");

                switch (choice) {
                    case 1:
                        Food addObj = new Food();
                        System.out.println("Enter food name: ");
                        addObj.setFoodName(InputValidator.getAlphabeticString(sc, "Please enter valid food name: "));

                        System.out.println("Enter food category: ");
                        addObj.setCategory(InputValidator.getAlphabeticString(sc, "Please enter valid food category: "));

                        System.out.println("Enter food price: ");
                        addObj.setPrice(InputValidator.getDoubleInput(sc, "Please enter a valid price:"));
                        sc.nextLine();

                        foodOps.addFood(addObj);
                        break;
                    case 2:
                        System.out.println("Enter file path: ");
                        foodOps.addByFile(InputValidator.getNonEmptyString(sc, "Please enter valid file path: "));
                        break;
                    case 3:
                        System.out.println("Enter Food id to delete:");
                        int deleteId = InputValidator.getIntInput(sc, "Please enter a valid food ID:");
                        sc.nextLine();
                        if (foodOps.isFoodIdExists(deleteId)) {
                            foodOps.deleteData(deleteId);
                        } else {
                            System.out.println("Food ID does not exist.");
                        }
                        break;
                    case 4:
                        System.out.println("Enter food id to update:");
                        int updateId = InputValidator.getIntInput(sc, "Please enter a valid food ID:");
                        sc.nextLine();
                        if (foodOps.isFoodIdExists(updateId)) {
                            Food updateObj = new Food();
                            updateObj.setFoodId(updateId);
                            System.out.println("Select what you want to update:");
                            System.out.println("1.Food Name\t2.Category\t3.Price");
                            System.out.println("Enter your choice to update:");
                            int choiceUpdate = InputValidator.getIntInput(sc, "Invalid input. Please enter a number:");
                            sc.nextLine();

                            switch (choiceUpdate) {
                                case 1:
                                    System.out.println("Enter food name to update: ");
                                    updateObj.setFoodName(InputValidator.getAlphabeticString(sc, "Please enter valid food name to update: "));
                                    foodOps.updateData(updateObj, choiceUpdate);
                                    break;
                                case 2:
                                    System.out.println("Enter category to update: ");
                                    updateObj.setCategory(InputValidator.getAlphabeticString(sc, "Please enter valid category to update: "));
                                    foodOps.updateData(updateObj, choiceUpdate);
                                    break;
                                case 3:
                                    double oldPrice = foodOps.getOldPrice(updateId);
                                    System.out.println("Enter price to update: ");
                                    updateObj.setPrice(InputValidator.getDoubleInput(sc, "Please enter a valid price:"));
                                    sc.nextLine();
                                    foodOps.updatePrice(updateObj, oldPrice);
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                        } else {
                            System.out.println("Food ID does not exist.");
                        }
                        break;
                    case 5:
                        System.out.println("Enter food name to search: ");
                        String keyword = InputValidator.getAlphabeticString(sc, "Food name cannot be empty and must contain only letters. Please enter food name to search: ");
                        List<Food> foodList = foodOps.searchFood(keyword);
                        if (!foodList.isEmpty()) {
                            System.out.println("FoodId\tFoodName\tCategory\t\tPrice");
                            System.out.println("------------------------------------------------------------------------------");
                            for (Food f : foodList) {
                                System.out.println(f.getFoodId() + "\t" + f.getFoodName() + "\t" + f.getCategory() + "\t" + f.getPrice());
                            }
                        }
                        break;
                    case 6:
                        System.out.println("Displaying database");
                        List<Food> foodsList = foodOps.displayData();
                        if (!foodsList.isEmpty()) {
                            System.out.println("FoodId\tFoodName\tCategory\t\tPrice\t\tCreatedDate\t\tUpdatedDate");
                            System.out.println("------------------------------------------------------------------------------");
                            for (Food food : foodsList) {
                                System.out.println(food.getFoodId() + "\t" + food.getFoodName() + "\t" + food.getCategory() + "\t" + food.getPrice() + "\t" + food.getCreatedDate() + "\t" + food.getUpdatedDate());
                                System.out.println("---------------------------------------------------------------------------");
                            }
                        } else {
                            System.out.println("No food data found");
                        }
                        break;
                    case 7:
                        System.out.println("Displaying data in json");
                        List<Food> jsonFoodList = foodOps.displayData();
                        if (!jsonFoodList.isEmpty()) {
                            foodOps.displayWithJson(jsonFoodList);
                        } else {
                            System.out.println("No food data found");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice");
                }

                do {
                    System.out.println("Do you want to continue (Y/N): ");
                    char ch = sc.next().charAt(0);
                    flag = (ch == 'Y' || ch == 'y') ? 1 : (ch == 'N' || ch == 'n') ? 0 : -1;
                    if (flag == -1) {
                        System.out.println("Invalid choice");
                    }
                } while (flag == -1);

            } while (flag == 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class InputValidator {
    private static final Pattern ALPHABETIC_PATTERN = Pattern.compile("^[A-Za-z]+$");

    public static int getIntInput(Scanner sc, String errorMessage) {
        while (!sc.hasNextInt()) {
            System.out.println(errorMessage);
            sc.next();
        }
        return sc.nextInt();
    }

    public static double getDoubleInput(Scanner sc, String errorMessage) {
        while (!sc.hasNextDouble()) {
            System.out.println(errorMessage);
            sc.next();
        }
        return sc.nextDouble();
    }

    public static String getNonEmptyString(Scanner sc, String errorMessage) {
        String input = sc.nextLine();
        while (input.trim().isEmpty()) {
            System.out.println(errorMessage);
            input = sc.nextLine();
        }
        return input;
    }

    public static String getAlphabeticString(Scanner sc, String errorMessage) {
        String input = sc.nextLine();
        while (input.trim().isEmpty() || !ALPHABETIC_PATTERN.matcher(input).matches()) {
            System.out.println(errorMessage);
            input = sc.nextLine();
        }
        return input;
    }
}



