package kr.kwangan2.springmvcboard.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyVO {
	
	private Long rno;
	private Long bno;
	
	private String reply;
	private String replyer;
	private Date replydate;
	private Date updatedate;
	
	
	public ReplyVO(Long rno, String reply, String replyer) {
		this.rno = rno;
		this.reply = reply;
		this.replyer = replyer;
	}
}
