package com.savchenko.testproject.models;


import org.imintel.mbtiles4j.MBTilesReader;
import org.imintel.mbtiles4j.Tile;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;


@Component
public class TileManager {
    private String tileName = "map.mbtiles";
    private MBTilesReader tilesReader;

    TileManager() {
        try {
            tilesReader = new MBTilesReader(new File(tileName));
        } catch (Exception ignored) {
        }
    }

    public void updateTiles() {
        try {
            tilesReader.close();
            tilesReader = new MBTilesReader(new File(tileName));
        } catch (Exception ignored) {
        }
    }

    public byte[] getTile(int zoom, int x, int y) {
        try {
            int flippedY = (1 << zoom) - 1 - y; //инверсия, как оказалось необходима для адекватной работы тех файлов которые я находил...
            Tile tile = tilesReader.getTile(zoom, x, flippedY);
            BufferedImage image = ImageIO.read(tile.getData());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
