package com.ll.basic1.boundedContext.member.repository;

import com.ll.basic1.boundedContext.member.entity.Member;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Component
@Repository // 컴포넌트랑 같음
public class MemberRepository {
    private List<Member> members;

    public MemberRepository() {
        members = new ArrayList<>();
        members.add(new Member("user1", "1234"));
        members.add(new Member("user2", "12345"));
        members.add(new Member("user3", "12346"));
        members.add(new Member("user4", "12347"));
        members.add(new Member("user5", "12348"));
        members.add(new Member("user6", "12349"));
        members.add(new Member("user7", "123410"));
        members.add(new Member("user8", "123411"));
        members.add(new Member("user9", "123412"));
        members.add(new Member("user10", "123413"));
    }

    public Member findByUserName(String username) {
        return members.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Member findById(long id) {
        return members.stream()
                .filter(member -> member.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
