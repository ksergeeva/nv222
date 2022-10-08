import java.io.*;
import java.util.Arrays;


class Basket implements Serializable {

    private String[] products;
    private int[] prices;
    private int[] cart;
    protected int sumProduct;

    Basket(int[] prices, String[] products) {

        this.products = products;
        this.prices = prices;


        cart = new int[products.length];
    }

    protected void addToCart(int productNum, int amount) {
        if (productNum > (products.length)) {
            System.out.println("Ошибка");
        } else {
            cart[productNum] += amount;
        }
    }

    protected void printCart() {
        sumProduct = 0;
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " : " + prices[i] + " руб " + cart[i] + " шт ");
            sumProduct += cart[i] * prices[i];
        }
        System.out.println("итог: " + sumProduct);
    }

    public void saveBin(File file) {
        try (OutputStream fileOut = new FileOutputStream(file)) {
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(Basket.this);
        } catch (IOException e) {
            System.out.println();
        }
    }

    protected Basket loadFromBinFile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Basket basketF = (Basket) ois.readObject();
            System.out.println(basketF);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
}









