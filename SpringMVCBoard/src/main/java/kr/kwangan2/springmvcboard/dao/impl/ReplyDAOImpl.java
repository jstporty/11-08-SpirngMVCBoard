package kr.kwangan2.springmvcboard.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.kwangan2.springmvcboard.domain.Criteria;
import kr.kwangan2.springmvcboard.domain.ReplyPageDTO;
import kr.kwangan2.springmvcboard.domain.ReplyVO;
import kr.kwangan2.springmvcboard.mapper.BoardMapper;
import kr.kwangan2.springmvcboard.mapper.ReplyMapper;
import kr.kwangan2.springmvcboard.service.ReplyService;
import lombok.Setter;

@Service
//로직 처리를 위한 어노테이션(서비스 레이어, 내부에서 자바 로직을 처리함)
public class ReplyDAOImpl implements ReplyService {
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	
	@Override
	public ReplyPageDTO listReplyVO(Criteria criteria, Long bno) {
		return new ReplyPageDTO(
				mapper.replyVOListCount(bno),
				mapper.listReplyVO(criteria, bno)
				);
	}

	@Override
	public int replyVOListCount(Long bno) {
		return mapper.replyVOListCount(bno);
	}

	@Override
	public ReplyVO selectReplyVO(Long rno) {
		return mapper.selectReplyVO(rno);
	}

	@Override
	public int insertReplyVO(ReplyVO reply) {
		boardMapper.updateReplycnt(reply.getBno(), 1);
		return mapper.insertReplyVO(reply);
	}

	@Override
	public int updateReplyVO(ReplyVO reply) {
		return mapper.updateReplyVO(reply);
	}

	@Transactional
	@Override
	public int deleteReplyVO(Long rno) {
		ReplyVO replyVO = mapper.selectReplyVO(rno);
		boardMapper.updateReplycnt(replyVO.getBno(),-1);
		return mapper.deleteReplyVO(rno);
	}

}
