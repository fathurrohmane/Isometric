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
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Isometric isometric;

	private Vector2 pos;
	
	@Override
	public void create() {		
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		isometric = new Isometric(Gdx.files.internal("tiles_2/tiles.pack"), Gdx.files.internal("tiles_2/map.txt"), 6, 5, 15);
		isometric.addStaticObject(Gdx.files.internal("objects/trees.pack"), Gdx.files.internal("objects/map.txt"));
		isometric.addDynamicObject(Gdx.files.internal("car/car.pack"), new Vector2(3,3));
		
		pos = new Vector2(0,0);
		
		camera.position.x = isometric.getPosition().x + isometric.getWidth() / 2;
		camera.position.y = isometric.getPosition().y + isometric.getHeight() / 2;

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
		
		Vector2 posMouse = isometric.getObjectCoordinate(Gdx.input.getX(), Gdx.input.getY());
		//System.out.println(Gdx.input.getX()+","+Gdx.input.getY()+"->"+posMouse.x+","+posMouse.y);
		
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
