package api.exam.io.read.info.domain.member.error;

public class MemberNotFoundException extends IllegalStateException{
    public MemberNotFoundException(String s) {
        super(s);
    }
}
