package org.zerock;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Member;
import org.zerock.domain.MemberRole;
import org.zerock.persistence.MemberRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class MemberTests {
	@Autowired
	private MemberRepository repo;
	
	//@Test
	public void testInsert() {
		for(int i=0;i<100;i++) {
			Member member = new Member();
			member.setUid("user"+i);
			member.setUpw("pw"+i);
			member.setUname("사용자"+i);
			
			MemberRole role = new MemberRole();
			if(i <= 80) {
				role.setRoleName("BASIC");
			}else if(i<=90) {
				role.setRoleName("MANAGER");
			}else {
				role.setRoleName("ADMIN");
			}
			List<? extends Object> list = Arrays.asList(new MemberRole[] {role});
			
			member.setRoles((List<MemberRole>)list);
			
			repo.save(member);
		}
	}
	
	
	@Test
	public void testRead() {
		Optional<Member> result = repo.findById("user85");
		result.ifPresent(member->{
			log.info("member:"+member);
			member.getRoles().forEach(role->log.info("role:"+role));
		});
	}
}
