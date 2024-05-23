package com.about.me.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.about.me.config.ApplicationConfig;
import com.about.me.dto.BoardDto;
import com.about.me.entity.BoardEntity;
import com.about.me.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	public List<BoardDto> getBoardList() {
		List<BoardEntity> list = boardRepository.findByDelYn(false);
		List<BoardDto> result = new ArrayList<>();
		for (BoardEntity entity : list) {
			BoardDto dto = new BoardDto();
			dto.setNo(entity.getNo());
			dto.setTitle(entity.getTitle());
			dto.setContent(entity.getContent());
			dto.setImage(entity.getImage().split(","));
			dto.setId(entity.getId());
			dto.setInsDate(entity.getInsDate());
			dto.setUpdDate(entity.getUpdDate());
			result.add(dto);
		}
		return result;
	}
	
	
	public BoardDto getBoard(long no) {
		Optional<BoardEntity> result = boardRepository.findByNo(no);
		BoardDto dto = new BoardDto();
		if (result.isPresent()) {
			BoardEntity entity = result.get();
			dto.setNo(entity.getNo());
			dto.setTitle(entity.getTitle());
			dto.setContent(entity.getContent());
			dto.setImage(entity.getImage().split(","));
			dto.setId(entity.getId());
			dto.setInsDate(entity.getInsDate());
			dto.setUpdDate(entity.getUpdDate());
		} else {
			return null;
		}
		return dto;
	}
	
	public String uploadImage(MultipartFile image){
		String uploadDir = applicationConfig.getImgRepoPath();
		
		if (image.isEmpty()) {
			return "";
		}
		
		String orgFilename = image.getOriginalFilename();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);
		String saveFilename = uuid + "." + extension;
		String fileFullPath = Paths.get(uploadDir, saveFilename).toString();
		
		File dir = new File(uploadDir);
		if (dir.exists() == false) {
			throw new RuntimeException();
		}
		
		try {
			File uploadFile = new File(fileFullPath);
			image.transferTo(uploadFile);
			return saveFilename;
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public byte[] printImage(String filename) {
		String uploadDir = applicationConfig.getImgRepoPath();
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
            return imageBytes;

        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
	}
	
	public void saveBoard(Map<String, Object> body) {
		String title = String.valueOf(body.get("title"));
		String content = String.valueOf(body.get("content"));
		
		
		
		
	}

}
