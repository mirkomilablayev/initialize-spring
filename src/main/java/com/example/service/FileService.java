package com.example.service;


import com.example.dto.CommonResponse;
import com.example.entity.File;
import com.example.exceptions.GenericException;
import com.example.repository.FileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value(value = "${app.save-file-path}")
    private String FILE_SAVE_PATH;

    private final FileRepository fileRepository;

    public ResponseEntity<CommonResponse> upload(MultipartFile file) {
        Long aLong = saveAttachment(file);
        if (aLong == -1) throw new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!");
        return ResponseEntity.ok(new CommonResponse(aLong));
    }


    public ResponseEntity<byte[]> download(Long fileId, HttpServletRequest request, HttpServletResponse response) {
        File file1 = fileRepository.findById(fileId)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!"));

        try {
            java.io.File file = new java.io.File(file1.getPath());
            if (!file.exists()) {
                throw new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!");
            }
            Path filePath = Paths.get(file.getPath());
            byte[] fileContent = Files.readAllBytes(filePath);
            String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            String originalFileName = file1.getOriginalName();
            if (originalFileName != null) {
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                contentType = MediaType.valueOf(URLConnection.guessContentTypeFromName(fileExtension)).toString();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData("attachment", originalFileName);
            headers.setContentLength(fileContent.length);

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException exception) {
            throw new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!");
        }
    }


    public ResponseEntity<Resource> videoStream(Long videoId) {
        File file = fileRepository.findById(videoId).orElseThrow(() ->  new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!"));
        java.io.File videoFile = new java.io.File(file.getPath());
        if (videoFile.exists() && videoFile.isFile()) {
            Resource resource = new FileSystemResource(videoFile);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(videoFile.length());
            headers.setContentDispositionFormData("attachment", file.getGeneratedName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            throw new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!");
        }
    }

    public ResponseEntity<Resource> videoStreamV2(Long videoId) {
        File file = fileRepository.findById(videoId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!"));
        java.io.File videoFile = new java.io.File(file.getPath());

        if (videoFile.exists() && videoFile.isFile()) {
            Resource resource = new FileSystemResource(videoFile);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("video/mp4"));
            headers.setContentDisposition(ContentDisposition.builder("inline")
                    .filename(file.getGeneratedName())
                    .build());
            headers.setCacheControl("no-store, no-cache, must-revalidate, max-age=0");
            headers.setContentLength(videoFile.length());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            throw new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!");
        }
    }


    public ResponseEntity<InputStreamResource> showPicture(Long fileId) {
        try {
            File file1 = fileRepository.findById(fileId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!"));
            java.io.File file = new java.io.File(file1.getPath());
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-disposition", "inline;filename=" + file1.getOriginalName());
            InputStreamResource resource = null;
            resource = new InputStreamResource(new FileInputStream(file));
            long contentLength = file.length();
            MediaType mediaType = MediaType.parseMediaType(file1.getContentType());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(contentLength)
                    .contentType(mediaType)
                    .body(resource);
        } catch (Exception e) {
            throw new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!");
        }
    }


    public Long saveAttachment(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            File file1 = new File();
            file1.setSize(file.getSize());
            file1.setContentType(file.getContentType());
            file1.setOriginalName(originalFilename);

            String generatedName = System.currentTimeMillis() + "-" + originalFilename;
            file1.setGeneratedName(generatedName);
            String filePath = filePathBuilder(file) + java.io.File.separator + generatedName;
            file1.setPath(filePath);
            file.transferTo(new java.io.File(filePath));
            File savedFile = fileRepository.save(file1);
            return savedFile.getId();
        } catch (Exception e) {
            return -1L;
        }
    }

    private String filePathBuilder(MultipartFile file) {

        FILE_SAVE_PATH = FILE_SAVE_PATH + java.io.File.separator + (isImage(file) ? "image" : isVideo(file) ? "video" : "other");
        Path directory = Paths.get(FILE_SAVE_PATH);

        try {
            if (Files.notExists(directory)) {
                Files.createDirectories(directory);
                log.info("Directory created: " + directory);
            } else {
                log.info("Directory already exists: " + directory);
            }
            return FILE_SAVE_PATH;
        } catch (IOException ex) {
            log.error("Failed to create directory: " + directory, ex);
            throw new GenericException(ex.getMessage());
        }
    }


    public void deleteFile(Long fileId) {
        try {
            com.example.entity.File file = fileRepository.findById(fileId).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "File topilmadi!"));
            Files.deleteIfExists(Path.of(file.getPath()));
            fileRepository.deleteById(fileId);
        } catch (Exception e) {
            log.info("Failed to delete file in ID={}", fileId);
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file in ID=" + fileId);
        }
    }


    public boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public boolean isVideo(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("video/");
    }

    public boolean isOtherTypeOfFile(MultipartFile file) {
        return !(isImage(file) || isVideo(file));
    }
}
