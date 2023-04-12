package api.exam.io.read.info.domain.auth.error;

public class AuthInfoMismatchException extends IllegalAccessException {
    public AuthInfoMismatchException(String s) {
        super(s);
    }
}
