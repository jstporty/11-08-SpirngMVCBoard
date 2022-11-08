package kr.kwangan2.springmvcboard.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.kwangan2.springmvcboard.domain.BoardVO;
import kr.kwangan2.springmvcboard.domain.Criteria;
import kr.kwangan2.springmvcboard.mapper.BoardMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardMapperTests {
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	private BoardVO boardVO;
	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
	
	//@Test
	public void testGetList() {
		mapper.boardVOList().forEach(board -> log.info(board));
	}
	//@Test
	public void testinsertBoardVO() {
		boardVO = new BoardVO();
		boardVO.setTitle("새글");
		boardVO.setContent("새내용");
		boardVO.setWriter("전민재");
		mapper.insertBoardVO(boardVO);
		log.info(boardVO);
	}
	//@Test
	public void testBoardVOSelectkey() {
		boardVO = new BoardVO();
		boardVO.setTitle("신제목SK");
		boardVO.setContent("신내용SK");
		boardVO.setWriter("신작성자SK");
		mapper.inserBoardVOSelectKey(boardVO);
		log.info(boardVO);
	}
	//@Test
	public void testSelectBoard() {
		boardVO=mapper.selectBoardVO(4L);
		log.info(boardVO);
	}
	//@Test
	public void testDeleteBoard() {
		int result = mapper.deleteBoardVO(35L);
		log.info(result);
	}
	//@Test
	public void testUpdateBoard() {
		boardVO = new BoardVO();
		boardVO.setBno(1L);
		boardVO.setTitle("수정된 글");
		boardVO.setContent("수정된 내용");
		boardVO.setWriter("수정된 글쓴이");
		mapper.updateBoardVO(boardVO);
	}
	
	//@Test
	public void testPaging() {
		Criteria criteria = new Criteria();
		criteria.setPageNum(3);
		criteria.setAmount(3);
		List<BoardVO>list = mapper.boardVOList(criteria);
		list.forEach(board -> log.info("hello"+board));;
	}
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	@Test
	public void testListPaging() throws Exception{
		log.info("test"+
				mockMvc.perform(
						MockMvcRequestBuilders.get("/board/list")
						.param("pagNum", "2")
						.param("amount", "10"))
						.andReturn().getModelAndView().getModelMap()
				);
	}
}















