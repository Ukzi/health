package com.health.dto;

import com.health.entity.Program;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramDto {
	
    public ProgramDto(Program program, String imgUrl){
        this.id = program.getId();
        this.programNm = program.getProgramNm();
        this.imgUrl = imgUrl;
    }
    
	private Long id; //상품코드

	private String programNm; //상품명
	
	private String imgUrl; //이미지 조회 경로
		
	//private int price; //가격

	//private String programDetail; //상품 상세설명
		
	//private ProgramSellStatus programSellStatus; //상품 판매상태
}