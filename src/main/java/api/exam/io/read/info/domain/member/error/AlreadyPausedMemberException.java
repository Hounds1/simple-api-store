package api.exam.io.read.info.domain.member.error;

public class AlreadyPausedMemberException extends IllegalStateException{
    public AlreadyPausedMemberException(String s) {
        super(s);
    }
}
