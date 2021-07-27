package login.practice.domain.members;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemberMemoryRepository implements MemberRepository{

    private static Map<Long, Member> memberStore = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(memberStore.values());
    }

    @Override
    public Member findById(Long id) {
        return memberStore.get(id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {

        return memberStore.values()
                .stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }


    @Override
    public Long add(Member member) {
        member.setId(++sequence);
        memberStore.put(member.getId(), member);
        return member.getId();
    }

    @Override
    public Member delete(Long id) {
        Member member = findById(id);
        memberStore.remove(id);
        return member;
    }

    @Override
    public Member update(Member member) {
        return memberStore.replace(member.getId(), member);
    }

    public void clear() {
        memberStore.clear();
    }

}
