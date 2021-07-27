package login.practice.domain.members;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public List<Member> findAll();
    public Member findById(Long id);
    public Optional<Member> findByLoginId(String loginId);
    public Long add(Member member);
    public Member delete(Long id);
    public Member update(Member member);
}
