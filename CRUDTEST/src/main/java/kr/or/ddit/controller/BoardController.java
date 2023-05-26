package kr.or.ddit.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.service.board.IBoardService;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
	
	@Inject
	private IBoardService boardService;
	
	@RequestMapping(value="/list")
	public String boardList(
			@RequestParam(name="page", required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false)
			String searchWord,
			Model model) {
		
		PaginationInfoVO<BoardVO> pagingVO = new PaginationInfoVO<BoardVO>();
		
		// 검색 했을 때
		if(StringUtils.isNotBlank(searchWord)) {
			pagingVO.setSearchType(searchType);
			pagingVO.setSearchWord(searchWord);
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchWord", searchWord);
		}
		
		pagingVO.setCurrentPage(currentPage);
		// 목록 총 게시글 수 가져오기
		int totalRecord = boardService.selectBoardCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<BoardVO> dataList = boardService.selectBoardBoardList(pagingVO);
		pagingVO.setDataList(dataList);
		model.addAttribute("pagingVO", pagingVO);
		return "pages/ddit_list";
	}
	
	@RequestMapping(value="/detail", method = RequestMethod.GET)
	public String detail(int boNo, Model model) {
		String goPage = "";
		BoardVO vo = boardService.selectPostByNo(boNo);
		if(vo == null) {
			goPage= "redirect:/board/list";
		}else {
			model.addAttribute("board",vo);
			goPage = "pages/ddit_detail";
		}
		return goPage;
	}
}
