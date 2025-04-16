package com.savchenko.testproject.controllers;

import com.savchenko.testproject.models.TileManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TileGetterController {
    private TileManager tileManager;

    public TileGetterController(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    @GetMapping(value = "/get_tile", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTile(@RequestParam int zoom, @RequestParam int xCord, @RequestParam int yCord) {
        return ResponseEntity.ok(tileManager.getTile(zoom, xCord, yCord));
    }


}
