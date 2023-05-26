package kr.or.ddit.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.member.IMemberService;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PaginationInfoVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/pages")
public class MemberController {
	
	@Inject
	private IMemberService memService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)	
	public String signinForm() {
		return "pages/ddit_signin";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String joinForm() {
		return "pages/ddit_signup";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(MemberVO mem, Model model, RedirectAttributes msg) {
		String goPage = "";
		log.info("mem_agree : " + mem.getMemAgree());
		log.info("mem_gender : " + mem.getMemGender());
		// 데이터가 하나라도 누락된 경우
		if(StringUtils.isBlank(mem.getMemAgree()) || StringUtils.isBlank(mem.getMemId()) ||
		   StringUtils.isBlank(mem.getMemPw()) || StringUtils.isBlank(mem.getMemName()) ||
		   StringUtils.isBlank(mem.getMemGender()) || StringUtils.isBlank(mem.getMemPhone()) ||
		   StringUtils.isBlank(mem.getMemEmail()) || StringUtils.isBlank(mem.getMemAgree())){
			model.addAttribute("miss", "miss");
			model.addAttribute("mem", mem);
			goPage = "pages/ddit_signup";
		}else {
			ServiceResult result = memService.memberJoin(mem);
			if(result.equals(ServiceResult.OK)) {
				// 일회성 데이터
				msg.addFlashAttribute("msg", "success");
				goPage = "redirect:/pages/login";
			}else {
				model.addAttribute("error", "가입이 실패했습니다! 다시 시도해주세요!");
				goPage = "pages/ddit_signup";
			}
		}
		
		return goPage;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(MemberVO mem, Model model,	HttpSession session) {
		String goPage = "";
		if(StringUtils.isBlank(mem.getMemId()) || StringUtils.isBlank(mem.getMemPw())) {
			model.addAttribute("miss", "miss");
			goPage = "pages/ddit_signin";
		}else {
			MemberVO vo = memService.loginCheck(mem);
			log.info("vo : " + vo);
			if(vo != null) {
				session.setAttribute("mem", vo);
				goPage = "redirect:/board/list";
			}else {
				model.addAttribute("error", "error");
				goPage = "pages/ddit_signin";
			}
		}
		return goPage;
	}
	
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate(); // 세션삭제
		return "redirect:/pages/login";
	}
}
