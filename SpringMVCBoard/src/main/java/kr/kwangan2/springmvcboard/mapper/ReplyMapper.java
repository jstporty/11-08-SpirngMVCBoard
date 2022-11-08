package kr.kwangan2.springmvcboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.kwangan2.springmvcboard.domain.Criteria;
import kr.kwangan2.springmvcboard.domain.ReplyVO;

public interface ReplyMapper {
	public List<ReplyVO> listReplyVO(
			@Param("criteria") Criteria criteria,
			@Param("bno") Long bno
			);
	
	public int replyVOListCount(Long bno);
	
	public ReplyVO selectReplyVO(Long rno);
	
	public int insertReplyVO(ReplyVO reply);
	
	public int updateReplyVO(ReplyVO reply);
	
	public int deleteReplyVO(Long rno);
}
