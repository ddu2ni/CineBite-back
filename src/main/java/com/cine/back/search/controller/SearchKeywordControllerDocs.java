package com.cine.back.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.cine.back.search.dto.SearchKeywordDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SearchKeyword", description = "검색어 저장 관련 API입니다.")
public interface SearchKeywordControllerDocs {

    @Operation(summary = "검색어 저장", description = "검색어을 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색어 저장 성공"),
        @ApiResponse(responseCode = "400", description = "검색어 저장 실패")})
        public ResponseEntity<Void> saveSearchKeyword(@RequestBody SearchKeywordDTO searchKeywordDTO);

}
