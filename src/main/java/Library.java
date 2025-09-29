public class Library {
    private Catalogue catalogue;

    public Library() { catalogue = new Catalogue(); }

    public void initializeLibrary(){}

    public int getCatalogueSize(){ return 0; }

    public Book getBookByTitle(String title){
        Book targetBook = new Book("", "");
        return targetBook;
    }

    public Book getBookByIndex(int index) {
        if (index < 0 || index >= catalogue.getSize()) {
            return null;
        }
        return catalogue.getBook(index);
    }


}
