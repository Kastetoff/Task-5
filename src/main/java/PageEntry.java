public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "\npdfName=" + pdfName +
                "\npage=" + page +
                "\ncount=" + count +
                "\n}";
    }

    @Override
    public int compareTo(PageEntry word) {
        if (word.getCount() > getCount()) {
            return 1;
        } else if (word.getCount() < getCount()) {
            return -1;
        } else return 0;
    }
}
