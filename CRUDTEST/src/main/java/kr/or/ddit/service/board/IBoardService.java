package kr.or.ddit.service.board;

import java.util.List;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IBoardService {

	public int selectBoardCount(PaginationInfoVO<BoardVO> pagingVO);

	public List<BoardVO> selectBoardBoardList(PaginationInfoVO<BoardVO> pagingVO);

	public BoardVO selectPostByNo(int boNo);

}
