public interface IManager<E> {
    void displayCSV();
    void add();
    void update();
    void remove();
    void searchFromCSV();
    void sortContactsByName();
    void writeContactsToCSV();
    void readContactFromCSV();
}