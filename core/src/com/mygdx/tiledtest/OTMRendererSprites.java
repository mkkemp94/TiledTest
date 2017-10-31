package com.mygdx.tiledtest;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

public class OTMRendererSprites extends OrthogonalTiledMapRenderer{
	private Sprite sprite;
	private List<Sprite> spriteList;
	private int drawSpritesAfterLayer = 1;

	public OTMRendererSprites(TiledMap map) {
		super(map);
		spriteList = new ArrayList<Sprite>();
	}

	public void addSprite(Sprite sprite) {
		spriteList.add(sprite);
	}

	@Override
	public void render() {
		beginRender();
		int currentLayer = 0;
		for (MapLayer layer : map.getLayers()) {
			if (layer.isVisible()) {
				if (layer instanceof TiledMapTileLayer) {
					renderTileLayer((TiledMapTileLayer) layer);
					currentLayer++;
					if (currentLayer == drawSpritesAfterLayer) {
						for (Sprite sprite : spriteList) {
							sprite.draw(this.getBatch());
						}
					}
					else {
						for (MapObject object : layer.getObjects()) {
							renderObject(object);
						}
					}
				}
			}
		}
		endRender();
	}
}
