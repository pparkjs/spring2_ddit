package kr.or.ddit.dao.board;

import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Repository
public class BoardDAOImpl implements IBoardDAO {
	
	@Inject
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int selectBoardCount(PaginationInfoVO<BoardVO> pagingVO) {
		return sqlSession.selectOne("Board.selectBoardCount", pagingVO);
	}

	@Override
	public List<BoardVO> selectBoardBoardList(PaginationInfoVO<BoardVO> pagingVO) {
		return sqlSession.selectList("Board.selectBoardBoardList", pagingVO);
	}

	@Override
	public BoardVO selectPostByNo(int boNo) {
		return sqlSession.selectOne("Board.selectPostByNo", boNo);
	}

	@Override
	public void updateHit(int boNo) {
		sqlSession.update("Board.updateHit", boNo);
	}

	@Override
	public int insertBoard(BoardVO board) {
		return sqlSession.insert("Board.insertBoard", board);
	}

	@Override
	public int updateBoard(BoardVO board) {
		return sqlSession.insert("Board.updateBoard", board);
	}

	@Override
	public int deleteBoard(int boNo) {
		return sqlSession.delete("Board.deleteBoard", boNo);
	}

}
