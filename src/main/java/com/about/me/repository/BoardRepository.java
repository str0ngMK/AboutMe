package com.about.me.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.about.me.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
	List<BoardEntity> findByDelYn(boolean delYn);

	Optional<BoardEntity> findByNoAndDelYn(long no, boolean delYn);

	boolean existsByNoAndBoardPwd(long no, String pwd);

	Optional<BoardEntity> findByNo(long no);

	@Query("SELECT b FROM board b WHERE b.delYn = false AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword% OR b.tag LIKE %:keyword% OR b.author LIKE %:keyword%)")
	List<BoardEntity> findByKeyword(@Param("keyword") String keyword);
}
