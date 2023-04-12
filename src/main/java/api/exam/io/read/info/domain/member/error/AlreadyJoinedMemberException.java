package api.exam.io.read.info.domain.member.error;

public class AlreadyJoinedMemberException extends IllegalStateException{
    public AlreadyJoinedMemberException(String s) {
        super(s);
    }
}
