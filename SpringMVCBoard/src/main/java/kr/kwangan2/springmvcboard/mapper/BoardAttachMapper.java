package kr.kwangan2.springmvcboard.mapper;

import java.util.List;

import kr.kwangan2.springmvcboard.domain.BoardAttachVO;

public interface BoardAttachMapper {

	public void insert(BoardMapper vo);

	public void delete(String uuid);

	public List<BoardAttachVO> findByBno(Long bno);
}
