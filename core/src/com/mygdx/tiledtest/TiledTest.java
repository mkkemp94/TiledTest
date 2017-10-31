package com.mygdx.tiledtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;

public class TiledTest extends ApplicationAdapter implements InputProcessor {
	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	SpriteBatch sb;
	Texture texture;
	Sprite sprite;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		tiledMap = new TmxMapLoader().load("MyMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		sb = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("pikachu.png"));
		sprite = new Sprite(texture);

		// This is for DEMONSTRATIONAL PURPOSES ONLY
		// One better way to deal with sprites and layers would be to add a sprite layer in tiled...

		// Get width and height of map, then halve it because our sprite is 64x64
		int mapWidth = tiledMap.getProperties().get("width", Integer.class) / 2;
		int mapHeight = tiledMap.getProperties().get("height", Integer.class) / 2;

		// Create a new map layer
		TiledMapTileLayer tileLayer = new TiledMapTileLayer(mapWidth, mapHeight, 64, 64);

		// Create a cell (tile) too add to the layer.
		TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

		// There is a single sprite in our layer, and it is 64x64
		TextureRegion textureRegion = new TextureRegion(texture, 64, 64);

		// Set the graphic for our cell to our new region
		cell.setTile(new StaticTiledMapTile(textureRegion));

		// Set the cell at position 2, 4 (which is 4, 8 in map coordinates), behind a tree
		tileLayer.setCell(2, 4, cell);

		// HACK
		// Get top layer and store
		MapLayer tempLayer = tiledMap.getLayers().get(tiledMap.getLayers().getCount() - 1);
		// Remove it
		tiledMap.getLayers().remove(tiledMap.getLayers().getCount() - 1);
		// Add new layer
		tiledMap.getLayers().add(tileLayer);
		// Add back. Now our new layer is not on top.
		tiledMap.getLayers().add(tempLayer);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sprite.draw(sb);
		sb.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.LEFT)
			camera.translate(-32, 0);
		if (keycode == Input.Keys.RIGHT)
			camera.translate(32, 0);
		if (keycode == Input.Keys.UP)
			camera.translate(0, -32);
		if (keycode == Input.Keys.DOWN)
			camera.translate(0, 32);
		if (keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if (keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
		Vector3 position = camera.unproject(clickCoordinates);
		sprite.setPosition(position.x, position.y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
