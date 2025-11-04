package fitpass.fitpass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api/images")
public class ImageController {
    private final ResourceLoader resLoader;

    @Autowired
    public ImageController(ResourceLoader resLoader) {
        this.resLoader = resLoader;
    }

    private String path;

    @PostMapping(value = "/upload")
    public ResponseEntity<Map<String, String>> handleFileUpload(@RequestParam("images") MultipartFile[] files) {
        Map<String, String> response = new HashMap<>();
        try {
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path path1 = Paths.get(path, fileName);
                if (!Files.exists(path1)) {
                    Files.write(path1, file.getBytes());
                }
            }
            response.put("message", "Files uploaded successfully.");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Error in the process of uploading files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(path, filename);
            byte[] imageBytes = Files.readAllBytes(imagePath);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imagePath));
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
