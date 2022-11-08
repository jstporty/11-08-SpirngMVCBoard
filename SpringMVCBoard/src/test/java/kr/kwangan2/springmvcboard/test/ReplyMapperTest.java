package kr.kwangan2.springmvcboard.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import kr.kwangan2.springmvcboard.domain.Criteria;
import kr.kwangan2.springmvcboard.domain.ReplyVO;
import kr.kwangan2.springmvcboard.service.ReplyService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTest {
	@Setter(onMethod_ = @Autowired)
	private ReplyService replyService;
	
	MockMvc mockMvc;
	
	//@Test
	/*
	public void testlistReplyVO() {
		List<ReplyVO>list = replyService.listReplyVO();
		assertNotNull(list);
		log.info(list);
	}*/
	
	//@Test
	public void testreplyVOListCount() {
		int result = replyService.replyVOListCount(3L);
		assertTrue(result>0);
	}
	
	//@Test
	public void testselectReplyVO() {
		ReplyVO replyVO = replyService.selectReplyVO(1L);
		assertNotNull(replyVO);
		log.info(replyVO);
	}
	//@Test
	public void testinsertReplyVO() {
		ReplyVO replyVO = new ReplyVO();
		replyVO.setBno(3L);
		replyVO.setReply("���ο� ���");
		replyVO.setReplyer("���ο� ��� �ۼ���");
		int result = replyService.insertReplyVO(replyVO);
		assertTrue(result>0);
		log.info("--------------------"+result);
	}
	//@Test
	public void testupdateReplyVO() {
		ReplyVO replyVO = new ReplyVO();
		replyVO.setRno(3L);
		replyVO.setReply("������ ���");
		replyVO.setReplyer("������ ��� �ۼ���");
		int result = replyService.updateReplyVO(replyVO);
		assertNotNull(result);
		log.info("--------------------"+result);
	}
	@Test
	public void testList2() {
		Criteria criteria = new Criteria(1,5);
		List<ReplyVO> replies = replyService.listReplyVO(criteria, 3L);
		replies.forEach(reply -> log.info(reply));
	}
}




























