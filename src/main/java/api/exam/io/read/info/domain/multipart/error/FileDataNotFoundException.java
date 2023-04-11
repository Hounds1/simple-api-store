package api.exam.io.read.info.domain.multipart.error;

public class FileDataNotFoundException extends IllegalStateException{
    public FileDataNotFoundException(String s) {
        super(s);
    }
}
