package com.about.me.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.about.me.config.ApplicationConfig;
import com.about.me.dto.BoardDto;
import com.about.me.dto.req.ReqBoardDto;
import com.about.me.entity.BoardEntity;
import com.about.me.repository.BoardRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ApplicationConfig applicationConfig;

	public List<ReqBoardDto> getBoardList() {
		List<BoardEntity> list = boardRepository.findByDelYn(false);
		List<ReqBoardDto> result = new ArrayList<>();
		for (BoardEntity entity : list) {
			ReqBoardDto dto = new ReqBoardDto();
			dto.setNo(entity.toDto().getNo());
			dto.setTitle(entity.toDto().getTitle());
			dto.setPreview(entity.toDto().getPreview());
			dto.setImage(entity.toDto().getImage());
			dto.setAuthor(entity.toDto().getAuthor());
			dto.setInsDate(entity.getInsDate());
			dto.setUpdDate(entity.getUpdDate());
			result.add(dto);
		}
		return result;
	}

	public ReqBoardDto getBoard(long no) {
		Optional<BoardEntity> result = boardRepository.findByNoAndDelYn(no, false);
		ReqBoardDto dto = new ReqBoardDto();
		if (result.isPresent()) {
			BoardEntity entity = result.get();
			dto.setNo(entity.toDto().getNo());
			dto.setTitle(entity.toDto().getTitle());
			dto.setContent(entity.toDto().getContent());
			dto.setImage(entity.toDto().getImage());
			String tag = entity.toDto().getTag();
			dto.setTag(tag == null ? new String[0] : tag.split(","));
			dto.setAuthor(entity.toDto().getAuthor());
			dto.setDelYn(entity.toDto().isDelYn());
			dto.setInsDate(entity.getInsDate());
			dto.setUpdDate(entity.getUpdDate());
		} else {
			return null;
		}
		return dto;
	}

	public boolean isBoard(long no) {
		boolean result = false;
		Optional<BoardEntity> board = boardRepository.findByNo(no);
		if (board.isPresent()) {
			BoardDto dto = board.get().toDto();
			if (!dto.isDelYn()) {
				result = true;
			}
		}
		return result;
	}

	public String uploadImage(MultipartFile image, HttpServletResponse response) {
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

			Cookie cookie = new Cookie(saveFilename, "filename");
			cookie.setHttpOnly(true);
			cookie.setSecure(true);
			cookie.setPath("/");
			cookie.setMaxAge(7 * 24 * 60 * 60);
			response.addCookie(cookie);

			return saveFilename;
		} catch (IOException e) {
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

	public Map<String, Object> saveBoard(ReqBoardDto body, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		BoardDto dto = new BoardDto();
		String content = null;
		List<String> realImage = null;
		try {
			String title = body.getTitle();
			content = body.getContent();
			realImage = getRealImg(content);
			String preview = body.getPreview();
			String image = realImage.size() == 0 ? null : realImage.get(0);
			String author = body.getAuthor();
			String boardPwd = body.getBoardPwd();
			String tag = null;
			for (String tagName : body.getTag()) {
				if (tag == null) {
					tag = tagName;
				} else {
					tag += "," + tagName;
				}
			}

			dto.setTitle(title);
			dto.setContent(content);
			dto.setPreview(preview);
			dto.setImage(image);
			dto.setTag(tag);
			dto.setAuthor(author);
			dto.setBoardPwd(boardPwd);

//			getRealImg(content);

			boardRepository.saveAndFlush(dto.toEntity());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("message", "저장을 실패하였습니다. 잠시 후 다시 시도 해 주세요.");
			return result;
		} finally {
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getValue().equals("filename")) {
						if (realImage.size() != 0) {
							for (String filename : getRealImg(content)) {
								if (!cookie.getName().equals(filename)) {
									deleteImage(cookie.getName());
								}
							}
						} else {
							deleteImage(cookie.getName());
						}
						cookie.setPath("/");
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		result.put("result", true);
		result.put("message", "저장 완료");

		return result;
	}

	private void deleteImage(String filename) {
		String uploadDir = applicationConfig.getImgRepoPath();
		Path path = Paths.get(uploadDir + filename);
		try {
			Files.delete(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<String> getRealImg(String content) {
		List<String> image = new ArrayList<>();

		Pattern pattern = Pattern.compile("/board/image-print\\?filename=([^&)]+)");
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			image.add(matcher.group(1));
		}
//		if (matcher.find()) {
//			image = matcher.group(1);
//		}

		return image;

//		while(matcher.find()) {
//			System.out.println("찾은 패턴: " + matcher.group());
//		}

//        String fileFullPath = Paths.get(uploadDir, filename).toString();
//
//        // 파일이 없는 경우 예외 throw
//        File uploadedFile = new File(fileFullPath);
	}

	public void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getValue().equals("filename")) {
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	public boolean checkBoardPwd(HttpSession session, long no, String pwd) {
		boolean result = boardRepository.existsByNoAndBoardPwd(no, pwd);
		if (result) {
			session.setAttribute("board", no);
			session.setAttribute("result", true);
		}
		return result;
	}

	public boolean deleteBoard(long no) {
		Optional<BoardEntity> result = boardRepository.findByNo(no);
		if (result.isPresent()) {
			try {
				BoardDto dto = result.get().toDto();
				dto.setDelYn(true);
				boardRepository.save(dto.toEntity());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

//	public ReqBoardDto modifyBoard(HttpSession session) {
//		Optional<BoardEntity> result = boardRepository.findByNo((long)session.getAttribute("board"));
//		ReqBoardDto dto = new ReqBoardDto();
//		if(result.isPresent()) {
//			BoardEntity entity = result.get();
//			dto.setTitle(entity.toDto().getTitle());
//			dto.setContent(entity.toDto().getContent());
//		}
//		return dto;
//	}

	public boolean updateBoard(HttpSession session, ReqBoardDto body, HttpServletRequest request, HttpServletResponse response) {
		boolean result = false;
		String content = null;
		List<String> realImage = null;
		try {
			Optional<BoardEntity> board = boardRepository.findByNo((long) session.getAttribute("board"));
			if (board.isPresent()) {
				content = body.getContent();
				realImage = getRealImg(content);
				String image = realImage.size() == 0 ? null : realImage.get(0);
				
				BoardDto dto = board.get().toDto();
				dto.setTitle(body.getTitle());
				dto.setContent(content);
				dto.setPreview(body.getPreview());
				dto.setImage(image);

				boardRepository.saveAndFlush(dto.toEntity());
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getValue().equals("filename")) {
						if (realImage.size() != 0) {
							for (String filename : getRealImg(content)) {
								if (!cookie.getName().equals(filename)) {
									deleteImage(cookie.getName());
								}
							}
						} else {
							deleteImage(cookie.getName());
						}
						cookie.setPath("/");
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		return result;
	}
	
	public List<ReqBoardDto> searchBoard(String keyword) {
		List<ReqBoardDto> result = new ArrayList<>();
		keyword = removeSpaces(keyword);
		List<BoardEntity> searchResult = boardRepository.findByKeyword(keyword);
		if(searchResult.size() > 0) {
			for (BoardEntity entity : searchResult) {
				ReqBoardDto dto = new ReqBoardDto();
				dto.setNo(entity.toDto().getNo());
				dto.setTitle(entity.toDto().getTitle());
				dto.setContent(entity.toDto().getContent());
				dto.setAuthor(entity.toDto().getAuthor());
				dto.setInsDate(entity.getInsDate());
				dto.setUpdDate(entity.getUpdDate());
				result.add(dto);
			}
		}
		return result;
	}
	
	private String removeSpaces(String keyword) {
		String result;
		int start = 0;
        int end = keyword.length() - 1;

        while (start < keyword.length() && keyword.charAt(start) == ' ') {
            start++;
        }

        while (end >= 0 && keyword.charAt(end) == ' ') {
            end--;
        }

        result = keyword.substring(start, end + 1);
        result = "%" + result + "%";
        result = result.replaceAll("\\s+", "%");
        
        return result;
	}

}
