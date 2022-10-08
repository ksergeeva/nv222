import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("basket.txt");
        if (file.createNewFile()) {
            System.out.println("Файл создан");
        }
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {54, 121, 99};
        Basket basketF = new Basket(prices, products);
        System.out.println("Список возможных товаров: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб");
        }
        while (true) {
            System.out.println("Выберите товар и количество:");
            System.out.println("Введите -end- для завершения");
            System.out.println("Введите -load- для загрузки корзины");
            String input = scanner.nextLine();
            try {
                if ("end".equals(input)) break;
                String[] addCart = input.split(" ");
                int numProduct = Integer.parseInt(addCart[0]) - 1;
                int countProduct = Integer.parseInt(addCart[1]);
                basketF.addToCart(numProduct, countProduct);
                basketF.saveTxt(file);
            } catch (NumberFormatException e) {
                System.out.println();
            }
            if ("load".equals(input)) {
                basketF.loadFromTxtFile(new File("basket.txt"));
                basketF.printCart();
                System.out.println("Корзина загружена ");
            }
        }
        basketF.printCart();
    }
}

