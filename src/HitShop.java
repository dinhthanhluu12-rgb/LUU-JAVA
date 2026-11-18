import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HitShop {

    private static final Customer currentCustomer =
            new Customer("C01", "Linh Nguyễn", "0900000001");
    private static final List<CartItem> cart = new ArrayList<>();
    private static final List<Product> products = new ArrayList<>();

    static void addItem(Product product, int quantity){
        cart.add(new CartItem(product,quantity));
        System.out.println("Da them mat hang thanh cong!");
    }
    static void removeItem(int index){
        if (index < 0 || index >= cart.size()) {
            System.out.println("Không tìm thấy mặt hàng.");
            return;
        }

        cart.remove(index);
        System.out.println("Da xoa mat hang thanh cong!");
    }
    static void printItems(){
        System.out.println("Khach hang: " + currentCustomer);
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng đang trống.");
            return;
        }
        System.out.println("\n====DANH SACH GIO HANG====");
        for (int i = 0; i < cart.size(); i++) {
            CartItem item = cart.get(i);
            Product p = item.getProduct();
            System.out.printf("%d. %s | %s | %.1f | SL: %d | Thành tiền: %.1f%n",
                    i + 1,
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    item.getQuantity(),
                    item.getTotal());
        }
    }
    static double calculateTotal(){
        double total = 0;
        for (CartItem item : cart) {
            total += item.getTotal();
        }
        return total;
    }

    static void addOrder(Scanner sc) {
        System.out.println("\n=== Chọn loại sản phẩm ===");
        System.out.println("1. Product - Sản phẩm thông thường");
        System.out.println("2. Book - Sách");
        System.out.println("3. Cake - Bánh");
        System.out.print("Lựa chọn loại sản phẩm: ");
        int type = sc.nextInt();
        sc.nextLine();

        if (type < 1 || type > 3) {
            System.out.println("Loại sản phẩm không hợp lệ!");
            return;
        }

        System.out.print("Nhập ID sản phẩm: ");
        String id = sc.nextLine().trim();
        System.out.print("Nhập tên sản phẩm: ");
        String name = sc.nextLine().trim();
        System.out.print("Nhập giá sản phẩm: ");
        double price = sc.nextDouble();
        System.out.print("Nhập tồn kho: ");
        int stock = sc.nextInt();
        sc.nextLine();

        Product newProduct = null;

        switch (type) {
            case 1:
                newProduct = new Product(id, name, price, stock);
                break;
            case 2:
                System.out.print("Nhập tác giả (author): ");
                String author = sc.nextLine().trim();
                newProduct = new Book(id, name, price, stock, author);
                break;
            case 3:
                System.out.print("Nhập thương hiệu (brand): ");
                String brand = sc.nextLine().trim();
                newProduct = new Cake(id, name, price, stock, brand);
                break;
        }

        System.out.print("Nhập số lượng mua: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        products.add(newProduct);
        addItem(newProduct, quantity);
    }

    static void checkExpensiveProduct(Scanner sc) {
        System.out.print("Nhập ID sản phẩm cần kiểm tra: ");
        String id = sc.nextLine().trim();

        for (Product p : products) {
            if (p.getId().equals(id)) {
                if (p.isExpensive()) {
                    System.out.println("Sản phẩm [" + p.getName() + "] là Đắt.");
                } else {
                    System.out.println("Sản phẩm [" + p.getName() + "] là Không đắt.");
                }
                return;
            }
        }

        System.out.println("Không tìm thấy sản phẩm có ID: " + id);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Product p1 = new Product("P01", "Áo thun", 150000, 20);
        Product p2 = new Product("P02", "Bình nước", 50000, 10);
        int choose;

        do{
            System.out.println("\n===DANH SACH LUA CHON===");
            System.out.println("1. Thêm mặt hàng");
            System.out.println("2. Xóa mặt hàng");
            System.out.println("3. Xem giỏ");
            System.out.println("4. Xem tổng tiền");
            System.out.println("5. Kiểm tra sản phẩm có đắt không");
            System.out.println("6. Thêm đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Lua chon cua ban: ");
            choose = sc.nextInt();
            sc.nextLine();


            switch (choose) {
                case 1:
                    System.out.println("Sản phẩm có sẵn:");
                    System.out.println("- " + p1.getId() + ": " + p1.getName());
                    System.out.println("- " + p2.getId() + ": " + p2.getName());
                    System.out.print("Nhập mã sản phẩm: ");
                    String code = sc.nextLine().trim();
                    System.out.print("Nhập số lượng: ");
                    int sl = sc.nextInt();
                    sc.nextLine();

                    Product selected = null;
                    if (p1.getId().equals(code)) {
                        selected = p1;
                    } else if (p2.getId().equals(code)) {
                        selected = p2;
                    }

                    if (selected == null) {
                        System.out.println("Không tìm thấy sản phẩm.");
                    } else if (sl <= 0) {
                        System.out.println("Số lượng phải lớn hơn 0.");
                    } else {
                        addItem(selected, sl);
                    }
                    break;
                case 2:
                    printItems();
                    if (!cart.isEmpty()) {
                        System.out.print("Nhập số thứ tự mặt hàng muốn xóa (tính từ 1): ");
                        int stt = sc.nextInt();
                        sc.nextLine();
                        removeItem(stt - 1);
                    }
                    break;
                case 3:
                    printItems();
                    break;
                case 4:
                    System.out.println("Tổng tiền: " + calculateTotal());
                    break;
                case 5:
                    checkExpensiveProduct(sc);
                    break;
                case 6:
                    addOrder(sc);
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }

        }while (choose !=0 );

        sc.close();
    }
}