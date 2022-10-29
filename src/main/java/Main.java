import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {54, 121, 99};
        Basket basketF = new Basket(prices, products);
        ClientLog clientLog = new ClientLog();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");
        doc.getDocumentElement().normalize();
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
                basketF.printCart();
                basketF.saveJson(new File("basket.json"));
                clientLog.log(numProduct, countProduct);
                clientLog.exportAsCSV(new File("client.csv"));
            } catch (NumberFormatException e) {
                System.out.println();
            }
            if ("load".equals(input)) {
                basketF.loadFromJsonFile(new File("basket.json"));
                basketF.loadFromTxtFile(new File("basket.txt"));
                basketF.printCart();
                System.out.println("Корзина загружена ");


            }
        }
    }
}