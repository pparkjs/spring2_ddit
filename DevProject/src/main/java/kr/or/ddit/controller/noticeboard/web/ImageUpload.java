package kr.or.ddit.controller.noticeboard.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class ImageUpload {
	
	@RequestMapping(value="/imageUpload.do")
	public String imageUpload(
			HttpServletRequest req, HttpServletResponse resp,
			MultipartHttpServletRequest multiFile
			) throws IOException {
		JsonObject json = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		long limitSize = 1024 * 1024 * 2; 	// 2MB
		MultipartFile file = multiFile.getFile("upload");
		
		if(file != null && file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
			if(file.getContentType().toLowerCase().startsWith("image/")) { // contentType 소문자로바꾸고 앞에 시작하는게 image/이면
				if(file.getSize() > limitSize) {
					/*
					 * json = {
					 * 		"error" : jsonarr	,
					 * 		"uploaded" : 0
					 * }
					 * 
					 * jsonArr = {"message": "2MB미만의 이미지만 업로드 가능합니다!"}
					 * 
					 * addProperty는 key value 넣는 메소드
					 * add 는 객체 넣는 것 
					 */
					JsonObject jsonMsg = new JsonObject();
					JsonArray jsonArr = new JsonArray();
					jsonMsg.addProperty("message", "2MB미만의 이미지만 업로드 가능합니다!");
					jsonArr.add(jsonMsg);
					json.addProperty("uploaded", 0);
					json.add("error", jsonArr.get(0));
					
					resp.setCharacterEncoding("UTR-8");
					printWriter = resp.getWriter();
					printWriter.println(json);
				}else {
					try {
						String fileName = file.getName();
						byte[] bytes = file.getBytes();
						String uploadPath = req.getServletContext().getRealPath("/resources/img"); // 업로드 경로 (서버)
						
						File uploadFile = new File(uploadPath);
						if(!uploadFile.exists()) {
							uploadFile.mkdirs(); // 존재하지 않는 경우 폴더 구조를 만들어준다.
						}
						
						fileName = UUID.randomUUID().toString(); // UUID를 활용하여 랜덤으로 발생되어 만들어진 문자열값을 저장한다.
						uploadPath = uploadPath + "/" + fileName + ".jpg"; // /resources/img/UUID.jpg
						
						out = new FileOutputStream(new File(uploadPath)); //파일 복사함
						out.write(bytes);
						
						printWriter = resp.getWriter();
						resp.setContentType("text/html");
						String fileUrl = req.getContextPath() + "/resources/img/" + fileName + ".jpg";
						
						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url", fileUrl); // 미리보기 url 뿌려준 거 
						
						printWriter.println(json);
					} catch (IOException e) {
						e.printStackTrace();
					}finally {
						if(out != null) {
							out.close();
						}
						if(printWriter != null) {
							printWriter.close();
						}
					}
				}
			}
		}
		return null;
		
	}
}
