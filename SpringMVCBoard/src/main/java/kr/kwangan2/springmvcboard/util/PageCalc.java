package kr.kwangan2.springmvcboard.util;

import kr.kwangan2.springmvcboard.domain.Criteria;
import lombok.Getter;

@Getter
public class PageCalc {

   public int startPage;
   public int endPage;
   public boolean prev;
   public boolean next;
   public int total;

   private Criteria criteria;

   public PageCalc(Criteria criteria, int total) {
      this.criteria = criteria;
      this.total = total;
   }

   public PageCalc calcPage() {
      this.endPage = (int) (Math.ceil(this.criteria.getPageNum() / 10.0)) * 10;
      // 시작페이지 번호
      this.startPage = this.endPage - 9;

      int realEnd = (int) (Math.ceil(this.total * 1.0) / this.criteria.getAmount());
      // 블럭내에 10개 페이지가 안될때 끝페이지 번호 설정
      if (realEnd < this.endPage) {
         this.endPage = realEnd;
      }

      this.prev = this.startPage > 1;
      this.next = this.endPage < realEnd;

      return this;
   }

}