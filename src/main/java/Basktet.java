import com.google.gson.Gson;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class Basket implements Serializable {
    private String[] products;
    private int[] prices;
    private static int[] cart;
    protected int sumProduct;

    Basket(int[] prices, String[] products) {

        this.products = products;
        this.prices = prices;
        cart = new int[products.length];
    }


    protected Basket loadFromTxtFile(File textFile) {
        String line;
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            while ((line = br.readLine()) != null) {
                cart[i] = Integer.parseInt(line);
                i += 1;
            }
        } catch (NumberFormatException | FileNotFoundException e) {
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "ваша корзина: \n" +
                "продукты: " + Arrays.toString(products) + "\n" +
                "количество: " + Arrays.toString(cart) + "\n" +
                "цена: " + Arrays.toString(prices) + "\n" +
                "итог: " + sumProduct;
    }

    public void addToCart(int productNum, int amount) {
        if (productNum > (products.length)) {
            System.out.println("Ошибка");
        } else {
            cart[productNum] += amount;
        }
        saveTxt(new File("basket.txt"));
    }

    public void printCart() {
        int sumProduct = 0;
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " : " + prices[i] + " руб " + cart[i] + " шт ");
            sumProduct += cart[i] * prices[i];
        }
        System.out.println("Итого :" + sumProduct);
    }

    public void saveTxt(File file) {
        try (PrintWriter out = new PrintWriter(file)) {
            for (int i = 0; i < products.length; i++) {
                out.write(cart[i] + "\n");

            }
        } catch (NumberFormatException | FileNotFoundException e) {
            System.out.println();
        }
    }

    public void saveJson(File jsonFile) throws IOException {
        try (PrintWriter out = new PrintWriter(jsonFile)) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            out.println(json);
            System.out.println("Данные сохранены");
        }
    }

    public Basket loadFromJsonFile(File jsonFile) throws IOException {
        try (Scanner scanner = new Scanner(jsonFile)) {
            String json = scanner.nextLine();
            Gson gson = new Gson();
            gson.fromJson(json, Basket.class);
            System.out.println("Данные загружены");


        }

        return null;
    }


}