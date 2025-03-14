import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

public class ContactManager implements IManager<Contact> {
    public static final String FILE_NAME = "src\\contacts.csv";
    public static File file = new File(FILE_NAME);
    public List<Contact> contacts = new ArrayList<Contact>();
    Scanner scanner = new Scanner(System.in);

    public ContactManager() {
    }

    @Override
    public void writeContactsToCSV() {
        // Giả sử phương thức display() trả về một danh sách các liên hệ hiện có
        List<Contact> currentContacts = display(); // Thay đổi đây nếu display không trả về danh sách

        if (currentContacts.isEmpty()) {
            System.out.println("Không có thông tin nào để ghi.");
            return;
        }

        // Ghi vào tệp CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, false))) { // append = false
            // Ghi tiêu đề
            writer.println("Số điện thoại, Nhóm, Tên, Giới tính, Địa chỉ, Ngày sinh, Email");

            // Ghi tất cả thông tin từ currentContacts
            for (Contact contact : currentContacts) {
                writer.println(contact.getPhoneNumber() + "," +
                        contact.getGroup() + "," +
                        contact.getName() + "," +
                        contact.getGender() + "," +
                        contact.getAddress() + "," +
                        contact.getDateOfBirth() + "," +
                        contact.getEmail());
            }

            System.out.println("✔ Ghi Thành Công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> display() {
        // Trả về danh sách các liên hệ hiện có trong bộ nhớ
        return contacts;
    }

    public List<Contact> readFromCSV() {
        contacts.clear(); // Xóa tất cả liên hệ hiện có trong danh sách
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            // Bỏ qua tiêu đề
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 7) { // Kiểm tra đủ số trường
                    // Tạo đối tượng Contact từ các trường
                    Contact contact = new Contact(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
                    contacts.add(contact); // Thêm contact vào danh sách
                } else {
                    System.out.println("Dòng không hợp lệ: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public void readContactFromCSV() {
        contacts.clear(); // Xóa danh sách hiện có
        List<Contact> contacts = readFromCSV();
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void inputContact(Contact contact) {
        contact.setPhoneNumber(promptPhoneNumber(scanner));
        contact.setGroup(promptGroup(scanner));
        contact.setName(promptName(scanner));
        contact.setGender(promptGender(scanner));
        contact.setAddress(promptAddress(scanner));
        contact.setDateOfBirth(promptDateOfBirth(scanner));
        contact.setEmail(promptEmail(scanner));
    }

//    @Override
//    public void displayCSV() {
//        if (contacts.isEmpty()) {
//            System.out.println("Danh sách hiện đang trống, không có thông tin để hiển thị.");
//        } else {
//            for (Contact contact : contacts) {
//                System.out.println(contact);
//            }
//        }
//    }

    @Override
    public void displayCSV() {
        if (contacts.isEmpty()) {
            System.out.println("Danh sách hiện đang trống, không có thông tin để hiển thị.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int totalContacts = contacts.size();
        int itemsPerPage = 5; // Số mục hiển thị mỗi lần
        int pageCount = (int) Math.ceil((double) totalContacts / itemsPerPage); // Tính số trang

        for (int page = 0; page < pageCount; page++) {
            System.out.println("DANH BẠ (Trang " + (page + 1) + "/" + pageCount + "):");

            // Hiển thị 5 mục trên mỗi trang
            for (int i = page * itemsPerPage; i < Math.min((page + 1) * itemsPerPage, totalContacts); i++) {
                Contact contact = contacts.get(i);
                System.out.println((i + 1) + ". " + contact);
            }

            // Đợi người dùng nhấn phím Enter để tiếp tục
            if (page < pageCount - 1) { // Không đợi ở lần cuối cùng
                System.out.print("Nhấn Enter để tiếp tục...");
                scanner.nextLine(); // Đợi nhấn phím Enter
            }
        }
    }

    @Override
    public void add() {
        Contact contact = new Contact();
        inputContact(contact);
        contacts.add(contact); // Thêm vào danh sách
        System.out.println("✔ Thêm thành công liên hệ\n" + contact);
    }

    @Override
    public void update() {
        Scanner scanner = new Scanner(System.in); // Khởi tạo Scanner

        while (true) {
            System.out.print("Nhập số điện thoại của liên hệ cần sửa (hoặc bỏ trống để Thoát): ");
            String phoneNumber = scanner.nextLine().trim();

            // Kiểm tra nếu người dùng nhập trống để thoát
            if (phoneNumber.isEmpty()) {
                System.out.println("Đã thoát khỏi chức năng cập nhật.");
                return; // Thoát khỏi phương thức
            }

            // Tìm liên hệ theo số điện thoại
            Contact contactToUpdate = null;
            for (Contact contact : contacts) {
                if (contact.getPhoneNumber().equals(phoneNumber)) {
                    contactToUpdate = contact;
                    break;
                }
            }

            // Nếu không tìm thấy liên hệ, thông báo cho người dùng
            if (contactToUpdate == null) {
                System.out.println("Không tìm được danh bạ với số điện thoại trên. Vui lòng thử lại.");
                continue; // Quay lại vòng lặp để yêu cầu nhập lại
            }

            // Nếu tìm thấy, yêu cầu nhập thông tin mới
            System.out.println("Thông tin hiện tại của " + contactToUpdate.getName() + ":\n" + contactToUpdate);
            System.out.println("→ Nhập Thông Tin Mới");

            // Cập nhật thông tin của liên hệ
            contactToUpdate.setGroup(promptGroup(scanner));
            contactToUpdate.setName(promptName(scanner));
            contactToUpdate.setGender(promptGender(scanner));
            contactToUpdate.setAddress(promptAddress(scanner));
            contactToUpdate.setDateOfBirth(promptDateOfBirth(scanner));
            contactToUpdate.setEmail(promptEmail(scanner));

            System.out.println("✔ Cập nhật thông tin thành công cho " + contactToUpdate.getName());
            return; // Kết thúc phương thức sau khi cập nhật
        }
    }

    @Override
    public void remove() {
        while (true) { // Vòng lặp để yêu cầu nhập cho đến khi người dùng thực hiện hợp lệ
            System.out.print("Nhập số điện thoại của liên hệ cần xóa (hoặc bỏ trống để Thoát): ");
            String phoneNumber = scanner.nextLine().trim();

            // Kiểm tra nếu người dùng nhập trống để thoát
            if (phoneNumber.isEmpty()) {
                System.out.println("Đã thoát khỏi chức năng xóa.");
                return; // Thoát khỏi phương thức
            }

            Contact contactRemove = null;

            // Tìm liên hệ
            for (Contact c : contacts) {
                if (c.getPhoneNumber().equals(phoneNumber)) {
                    contactRemove = c;
                    break;
                }
            }

            if (contactRemove == null) {
                System.out.println("Không tìm được danh bạ với số điện thoại trên. Vui lòng thử lại.");
            } else {
                System.out.print("Nhập Y để xác nhận xóa " + contactRemove.getName() + " khỏi danh bạ, nhập N để hủy yêu cầu: ");
                String confirm = scanner.nextLine().trim();

                if (confirm.equalsIgnoreCase("Y")) {
                    contacts.remove(contactRemove);
                    System.out.println("✔ Đã xóa " + contactRemove.getName() + " khỏi danh bạ.");
                    return; // Thoát khỏi phương thức sau khi xóa thành công
                } else {
                    System.out.println("Hủy yêu cầu xóa.");
                    return; // Thoát khỏi phương thức khi hủy
                }
            }
        }
    }

    @Override
    public void searchFromCSV() {
        List<Contact> searchContact = new ArrayList<>();

        System.out.println("Nhập số điện thoại hoặc tên liên hệ cần tìm kiếm: ");
        String str = scanner.nextLine().trim().toLowerCase();

        for (Contact c : contacts) {
            if (c.getPhoneNumber().toLowerCase().contains(str) ||
                c.getName().toLowerCase().contains(str)) {
                searchContact.add(c);
            }
        }
        // Kiểm tra xem có tìm thấy liên hệ nào không
        if (searchContact.isEmpty()) {
            System.out.println("Không tìm thấy liên hệ nào.");
        } else {
            System.out.println("Danh sách liên hệ phù hợp với yêu cầu của bạn:");
            for (Contact c : searchContact) {
                System.out.println(c);
            }
        }
    }

    @Override
    public void sortContactsByName() {
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });

        System.out.println("Danh Sách Liên Hệ Đã Được Sắp Xếp Theo Họ Tên:");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public String promptPhoneNumber(Scanner scanner) {
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            String phoneNumber = scanner.nextLine();
            if (Pattern.matches("0[35789][0-9]{8}", phoneNumber)) {
                return phoneNumber;
            } else {
                System.out.println("SĐT chuẩn phải có 10 số và bắt đầu bằng số '0'. Vui lòng nhập lại!");
            }
        }
    }

    public String promptGroup(Scanner scanner) {
        while (true) {
            System.out.print("Nhập nhóm: ");
            String group = scanner.nextLine();
            if (Pattern.matches("[\\p{L} ]+", group)) {
                return group;
            } else {
                System.out.println("Tên nhóm phải là chữ hoặc số. Vui lòng nhập lại!");
            }
        }
    }

    public String promptName(Scanner scanner) {
        while (true) {
            System.out.print("Nhập họ tên: ");
            String name = scanner.nextLine();
            if (Pattern.matches("[\\p{L} ]+", name)) {
                return name;
            } else {
                System.out.println("Họ tên phải là chữ. Vui lòng nhập lại!");
            }
        }
    }

    public String promptGender(Scanner scanner) {
        while (true) {
            System.out.print("Nhập giới tính (Nam/Nữ): ");
            String gender = scanner.nextLine();
            if (gender.equalsIgnoreCase("Nam") || gender.equalsIgnoreCase("Nữ")) {
                return gender;
            } else {
                System.out.println("Giới tính phải là (Nam/Nữ). Vui lòng nhập lại!");
            }
        }
    }

    public String promptAddress(Scanner scanner) {
        while (true) {
            System.out.print("Nhập địa chỉ: ");
            String address = scanner.nextLine();
            if (Pattern.matches("[\\p{L}0-9-., ]+", address)) {
                return address;
            } else {
                System.out.println("Địa chỉ phải là chữ hoặc số. Vui lòng nhập lại!");
            }
        }
    }

    public String promptDateOfBirth(Scanner scanner) {
        while (true) {
            System.out.print("Nhập ngày sinh (YYYY-MM-DD): ");
            String dateOfBirth = scanner.nextLine();
            if (isValidDate(dateOfBirth)) {
                return dateOfBirth;
            } else {
                System.out.println("Ngày sinh không hợp lệ. Vui lòng nhập lại!");
            }
        }
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            return true; // Ngày hợp lệ
        } catch (DateTimeParseException e) {
            return false; // Ngày không hợp lệ
        }
    }

    public String promptEmail(Scanner scanner) {
        while (true) {
            System.out.print("Nhập Email: ");
            String email = scanner.nextLine();
            if (Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
                return email;
            } else {
                System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }
}