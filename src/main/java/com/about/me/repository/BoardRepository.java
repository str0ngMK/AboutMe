package com.about.me.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.about.me.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
	List<BoardEntity> findByDelYn(boolean delYn);

	Optional<BoardEntity> findByNo(long no);
}
