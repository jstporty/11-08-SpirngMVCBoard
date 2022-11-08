package kr.kwangan2.tx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.kwangan2.tx.service.SampleTXService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class SampleTXTest {

	@Setter(onMethod_ = @Autowired)
	private SampleTXService service;

	@Test
	public void testLong() {

		String str = "dsfsdfsdkfjasdkhgf;ldksajgd;kskjfsdlsljfl";
		log.info(str.getBytes().length);
		service.addData(str);
	}

}
