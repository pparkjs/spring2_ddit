package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	private int userNo;
	private String userId;
	private String userName;
	private String userPw;
	private Date regDate;
	private Date updDate;
	private List<MemberAuth> authList;
	
}
