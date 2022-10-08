import java.io.*;

public class Basket {

    protected int[] cart;
    protected String[] products;
    protected int[] prices;


    public Basket(int[] prices, String[] products) {
        this.products = products;
        this.prices = prices;
        cart = new int[products.length];
    }

    public Basket loadFromTxtFile(File textFile) {
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
}
