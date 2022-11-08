package kr.kwangan2.springmvcboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.kwangan2.springmvcboard.domain.BoardVO;
import kr.kwangan2.springmvcboard.domain.Criteria;

public interface BoardMapper {
	//@Select(" select * from board where bno>0 ")
	public List<BoardVO> boardVOList();
	
	public List<BoardVO> boardVOList(Criteria croteria);
	
	public int insertBoardVO(BoardVO boardVO);
	
	public int inserBoardVOSelectKey(BoardVO boardvo);
	
	public BoardVO selectBoardVO(Long bno);
	
	public int deleteBoardVO(Long bno);
	
	public int updateBoardVO(BoardVO boardvo);
	
	public int boardVOListCount(Criteria criteria);
	
	public void updateReplycnt(
			@Param("bno") long bno,
			@Param("amount") int amount
			);
}
