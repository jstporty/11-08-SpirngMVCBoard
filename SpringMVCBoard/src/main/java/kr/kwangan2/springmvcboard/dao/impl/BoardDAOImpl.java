package kr.kwangan2.springmvcboard.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kwangan2.springmvcboard.domain.BoardVO;
import kr.kwangan2.springmvcboard.domain.Criteria;
import kr.kwangan2.springmvcboard.mapper.BoardMapper;
import kr.kwangan2.springmvcboard.mapper.ReplyMapper;
import lombok.Setter;

@Service
public class BoardDAOImpl extends AbstractBoardDAO {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replymapper;
	
	
	/*
	@Override
	public List<BoardVO> boardVOList() {
		return mapper.boardVOList();
	}
	*/
	
	@Override
	public int insertBoardVO(BoardVO boardVO) {
		return mapper.insertBoardVO(boardVO); 
	}

	@Override
	public int inserBoardVOSelectKey(BoardVO boardvo) {
		return mapper.inserBoardVOSelectKey(boardvo);
	}

	@Override
	public BoardVO selectBoardVO(Long bno) {
		// TODO Auto-generated method stub
		return mapper.selectBoardVO(bno);
	}

	@Override
	public int deleteBoardVO(Long bno) {
		// TODO Auto-generated method stub
		return mapper.deleteBoardVO(bno);
	}

	@Override
	public int updateBoardVO(BoardVO boardvo) {
		// TODO Auto-generated method stub
		return mapper.updateBoardVO(boardvo);
	}

	@Override
	public List<BoardVO> boardList(Criteria criteria) {
		// TODO Auto-generated method stub
		return mapper.boardVOList(criteria);
	}

	@Override
	public int boardVOListCount(Criteria criteria) {
		// TODO Auto-generated method stub
		return mapper.boardVOListCount(criteria);
	}

	@Override
	public void updateReplycnt(Long bno, int amount) {
		// TODO Auto-generated method stub
		mapper.updateReplycnt(bno, amount);
	}
	
	
	
}
