package com.health.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.health.dto.ProgramDto;
import com.health.dto.ProgramFormDto;
import com.health.service.ProgramService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProgramController {

	private final ProgramService programService;

	/* 사용자 */

	// 프로그램 리스트를 보여줌
	@GetMapping(value = "/program/programPage")
	public String programPageList(Model model) {
		List<ProgramDto> programList = programService.getProgramList();
		model.addAttribute("programs", programList);
		return "/program/programPage";
	}

	
	
	/* 관리자 */

	// 프로그램 등록 페이지를 보여줌
	@GetMapping(value = "/admin/program/programForm")
	public String programPage(Model model) {
		model.addAttribute("programFormDto", new ProgramFormDto());
		return "/program/programForm";
	}

	// 프로그램 등록
	@PostMapping(value = "/admin/program/new")
	public String itemNew(@Valid ProgramFormDto programFormDto, BindingResult bindingResult, Model model,
			@RequestParam("programImgFile") List<MultipartFile> programImgFileList) {

		if (bindingResult.hasErrors()) {
			return "program/programForm";
		}

		// 첫번째 이미지가 있는지 검사(첫번째 이미지는 필수 입력값이기 때문에)
		if (programImgFileList.get(0).isEmpty() && programFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "program/programForm";
		}

		try {
			programService.saveProgram(programFormDto, programImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "program/programForm";
		}

		return "redirect:/";
	}
	
	//상품 상세 페이지
	@GetMapping(value = "/order/orderPage/{programId}")
	public String programDtl(Model model, @PathVariable("programId") Long programId) {
		ProgramFormDto programFormDto = programService.getProgramDtl(programId);
		model.addAttribute("program", programFormDto);
		return "order/orderPage";
	}

}
