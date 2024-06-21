package com.cine.back.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.search.entity.SearchEntity;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, Integer> {

    // 사용자ID로 검색 (날짜 내림차순)
    List<SearchEntity> findByUserIdOrderBySearchListTimeDesc(String userId);

    // 주어진 검색어로 검색 엔터티를 찾습니다.
    List<SearchEntity> findBySearchKeyword(String searchKeyword);

    // 유저찾기
    List<SearchEntity> findByUserId(String userId);

}
