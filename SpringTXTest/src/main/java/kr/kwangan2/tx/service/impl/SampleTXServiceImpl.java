package kr.kwangan2.tx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.kwangan2.tx.mapper.TBL1Mapper;
import kr.kwangan2.tx.mapper.TBL2Mapper;
import kr.kwangan2.tx.service.SampleTXService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SampleTXServiceImpl implements SampleTXService {
	
	@Setter(onMethod_=@Autowired)
	private TBL1Mapper tbl1mapper;

	@Setter(onMethod_=@Autowired)
	private TBL2Mapper tbl2mapper;
	
	@Transactional      //이거를 붙이냐 안붙이냐가 트렌젝션처리함 ㅋ
	@Override
	public void addData(String value) {
		tbl1mapper.insertCol1(value);
			tbl2mapper.insertCol2(value);
	}
	
}





