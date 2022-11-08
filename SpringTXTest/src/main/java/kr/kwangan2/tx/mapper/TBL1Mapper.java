package kr.kwangan2.tx.mapper;

import org.apache.ibatis.annotations.Insert;

public interface TBL1Mapper {

	@Insert("insert into tbl1 (col1) values (#{data})")
	public int insertCol1(String data);
}
