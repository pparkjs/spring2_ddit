package kr.or.ddit.dao.member;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.MemberVO;

@Repository
public class MemberDAOImpl implements IMemberDAO {
	
	@Inject
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int memberJoin(MemberVO mem) {
		return sqlSession.insert("Member.memberJoin", mem);
	}

	@Override
	public MemberVO loginCheck(MemberVO mem) {
		return sqlSession.selectOne("Member.loginCheck", mem);
	}

}
