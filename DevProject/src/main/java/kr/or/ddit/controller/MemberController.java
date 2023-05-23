package kr.or.ddit.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

/*
 * 5장 컨트롤러 요청 처리
 * 
 * 	1. 컨트롤러 메소드 매개변수
 * 		Model
 * 		- 이동 대상에 전달할 데이터를 가지고 있는 인터페이스
 * 		RedirectAttributes
 * 		- 리다이렉트 대상에 전달할 데이터를 가지고 있는 인터페이스
 * 		MultipartFile
 * 		- 멀티파트 요청을 사용해 업로드 된 파일 정보를 가지고 있는 인터페이스
 * 		BindingResult
 * 		- 도메인 클래스의 입력값 검증을 가지고 있는 인터페이스
 * 		java.security.Principal
 * 		- 클라이언트 인증을 위한 사용자 정보를 가지고 있는 인터페이스
 * 
 */

@Controller
@Slf4j
public class MemberController {

	/*
	 * 5장 컨트롤러 요청 처리 시작 컨트롤러 메소드
	 * - 페이지를 요청해 테스트를 진행합니다.
	 */
	
	@RequestMapping(value="/registerForm", method = RequestMethod.GET)
	public String registerForm() {
		log.info("registerForm() 실행 ...!");
		return "member/registerForm";
	}
	
	/*
	 * 1. 컨트롤러 메소드 매개변수 (요청처리)
	 */
	
	// 1) URL 경로 상의 파라미터 정보로부터 요청 데이터를 취득할 수 있다.
	@RequestMapping(value= "/register", method = RequestMethod.GET)
	public String registerByParameter(String userId, String password) {
		log.info("registerByParameter() 실행...!");
		log.info("userId : " + userId); 
		log.info("password : " + password); 
		return "success";
	}
	
	// 2) URL 경로 상의 경로 변수로부터 요청 데이터를 취득할 수 있다.
	// - 결과는 null, 아래와 같은 형태로 받으려면 @PathVariable를 이용한다.
	@RequestMapping(value="/register/{userId}", method = RequestMethod.GET)
	public String registerByPath(String userId) {
		// 결과로 userId는 null로 표시된다.
		// 넘겨받은 id를 위와 같은 형태로 받으려면 @PathVariable를 이용한다.
		log.info("registerByPath() 실행...!");
		log.info("userId : " + userId); 
		return "success";
	}
	
	// 3) HTML Form 필드명과 컨트롤러 매개변수명이 일치하면 요청 데이터를 취득할 수 있다.
	@RequestMapping(value="/register01", method = RequestMethod.POST)
	public String register01(String userId) {
		log.info("register01() 실행..!");
		log.info("userId : " + userId);
		return "success";
	}
	
	// 4) HTML Form 필드가 여러개일 경우에도 컨트롤러 매개변수명이 일치하면 요청 데이터를 취득할 수 있다.
	@RequestMapping(value="/register02", method = RequestMethod.POST)
	public String register02(String userId, String password) {
		log.info("register02() 실행...!");
		log.info("userId : " + userId);
		log.info("password : " + password);
		return "success";
	}
	
	// 5) HTML Form 필드가 여러 개일 경우에 컨트룰로 매개변수의 위치는 상관이 있는가?
	@RequestMapping(value="/register03" , method = RequestMethod.POST)
	public String register03(String password, String userId) {
		log.info("register03() 실행...!");
		log.info("password : " + password);
		log.info("userId : " + userId);
		return "success";
	}
	
	// 6) HTML Form 필드값이 숫자일 경우에는 컨트롤러 매개변수 타입이 문자열이면 그대로 문자열 형태로 들어가는가?
	@RequestMapping(value="/register04", method = RequestMethod.POST)
	public String register04(String userId, String password, String coin) {
		log.info("register04() 실행...!");
		log.info("userId : " + userId);
		log.info("password : " + password);
		log.info("coin : " + coin);
		return "success";
	}
	
	// 7) HTML Form 필드값이 숫자일 경우에는 컨트롤러 매개변수 타입이 숫자형이면 숫자로 타입변환하여 데이터를 취득하는가?
	@RequestMapping(value="/register05", method = RequestMethod.POST)
	public String register05(String userId, String password, int coin) {
		log.info("register05() 실행...!");
		log.info("userId : " + userId);
		log.info("password : " + password);
		log.info("coin : " + coin);
		return "success";
	}
	
	/*
	 * 3. 요청 데이터 처리 어노테이션
	 * 
	 * 		@PathVariable
	 * 		- URL에서 경로 변수 값을 가져오기 위한 어노테이션
	 * 		@RequestParam
	 * 		- 요청 파라미터 값을 가져오기 위한 어노테이션
	 * 		@RequestHeader
	 * 		- 요청 헤더 값을 가져오기 위한 어노테이션
	 *  	@RequestBody
	 *  	- 요청 본문 내용을 가져오기 위한 어노테이션
	 *  	@CookieValue
	 *  	- 쿠키 값을 가져오기 위한 어노테이션
	 */
	
	
	// 1) URL 경로 상의 경로 변수가 여러 개일때 @PathVariable 어노테이션을 사용하여 특정한 경로 변수명을 지정해준다.
	@RequestMapping(value="/register/{userId}/{coin}", method = RequestMethod.GET)
	public String registerByPath(@PathVariable("userId") String userId, @PathVariable("coin") int coin) {
		log.info("registerByPath() 실행...!");
		log.info("userId : " + userId);
		log.info("coin : " + coin);
		return "success";
	}
	
	// 2) HTML 폼의 필드명과 컨트롤러 매개변수명이 일치하면 요청을 처리할 수 있다.
	@RequestMapping(value="/register0101", method = RequestMethod.POST)
	public String register0101(String userId) {
		log.info("register0101() 실행...!");
		log.info("userId : " + userId);
		return "success";
	}
	
	// 3) HTML 폼 필드명과 컨트롤러 매개변수명이 일치(대소문자 구분)하지 않으면 요청을 처리할 수 없다
	// 클라이언트에서 선언된 필드명은 userId인데 서버 컨트롤러 메소드에서 받는 필드명이 username이면 파라미터를 받을 수 없다.
	// 정확하게 각 필드명이 일치했을때만 파라미터를 받을 수 있다.
	@RequestMapping(value="/register0201", method = RequestMethod.POST)
	public String register0201(String username) {
		log.info("register0201() 실행...!");
		log.info("username : " + username);
		return "success";
	}
	
	// 4) @RequestParam 어노테이션을 사용하여 특정한 HTML Form의 필드명을 지정하여 요청을 처리한다.
	// 데이터를 받는 필드명은 동일하게 userId로 하되, 사용하는 변수명은 username으로 달리 설정할 수 있다.
	// 우리가 페이징을 구현할 떄 page를 넘기는 방법을 해당 이녀석을 채택해서 구현함.
	@RequestMapping(value="/register0202", method = RequestMethod.POST)
	public String register0202(@RequestParam("userId") String username) {
		log.info("register0202() 실행...!");
		log.info("username : " + username);
		return "success";
	}
	
	/*
	 * 4. 요청 처리 자바빈즈
	 */

	// 1) 폼 텍스트 필드 요소값을 자바빈즈 매개변수로 처리한다.
	@RequestMapping(value="/beans/register01", method = RequestMethod.POST)
	public String registerJavaBeans01(Member member) {
		log.info("registerJavaBeans01() 실행..!");
		log.info("member.userId : " + member.getUserId());
		log.info("member.password : " + member.getPassword());
		log.info("member.coin : " + member.getCoin());
		return "success";
	}
	
	// 2) 폼 텍스트 필드 요소값을 자바빈즈 매개변수와 기본 데이터 타입인 정수 타입 매개변수로 처리한다.
	@RequestMapping(value="/beans/register02", method = RequestMethod.POST)
	public String registerJavaBeans02(Member member, int coin) {
		log.info("registerJavaBeans02() 실행..!");
		log.info("member.userId : " + member.getUserId());
		log.info("member.password : " + member.getPassword());
		log.info("member.coin : " + member.getCoin());
		log.info("coin : " + coin);
		return "success";
	}
	
	// 3) 여러 개의 폼 텍스트 필드 요소값을 매개변수 순서와 상관없이 매개변수명을 기준으로 처리한다.
	@RequestMapping(value="/beans/register03", method = RequestMethod.POST)
	public String registerJavaBeans03(int uid, Member member) {
		log.info("registerJavaBeans03() 실행..!");
		log.info("uid : " + uid);
		log.info("member.userId : " + member.getUserId());
		log.info("member.password : " + member.getPassword());
		log.info("member.coin : " + member.getCoin());
		return "success";
	}
	
	/*
	 * 5. Date 타입 처리
	 * - 스프링 MVC는 Date 타입의 데이터를 처리하는 여러 방법을 제공한다.
	 * - 따로 지정하지 않으면 변환에 적합한 날짜 문자열 형식이 어떤것이 있는지 알아보자.
	 */
	
	// 1)
	@RequestMapping(value="/registerByGet01", method = RequestMethod.GET)
	public String registerByGet01(String userId, @DateTimeFormat(pattern = "yyyyMMdd")  Date dateOfBirth) {
		log.info("registerByGet01() 실행...!");
		log.info("userId : " + userId);
		log.info("dateOfBirth : " + dateOfBirth);
		return "success";
	}
	
	@RequestMapping(value="/registerByGet02", method = RequestMethod.GET)
	public String registerByGet02(Member member) {
		log.info("registerByGet02() 실행...!");
		log.info("member.userId : " + member.getUserId());
		log.info("member.dateOfBirth : " + member.getDateOfBirth());
		return "success";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String register(Member member) {
		log.info("register() 실행...!");
		log.info("member.userId : " + member.getUserId());
		log.info("member.password : " + member.getPassword());
		log.info("member.dateOfBirth : " + member.getDateOfBirth());
		return "success";
	}
	
	/*
	 * 6. @DateTimeFormat 어노테이션
	 * - @DateTimeFormat 어노테이션의 pattern 속성값에 원하는 날짜형식을 지정할 수 있다.
	 * 
	 * > 테스트는 /registerByGet02를 요청하고 파라미터로 받아 줌, Member 클래스의 날짜를 받는 필드의 어노테이션 설정
	 */
	
}
