package com.about.me.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.about.me.dto.BoardDto;
import com.about.me.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/list/get")
	public ResponseEntity<List<BoardDto>> getBoardList() {
		System.out.println("/board/list");
		return ResponseEntity.ok().body(boardService.getBoardList());
	}

	@GetMapping("/get")
	public ResponseEntity<BoardDto> getBoard(@RequestParam(name = "no") long no){
		BoardDto result = boardService.getBoard(no);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok().body(result);
	}
	
	private final String uploadDir = Paths.get("C:", "Users", "diquest", "Desktop", "img").toString();
	
	@PostMapping("/image-upload")
	public ResponseEntity<Map<String, Object>> uploadEditorImage(@RequestParam(name = "image") MultipartFile image) {
		Map<String, Object> result = new HashMap<>();
		if (image.isEmpty()) {
			result.put("result", "");
			return ResponseEntity.ok().body(result);
		}
		String orgFilename = image.getOriginalFilename();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);
		String saveFilename = uuid + "." + extension;
		String fileFullPath = Paths.get(uploadDir, saveFilename).toString();
		
		File dir = new File(uploadDir);
		if (dir.exists() == false) {
			dir.mkdirs();
		}
		
		try {
			File uploadFile = new File(fileFullPath);
			image.transferTo(uploadFile);
			result.put("result", saveFilename);
			return ResponseEntity.ok().body(result);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@GetMapping("/image-print")
    public ResponseEntity<byte[]> printEditorImage(@RequestParam(name = "filename") String filename) {
        // 업로드된 파일의 전체 경로
        String fileFullPath = Paths.get(uploadDir, filename).toString();

        // 파일이 없는 경우 예외 throw
        File uploadedFile = new File(fileFullPath);
        if (uploadedFile.exists() == false) {
            throw new RuntimeException();
        }

        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());
            return ResponseEntity.ok().body(imageBytes);

        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
    }
}
