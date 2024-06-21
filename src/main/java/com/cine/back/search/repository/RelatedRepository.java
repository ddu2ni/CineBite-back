package com.cine.back.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.search.entity.RelatedEntity;
import com.cine.back.search.entity.SearchEntity;

@Repository
public interface RelatedRepository extends JpaRepository<RelatedEntity, Integer> {

    // 주어진 SearchEntity와 연관된 Secondary Keyword로 조회
    RelatedEntity findBySearchEntityAndSearchRelatedWord(SearchEntity searchEntity, String secondaryKeyword);

    // //특정 검색어에 대한 모든 연관 검색어 리스트 조회
    List<RelatedEntity> findBySearchEntity(SearchEntity searchEntity);

}
