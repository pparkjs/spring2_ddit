package kr.or.ddit.controller.noticeboard.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.noticeboard.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;

@Controller
@RequestMapping("/notice")
public class NoticeInsertController {
	
	@Inject
	private INoticeService noticeService;
	
	// 등록 폼으로 이동하는 메소드
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public String noticeInsertForm() {
		return "notice/form";
	}
	
	// 게시글 등록하는 메소드
	@RequestMapping(value="/insert.do", method = RequestMethod.POST)
	public String noticeInsert(NoticeVO noticeVO, Model model) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		if(StringUtils.isBlank(noticeVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요.");
		}
		if(StringUtils.isBlank(noticeVO.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요");
		}
		
		if(errors.size() > 0) { // 에러가 발생
			model.addAttribute("errors", errors);
			model.addAttribute("noticeVO", noticeVO);
			goPage = "notice/form";
		}else {
			noticeVO.setBoWriter("a001"); // 임시로 넣어둔다( 로그인 처리 후 재 셋팅할 예정 )
			ServiceResult result = noticeService.insertNotice(noticeVO);
			if(result.equals(ServiceResult.OK)) {
				goPage = "redirect:/notice/detail.do?boNo="+noticeVO.getBoNo();
			}else {
				model.addAttribute("message", "서버에러, 다시 시도해주세요!");
				goPage = "notice/form";
			}
		}
		return goPage;
	}
	
}
