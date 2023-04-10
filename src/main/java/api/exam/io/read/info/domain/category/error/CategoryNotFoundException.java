package api.exam.io.read.info.domain.category.error;

public class CategoryNotFoundException extends IllegalStateException{
    public CategoryNotFoundException(String s) {
        super(s);
    }
}
