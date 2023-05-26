package kr.or.ddit.service.member;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.dao.member.IMemberDAO;
import kr.or.ddit.vo.MemberVO;

@Service
public class MemberServiceImpl implements IMemberService {
	
	@Inject
	private IMemberDAO memDao;
	
	@Override
	public ServiceResult memberJoin(MemberVO mem) {
		ServiceResult result = null;
		mem.setMemAgree("Y");
		int status = memDao.memberJoin(mem);
		if(status > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public MemberVO loginCheck(MemberVO mem) {
		MemberVO vo = memDao.loginCheck(mem);
		return vo;
	}

}
