package kr.kwangan2.springmvcboard.controller;

import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.kwangan2.springmvcboard.domain.Criteria;
import kr.kwangan2.springmvcboard.domain.ReplyPageDTO;
import kr.kwangan2.springmvcboard.domain.ReplyVO;
import kr.kwangan2.springmvcboard.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/replies/")
@AllArgsConstructor
public class ReplyController {
	@Setter(onMethod_ = @Autowired)
	private ReplyService replyService;

	@PostMapping(value = "/insert", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity insertReplyVO(@RequestBody ReplyVO replyVO) {
		int insertCount = replyService.insertReplyVO(replyVO);
		return insertCount == 1 ? new ResponseEntity("success", HttpStatus.OK)
				: new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		// 다른 조건문을 사용해도됨
	}

	@GetMapping(value = "/pages/{bno}/{page}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ReplyPageDTO> listReplyVO(@PathVariable("bno") Long bno, @PathVariable("page") int page) {
		Criteria criteria = new Criteria(page, 10);
		return new ResponseEntity<>(replyService.listReplyVO(criteria, bno), HttpStatus.OK);
	}

	@GetMapping(value = "/{rno}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ReplyVO> selectReplyVO(@PathVariable("rno") Long rno) {
		return new ResponseEntity<>(replyService.selectReplyVO(rno), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{rno}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> deleteReplyVO(@PathVariable("rno") Long rno) {
		return replyService.deleteReplyVO(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//put방식은 리소스의 모든 것을 업데이트 하고 patch방식은 리소스의 일부를 업데이트함
	@RequestMapping(value = "{rno}", method = { RequestMethod.PUT, RequestMethod.PATCH }, consumes = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity updateReplyVO(@RequestBody ReplyVO replyVO, @PathVariable("rno") Long rno) {
		replyVO.setRno(rno);
		return replyService.updateReplyVO(replyVO) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
