package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.RelatedEntity;
import com.cine.back.search.service.RelatedService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/related/search")
@RequiredArgsConstructor
public class RelatedController implements RelatedControllerDocs {

    private final RelatedService relatedService;

    @PostMapping("/save")
    public ResponseEntity<List<RelatedEntity>> saveRelatedKeyword(@RequestBody SearchRequest searchRequest) {
        log.info("Received keywords: {}", searchRequest.keywords());
        log.info("연관검색어 저장 컨트롤러 실행");

        try {
            List<RelatedEntity> savedRelatedKeywords = relatedService.saveRelatedKeyword(searchRequest);
            log.info("연관검색어 저장 컨트롤러 실행 완료");
            return ResponseEntity.ok(savedRelatedKeywords);
        } catch (RuntimeException e) {
            log.error("연관검색어 저장 컨트롤러 실행 중 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
