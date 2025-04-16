package com.savchenko.testproject.controllers;

import com.savchenko.testproject.models.TileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class NewTileMapLoaderController {
    private final TileManager tileManager;

    public NewTileMapLoaderController(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleUpload(@RequestParam("file") MultipartFile file) {
        try {
            Path uploadDir = Paths.get("");
            Files.createDirectories(uploadDir);
            Path path = uploadDir.resolve("map.mbtiles");
            Files.write(path, file.getBytes());
            tileManager.updateTiles();
            return ResponseEntity.ok("Файл сохранён: " + path.toAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при сохранении");
        }
    }
}
