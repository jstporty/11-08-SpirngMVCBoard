package kr.kwangan2.springmvcboard.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyPageDTO {
	private int replyCnt;
	private List<ReplyVO> list;
}
