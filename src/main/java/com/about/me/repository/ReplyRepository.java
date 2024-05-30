package com.about.me.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.about.me.entity.ReplyEntity;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long>{

	List<ReplyEntity> findByBoardNoAndDelYn(long no, boolean delYn);

}
