package api.exam.io.read.info.domain.product.error;

public class ProductNotFoundException extends IllegalStateException{
    public ProductNotFoundException(String s) {
        super(s);
    }
}
