package api.exam.io.read.info.domain.category.error;

public class NoSearchCategories extends IllegalStateException{
    public NoSearchCategories(String s) {
        super(s);
    }
}
