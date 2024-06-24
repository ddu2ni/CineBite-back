package com.cine.back.movieList.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.service.MovieListService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/movie")
public class MovieListController {

    private final MovieListService movieListService;

    public MovieListController(MovieListService movieListService) {
        this.movieListService = movieListService;
    }

    // 흥행 높은순 정렬
    @GetMapping("/movieList")
    public ResponseEntity<Optional<List<MovieDetailEntity>>> getMoviePopularity() {
        log.info("전체 영화 조회 컨트롤러");
        Optional<List<MovieDetailEntity>> allMovieList = movieListService.getAllMovieList();
        return ResponseEntity.ok().body(allMovieList);
    }

    // 장르별 정렬
    @PostMapping("/genresList")
    public ResponseEntity<Optional<List<MovieDetailEntity>>> getMovieGenres(@RequestBody String genre) {
        log.info("장르별 조회 컨트롤러");
        Optional<List<MovieDetailEntity>> genresList = movieListService.getMovieGernes(genre);
        return ResponseEntity.ok().body(genresList);
    }

    // 배우별 정렬
    @PostMapping("/actorList")
    public ResponseEntity<Optional<List<MovieDetailEntity>>> getMovieActors(@RequestBody String actor) {
        log.info("배우별 조회 컨트롤러");
        Optional<List<MovieDetailEntity>> actorsList = movieListService.getMovieActors(actor);
        return ResponseEntity.ok().body(actorsList);
    }

    // 한 개 영화정보 꺼내기
    @GetMapping("/{movieId}")
    public ResponseEntity<Optional<MovieDetailEntity>> getMovieDetail(@PathVariable int movieId) {
        log.info("영화 상세 조회 컨트롤러");
        Optional<MovieDetailEntity> movieDetail = movieListService.getMovieDetail(movieId);
        return ResponseEntity.ok().body(movieDetail);
    }

    // 영화명, 배우, 장르로 검색
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<MovieDetailEntity>> searchByKeyword(@PathVariable(value = "keyword") String keyword) {

        log.info("영화 검색 컨트롤러 실행 - 키워드: {}", keyword);
        try {
            List<MovieDetailEntity> searchResults = movieListService.searchByKeyword(keyword);

            if (searchResults.isEmpty()) {
                log.warn("영화 검색 컨트롤러 - 검색 결과 없음: {}", keyword);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(searchResults);
            }

            log.info("영화 검색 컨트롤러 - 검색 결과 반환: {}", searchResults);
            return ResponseEntity.ok(searchResults);

        } catch (Exception e) {
            log.error("영화 검색 컨트롤러 - 검색 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 같은 장르의 영화 추천
    @GetMapping("/recommend")
    public ResponseEntity<List<MovieDetailEntity>> recommendSimilarGenreMovies(@RequestParam String genre) {
        log.info("영화 추천 컨트롤러 컨트롤러 실행 - 장르: {}", genre);
        try {
            List<MovieDetailEntity> recommendedMovies = movieListService.recommendSimilarGenre(genre);

            if (recommendedMovies.isEmpty()) {
                log.warn("영화 추천 컨트롤러 - 추천할 영화 없음: {}", genre);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(recommendedMovies);
            }

            log.info("영화 추천 컨트롤러 - 추천 영화 반환: {}", recommendedMovies);
            return ResponseEntity.ok(recommendedMovies);

        } catch (Exception e) {
            log.error("영화 추천 컨트롤러 - 추천 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
