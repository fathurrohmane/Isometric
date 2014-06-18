package com.gdx.isometric;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Isometric isometric;
	
	@Override
	public void create() {		
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = Gdx.graphics.getWidth()/2;
		camera.position.y = Gdx.graphics.getHeight()/2;
		batch = new SpriteBatch();
		
		isometric = new Isometric(Gdx.files.internal("tiles_2/tiles.pack"), Gdx.files.internal("tiles_2/map.txt"), 6, 5);
		isometric.addObject(Gdx.files.internal("objects/trees.pack"), Gdx.files.internal("objects/map.txt"));

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyPressed(Keys.S)){
			camera.position.y++;
		}else if(Gdx.input.isKeyPressed(Keys.W)){
			camera.position.y--;
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			camera.position.x--;
		}else if(Gdx.input.isKeyPressed(Keys.A)){
			camera.position.x++;
		}
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		isometric.draw(batch);
		batch.end();
		

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
