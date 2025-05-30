package com.example.instagram_server.video.controller;

import com.example.instagram_server.video.domain.Video;
import com.example.instagram_server.video.dto.VideoListResDto;
import com.example.instagram_server.video.dto.VideoResDto;
import com.example.instagram_server.video.dto.VideoSaveReqDto;
import com.example.instagram_server.video.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/")
    public ResponseEntity<?> videoAdd(@RequestBody VideoSaveReqDto videoSaveReqDto) {
        Video video = videoService.add(videoSaveReqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Page<VideoResDto>> listVideos(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Video> videoPage = videoService.getVideos(PageRequest.of(page, size));
        Page<VideoResDto> dtoPage = videoPage.map(VideoResDto::new);
        return ResponseEntity.ok(dtoPage);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable("id") Long id) {
        VideoResDto video = videoService.findById(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> videoUpdate(@RequestBody VideoSaveReqDto videoSaveReqDto, @PathVariable("id") Long id) {
        videoService.update(videoSaveReqDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> videoDelete(@PathVariable("id") Long id) {
        videoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VideoResDto>> searchVideos(@RequestParam(name = "title", required = false) String title,
                                                          @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Video> videoPage = videoService.searchByTitle(title, pageable);
        Page<VideoResDto> dtoPage = videoPage.map(VideoResDto::new);
        return ResponseEntity.ok(dtoPage);
    }

}
