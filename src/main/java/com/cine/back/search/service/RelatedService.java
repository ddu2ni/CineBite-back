package com.cine.back.search.service;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.RelatedEntity;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.repository.RelatedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatedService {

    private final RelatedRepository relatedRepository;
    private final SearchService searchService;

    @Transactional
    public List<RelatedEntity> saveRelatedKeyword(SearchRequest searchRequest) {
        List<String> keywords = searchRequest.keywords();

        String primaryKeyword = keywords.get(0);
        String secondaryKeyword = keywords.get(1);

        try {
            List<SearchEntity> searchEntities = searchService.findSearchEntityByKeyword(primaryKeyword);

            // 첫 번째 검색어가 존재하지 않으면 연관 검색어를 저장하지 않음
            if (searchEntities.isEmpty()) {
                log.warn("첫 번째 검색어가 존재하지 않아 연관 검색어를 저장하지 않습니다.");
                return Collections.emptyList();
            }

            for (SearchEntity searchEntity : searchEntities) {
                RelatedEntity relatedEntity = relatedRepository.findBySearchEntityAndSearchRelatedWord(searchEntity,
                        secondaryKeyword);

                if (relatedEntity == null) {
                    relatedEntity = new RelatedEntity();
                    relatedEntity.setSearchEntity(searchEntity);
                    relatedEntity.setSearchRelatedWord(secondaryKeyword);
                    relatedEntity.setSearchRelatedCount(1);
                } else {
                    relatedEntity.setSearchRelatedCount(relatedEntity.getSearchRelatedCount() + 1);
                }

                relatedRepository.save(relatedEntity);
                log.info("연관검색어 저장 서비스 - 키워드 '{}'", secondaryKeyword);
            }

            // 저장된 연관 검색어 리스트 반환
            return relatedRepository.findBySearchEntity(searchEntities.get(0));

        } catch (Exception e) {
            log.error("연관검색어 저장 서비스 중 예외 발생", e);
            throw new RuntimeException("연관검색어 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @Transactional
    public List<RelatedEntity> findRelatedKeywords(SearchEntity searchEntity) {
        return relatedRepository.findBySearchEntity(searchEntity);
    }
}
