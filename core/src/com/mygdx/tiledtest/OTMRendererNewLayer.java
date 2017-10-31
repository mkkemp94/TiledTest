package com.mygdx.tiledtest;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class OTMRendererNewLayer extends OrthogonalTiledMapRenderer{

	public OTMRendererNewLayer(TiledMap map) {
		super(map);
	}

	@Override
	public void renderObject(MapObject object) {

		// Every time it renders it draws every texture region.
		if (object instanceof TextureMapObject) {
			TextureMapObject textureMapObject = (TextureMapObject) object;
			this.getBatch().draw(textureMapObject.getTextureRegion(),
					textureMapObject.getX(), textureMapObject.getY()
			);
		}
	}
}
