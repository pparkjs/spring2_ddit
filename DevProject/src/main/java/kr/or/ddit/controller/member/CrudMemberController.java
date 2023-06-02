package kr.or.ddit.controller.member;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.IMemberService;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/crud/member")
@Slf4j
public class CrudMemberController {

	@Inject
	private IMemberService service;
	
	@GetMapping(value="/register")
	public String crudMemberRegisterForm() {
		log.info("crudMemberRegisterForm() 실행..!");
		return "crud/member/register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String crudMemberRegister(MemberVO member, Model model) {
		log.info("crudMemberRegister() 실행...!");
		service.register(member);
		model.addAttribute("msg", "등록이 완료되었습니다.");
		return "crud/member/success";
	}
	
	@GetMapping(value="/list")
	public String list(Model model) {
		List<MemberVO> memberList = service.list();
		model.addAttribute("memberList", memberList);
		return "crud/member/list";
	}
	
	@GetMapping(value="/read")
	public String read(int userNo, Model model) {
		MemberVO member = service.read(userNo);
		model.addAttribute("member", member);
		return "crud/member/read";
	}
	
	// 수정하기 폼 이동 메소드
	@RequestMapping(value="/modify", method = RequestMethod.GET)
	public String modifyForm(int userNo, Model model) {
		MemberVO member = service.read(userNo);
		model.addAttribute("member", member);
		return "crud/member/modify";
	}
	
	// 수정하기 처리 메소드
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modify(MemberVO member, Model model) {
		service.modify(member);
		model.addAttribute("mgs", "수정이 완료 되었습니다.");
		return "crud/member/success";
	}
	
	// 삭제하기 메소드
	@RequestMapping(value="/remove", method = RequestMethod.POST)
	public String remove(int userNo, Model model) {
		service.remove(userNo);
		model.addAttribute("msg", "삭제가 완료되었습니다.");
		return "crud/member/success";
	}
}
