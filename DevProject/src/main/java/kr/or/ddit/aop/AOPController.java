package kr.or.ddit.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// @Component는 스프링 빈으로 등록하기 위한 어노테이션
// @Aspect는 어노테이션을 붙여 이 클래스가 Aspect를 나타내는 클래스라는걸 명시
// AOPController 클래스 빈 등록 시, aOPController로 할지 aoPController로 할지가 명확하지 않을 수 있어서 aopController라는 이름을 명시해줌
@Component("aopController")
@Aspect	
@Slf4j
public class AOPController {
	
	@Before("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("[@Before] startLog");
		// getSignature() : 어떤 클래스의 어떤 메소드가 실행되었는지를 보여줍니다.
		//					파라미터 타입은 무엇인지 보여줍니다.
		log.info("[@Before] startLog : " + jp.getSignature());
		// getArgs() : 전달 된 파라미터 정보를 보여준다.
		//			예 ) [BoardVO [boardNo=127, title=개똥이]]
		log.info("[@Before] startLog : " + Arrays.toString(jp.getArgs()));
		
		// 8. 메서드 정보 획득
		// 프록시가 입혀지기 전의 원본 대상 객체를 가져온다.
		Object targetObject = jp.getTarget();
		log.info("targetObject : " + targetObject);
		
		// 프록시를 가져온다.
		Object thisObject = jp.getThis();
		log.info("thisObject : " + thisObject);
		
		// 인수를 가져온다.
		Object[] args = jp.getArgs();
		log.info("args.length :  " + args.length);
	}
	
	/*
	 * 4. After Returning 어드바이스
	 * - 조인 포인트가 정상적으로 종료한 후에 실행된다. 예외가 발생하면 실행되지 않는다. 
	 */
	@AfterReturning("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void logReturning(JoinPoint jp) {
		log.info("[@AfterReturning] logReturning");
		log.info("[@AfterReturning] logReturning : " + jp.getSignature());
	}
	
	/*
	 * 5. AfterThrowing 어드바이스
	 * - 조인 포인트에서 예외가 발생했을 때 실행된다. 예외가 발생하지 않고 정상적으로 종료되면 실행되지 않는다.
	 * ** 예시) crud board에서 delete부를 에러로 실행해봅시다. (쿼리를 no = 2, no 2 = )으로 변경해서 진행
	 */
	@AfterThrowing(pointcut = "execution(* kr.or.ddit.service.IBoardService.*(..))", throwing = "e")
	public void logExceptio(JoinPoint jp, Exception e) {
		log.info("[@AfterThrowing] logExceptio");
		log.info("[@AfterThrowing] logExceptio : " + jp.getSignature());
		log.info("[@AfterThrowing] logExceptio : " + e);
	}
	
	/*
	 * 6. After 어드바이스
	 * - 조인 포인트에서 처리가 완료된 후 실행된다.
	 */

	@After("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("[@After] endLog");
		log.info("[@After] endLog : " + jp.getSignature());
		log.info("[@After] endLog : " + Arrays.toString(jp.getArgs()));
	}
	
	/*
	 * 7. Around 어드바이스
	 * - 조인 포인트 전 후에 실행된다.
	 * 
	 * ProceedingJoinPoint : around 어드바이스에서 사용합니다.
	 * 
	 * 스프링 프레임워크가 컨트롤 하고 있는 비즈니스 로직 호출을 가로챈다.
	 * 책임이 around 어드바이스로 전가되고 그래서 비즈니스 로직에 대한 정보를 around 어드바이스 메소드가 가지고 있어야하고
	 * 그 정보를 스프링 컨테이너가 around 어드바이스 메소드로 넘겨줌녀 ProceedingJoinPoint 객체로 받아서 around 어드바이스가 컨트롤 시 활용함
	 */
	
	@Around("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		
		// 메소드 실행 직전 시간 채킹
		long startTime = System.currentTimeMillis();
		log.info("[@Around] : " + Arrays.toString(pjp.getArgs()));
		
		// 메소드 실행
		Object result = pjp.proceed();
		
		// 메소드 실행 직후 시간 체킹
		long endTime = System.currentTimeMillis();
		log.info("[@Around]pjpEnd : " + Arrays.toString(pjp.getArgs()));
		
		log.info("[@Around] : " + pjp.getSignature().getName() + "(메소드 실행 시간 : ) " + (endTime - startTime));
		return result;
	}
	
	/*
	 * 8. 메소드 정보 획득
	 * - @Before 어노테이션 붙은 메소드는 joinPoint라는 매개변수를 통해 실행 중인 메서드의 정보를 구할 수 있다.
	 * > @Before 어드바이스에서 테스트 진행합니다.
	 * 
	 */
}
