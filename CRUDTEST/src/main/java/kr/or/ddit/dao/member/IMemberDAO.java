package kr.or.ddit.dao.member;

import kr.or.ddit.vo.MemberVO;

public interface IMemberDAO {

	public int memberJoin(MemberVO mem);

	public MemberVO loginCheck(MemberVO mem);

}
