package login.practice.member;


import login.practice.domain.members.Member;
import login.practice.domain.members.MemberMemoryRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemberMemoryRepositoryTest {
    private MemberMemoryRepository memberRepository = new MemberMemoryRepository();

    @AfterEach
    void clear() {
        memberRepository.clear();
    }

    @Test
    @DisplayName("회원 등록 성공")
    void signUp_Success() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        memberRepository.add(member1);
        memberRepository.add(member2);

        //when
        Long id1 = memberRepository.add(member1);
        Long id2 = memberRepository.add(member2);

        //then
        assertMemberEquals(member1, memberRepository.findById(id1));
        assertMemberEquals(member2, memberRepository.findById(id2));
    }

    @Test
    @DisplayName("id로 회원 조회 성공")
    void findById_Success() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        memberRepository.add(member1);
        memberRepository.add(member2);

        //when
        Member findMember1 = memberRepository.findById(1L);
        Member findMember2 = memberRepository.findById(2L);

        //then
        assertMemberEquals(member1, findMember1);
        assertMemberEquals(member2, findMember2);
    }

    @Test
    @DisplayName("id로 회원 조회 실패 - 없는 id로 Member 를 조회")
    void findById_Fail() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        memberRepository.add(member1);
        memberRepository.add(member2);

        //when
        Member findMember1 = memberRepository.findById(5L);
        Member findMember2 = memberRepository.findById(3L);

        //then
        assertThrows(NullPointerException.class, () -> {assertMemberEquals(member1, findMember1);});
        assertThrows(NullPointerException.class, () -> {assertMemberEquals(member2, findMember2);});
    }

    @Test
    @DisplayName("loginId로 회원 조회 성공")
    void findByLoginId_Success() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        memberRepository.add(member1);
        memberRepository.add(member2);

        //when
        Member findMember1 = memberRepository.findByLoginId("test").orElse(null);
        Member findMember2 = memberRepository.findByLoginId("test2").orElse(null);

        //then
        assertMemberEquals(member1, findMember1);
        assertMemberEquals(member2, findMember2);
    }

    @Test
    @DisplayName("loginId로 회원 조회 실패 - 없는 loginId로 Member 를 조회")
    void findByLoginId_fail() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        memberRepository.add(member1);
        memberRepository.add(member2);

        //when
        Member findMember1 = memberRepository.findByLoginId("test3").orElse(null);
        Member findMember2 = memberRepository.findByLoginId("test4").orElse(null);

        //then
        assertThrows(NullPointerException.class, () -> {assertMemberEquals(member1, findMember1);});
        assertThrows(NullPointerException.class, () -> {assertMemberEquals(member2, findMember2);});
    }

    @Test
    @DisplayName("모든 회원 조회")
    void findAll() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        memberRepository.add(member1);
        memberRepository.add(member2);

        //when
        List<Member> members = memberRepository.findAll();

        //then
        assertEquals(2, members.size());
    }

    @Test
    @DisplayName("회원 삭제 성공")
    void delete_Success() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        Long id1 = memberRepository.add(member1);
        Long id2 = memberRepository.add(member2);

        //when
        Member deleteMember1 = memberRepository.delete(id1);

        //then
        assertMemberEquals(member1, deleteMember1);
        assertEquals(1, memberRepository.findAll().size());

        //when
        Member deleteMember2 = memberRepository.delete(id2);

        //then
        assertMemberEquals(member2, deleteMember2);
        assertEquals(0, memberRepository.findAll().size());
    }

    @Test
    @DisplayName("회원 삭제 실패 - 없는 id의 Member 를 삭제")
    void delete_fail() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        memberRepository.add(member1);
        memberRepository.add(member2);

        //when
        Member deleteMember1 = memberRepository.delete(3L);

        //then
        assertThrows(NullPointerException.class, () -> {deleteMember1.getClass();});
        assertEquals(2, memberRepository.findAll().size());

        //when
        Member deleteMember2 = memberRepository.delete(4L);

        //then
        assertNull(deleteMember2);
        assertEquals(2, memberRepository.findAll().size());
    }

    @Test
    @DisplayName("회원 정보 수정 성공")
    void update_Success() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        Long id1 = memberRepository.add(member1);
        Long id2 = memberRepository.add(member2);

        //when
        Member updateMemberForm1 = new Member.Builder().setLoginId("test3").setPassword("test#").setName("테스터3").build();
        updateMemberForm1.setId(id1);
        Member updatedMember1 = memberRepository.update(updateMemberForm1);

        //then
        assertMemberEquals(updateMemberForm1, updatedMember1);
        assertEquals(id1, updatedMember1.getId());

        //when
        Member updateMemberForm2 = new Member.Builder().setLoginId("test4").setPassword("test$").setName("테스터4").build();
        updateMemberForm2.setId(id2);
        Member updatedMember2 = memberRepository.update(updateMemberForm2);

        //then
        assertMemberEquals(updateMemberForm2, updatedMember2);
        assertEquals(id2, updatedMember2.getId());

    }

    @Test
    @DisplayName("회원 정보 수정 실패 - 없는 id의 member 를 수정")
    void update_fail() {
        //given
        Member member1 = new Member.Builder()
                .setLoginId("test")
                .setPassword("test!")
                .setName("테스터")
                .build();
        Member member2 = new Member.Builder()
                .setLoginId("test2")
                .setPassword("test@")
                .setName("테스터2")
                .build();
        memberRepository.add(member1);
        memberRepository.add(member2);

        //when
        List<Member> beforeUpdate1 = memberRepository.findAll();
        Member updateMemberForm1 = new Member.Builder().setLoginId("test3").setPassword("test#").setName("테스터3").build();
        updateMemberForm1.setId(3L);
        Member updatedMember1 = memberRepository.update(updateMemberForm1);
        List<Member> afterUpdate = memberRepository.findAll();

        //then
        assertNull(updatedMember1);
        assertEquals(beforeUpdate1, afterUpdate);

        //when
        List<Member> beforeUpdate2 = memberRepository.findAll();
        Member updateMemberForm2 = new Member.Builder().setLoginId("test4").setPassword("test$").setName("테스터4").build();
        updateMemberForm2.setId(4L);
        Member updatedMember2 = memberRepository.update(updateMemberForm2);
        List<Member> afterUpdate2 = memberRepository.findAll();

        //then
        assertNull(updatedMember2);
        assertEquals(beforeUpdate2, afterUpdate2);

    }

    private void assertMemberEquals(Member member, Member compMember) {
        assertEquals(member.getLoginId(), compMember.getLoginId());
        assertEquals(member.getPassword(), compMember.getPassword());
        assertEquals(member.getName(), compMember.getName());
    }

}
