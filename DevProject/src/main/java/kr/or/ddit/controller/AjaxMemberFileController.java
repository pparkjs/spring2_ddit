package kr.or.ddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/ajax")
@Slf4j
public class AjaxMemberFileController {

	
	@GetMapping("/registerFileForm")  // 메소드방식이 GET이면서 Mapping 하는거
	@PostMapping("/registerFileForm") // 메소드방식이 POST이면서 Mapping 하는거
//	@RequestMapping(value="/registerFileForm", method = RequestMethod.GET)
	public String ajaxRegisterFileForm() {
		return "member/ajaxRegisterFile";
	}
	
	// produces는 ajax에서 dataType설정한 거에 대한 응답 타입을 설정할 수도 있고, ajax에서 headers 관한 거 키 역할을 할 수도 있다.  
	@RequestMapping(value="/uploadAjax", method = RequestMethod.POST, produces="text/plain; charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file){
		String originalFileName = file.getOriginalFilename();
		log.info("uploadAjax() 실행...!");
		log.info("originalFIleName : " + originalFileName);
		return new ResponseEntity<String>("UPLOAD SUCCESS", HttpStatus.OK);
	}
}
