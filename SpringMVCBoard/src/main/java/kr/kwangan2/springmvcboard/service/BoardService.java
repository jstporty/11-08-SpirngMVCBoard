package kr.kwangan2.springmvcboard.service;

import java.util.List;

import kr.kwangan2.springmvcboard.domain.BoardVO;
import kr.kwangan2.springmvcboard.domain.Criteria;

public interface BoardService {
	
	public List<BoardVO> boardVOList();

	public int insertBoardVO(BoardVO boardVO);

	public int inserBoardVOSelectKey(BoardVO boardvo);

	public BoardVO selectBoardVO(Long bno);

	public int deleteBoardVO(Long bno);

	public int updateBoardVO(BoardVO boardvo);
	
	public List<BoardVO>boardList(Criteria criteria);
	
	public int boardVOListCount(Criteria criteria);
	
	public void updateReplycnt(Long bno, int amount);
}
