package kr.or.ddit.service.member;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.MemberVO;

public interface IMemberService {

	//회원가입
	public ServiceResult memberJoin(MemberVO mem);

	//로그인 체크
	public MemberVO loginCheck(MemberVO mem);

}
