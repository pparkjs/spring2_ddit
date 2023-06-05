package kr.or.ddit.controller.noticeboard.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.noticeboard.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;

@Controller
@RequestMapping("/notice")
public class NoticeModifyController {

	@Inject
	private INoticeService noticeService;
	
	// 수정하기 폼으로 이동
	@RequestMapping(value="/update.do", method = RequestMethod.GET)
	public String noticeModifyForm(int boNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		model.addAttribute("notice", noticeVO);
		model.addAttribute("status", "u");
		return "notice/form";
	}
	
	// 수정하기 처리 메소드
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String noticeModify(NoticeVO noticeVO, Model model) {
		String goPage = "";
		ServiceResult result = noticeService.updateNotice(noticeVO);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/notice/detail.do?boNo=" + noticeVO.getBoNo();
		}else {
			model.addAttribute("message", "수정에 실패하였습니다!");
			model.addAttribute("noticeVO", noticeVO);
			model.addAttribute("status", "u");
			goPage = "notice/form";
		}
		return goPage;
	}
	
	// 삭제하는 메소드
	@RequestMapping(value="/delete.do", method = RequestMethod.POST)
	public String noticeDelete(int boNo, Model model) {
		String goPage = "";
		ServiceResult result = null;
		
		
		result = noticeService.deleteNotice(boNo);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/notice/list.do";
		}else {
			model.addAttribute("message", "서버오류, 다시시도해주세요!");
			goPage = "redirect:/notice/detail.do?boNo=" + boNo;
		}
		return goPage;
	}
}
