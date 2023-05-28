package kr.or.ddit.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.dao.board.IBoardDAO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Service
public class BoardServiceImpl implements IBoardService {

	@Inject
	private IBoardDAO boardDao;
	
	@Override
	public int selectBoardCount(PaginationInfoVO<BoardVO> pagingVO) {
		return boardDao.selectBoardCount(pagingVO);
	}

	@Override
	public List<BoardVO> selectBoardBoardList(PaginationInfoVO<BoardVO> pagingVO) {
		return boardDao.selectBoardBoardList(pagingVO);
	}

	@Override
	public BoardVO selectPostByNo(int boNo) {
		boardDao.updateHit(boNo);
		return boardDao.selectPostByNo(boNo);
	}

	@Override
	public ServiceResult insertBoard(BoardVO board) {
		ServiceResult result = null;
		
		int status = boardDao.insertBoard(board);
		
		if(status > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public ServiceResult updateBoard(BoardVO board) {
		ServiceResult result = null;
		
		int status = boardDao.updateBoard(board);
		
		if(status > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public ServiceResult deleteBoard(int boNo) {
		ServiceResult result = null;
		
		int status = boardDao.deleteBoard(boNo);
		
		if(status > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

}
