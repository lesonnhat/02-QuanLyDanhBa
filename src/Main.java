import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactManager manager = new ContactManager();
        Scanner sc = new Scanner(System.in);
        menu();
        int choose;

        do {
            System.out.print("→ Chọn chức năng: ");

            while (!sc.hasNextInt()) { // Kiểm tra đầu vào
                System.out.print("Vui lòng nhập một số nguyên hợp lệ.\n → Chọn chức năng: ");
                sc.next(); // Loại bỏ đầu vào không hợp lệ
            }

            choose = sc.nextInt();

            switch (choose) {
                case 1:
                    manager.displayCSV();
                    break;
                case 2: {
                    manager.add();
                    break;
                }
                case 3:
                    manager.update();
                    break;
                case 4:
                    manager.remove();
                    break;
                case 5:
                    manager.searchFromCSV();
                    break;
                case 6:
                    manager.sortContactsByName();
                    break;
                case 7:
                    manager.writeContactsToCSV();
                    break;
                case 8:
                    manager.readContactFromCSV();
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình.");
                    break; // Cho phép người dùng thoát
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (choose != 0);
        sc.close();
    }

    private static void menu() {
        System.out.println("---CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ---");
        System.out.println("1. Xem danh sách\n" +
                "2. Thêm mới\n" +
                "3. Sửa\n" +
                "4. Xóa\n" +
                "5. Tìm kiếm\n" +
                "6. Sắp xếp\n" +
                "7. Ghi vào file\n" +
                "8. Đọc từ file\n" +
                "0. Thoát");
    }
}