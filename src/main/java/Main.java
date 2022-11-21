import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws IOException, XPathExpressionException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {54, 121, 99};
        Basket basketF = new Basket(prices, products);
        ClientLog clientLog = new ClientLog();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("shop.xml"));

            XPath xPath = XPathFactory.newInstance().newXPath();
            String loadFileName = xPath
                    .compile("config/load/fileName")
                    .evaluate(doc);
            boolean loadFile = Boolean.parseBoolean(xPath
                    .compile("config/load/enabled")
                    .evaluate(doc));
            boolean saveFile = Boolean.parseBoolean(xPath
                    .compile("config/save/enabled")
                    .evaluate(doc));
            String saveFileName = xPath
                    .compile("config/save/fileName")
                    .evaluate(doc);
            boolean logFile = Boolean.parseBoolean(xPath
                    .compile("config/log/enabled")
                    .evaluate(doc));
            String logFileName = xPath
                    .compile("config/log/fileName")
                    .evaluate(doc);
            String txtBasket = "basket.txt";
            File txtBasketF = new File("basket.txt");
            File jsonBasketF = new File("basket.json");
            File file = new File(loadFileName);
            if (loadFile) {
                if (jsonBasketF.exists()) {
                    basketF.loadFromJsonFile(jsonBasketF);
                    System.out.println("jsons загружен");
                    basketF.printCart();
                }
            } else {
                try {
                    if (file.createNewFile()) {
                        System.out.println("Файл создан");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (txtBasketF.exists()) {
                basketF.loadFromTxtFile(txtBasketF);
            } else {
                try {
                    if (txtBasketF.createNewFile()) {
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Список возможных товаров: ");
            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб");
            }
            while (true) {
                System.out.println("Выберите товар и количество:");
                System.out.println("Введите -end- для завершения");
                String input = scanner.nextLine();
                if (input.equals("end")) {
                    if (saveFile) {
                        basketF.saveJson(new File(saveFileName));
                    }
                    basketF.printCart();
                    if (logFile) {
                        clientLog.exportAsCSV(new File(logFileName));
                        System.out.println("Журнал сохранен");
                    }
                    break;
                }
                String[] inputProduct = input.split(" ");
                int productNumber = Integer.parseInt(inputProduct[0]) - 1;
                int productCount = Integer.parseInt(inputProduct[1]);
                basketF.addToCart(productNumber, productCount);
                basketF.saveTxt(new File(txtBasket));
                clientLog.log(productNumber, productCount);
                basketF.printCart();
            }
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
}