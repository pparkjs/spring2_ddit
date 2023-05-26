package kr.or.ddit.dao.board;

import java.util.List;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IBoardDAO {

	public int selectBoardCount(PaginationInfoVO<BoardVO> pagingVO);

	public List<BoardVO> selectBoardBoardList(PaginationInfoVO<BoardVO> pagingVO);

	public BoardVO selectPostByNo(int boNo);

	public void updateHit(int boNo);

}
