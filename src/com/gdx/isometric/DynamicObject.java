package com.gdx.isometric;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DynamicObject extends IsometricObject{

	private Tile placedTile;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private TextureRegion[][] textureRegion;
	
	public DynamicObject(FileHandle file) {
		textureAtlas = new TextureAtlas(file);

//		animation = new Animation(1/15f, textureAtlas.getRegions());
//		textureRegion = new TextureRegion[4][1];
//		textureRegion[0][0] = textureAtlas.findRegion("01");
//		textureRegion[1][0] = textureAtlas.findRegion("02");
//		textureRegion[2][0] = textureAtlas.findRegion("03");
//		textureRegion[3][0] = textureAtlas.findRegion("04");
	}
	
	public DynamicObject(TextureRegion textureRegion) {
		super(textureRegion);
	}
	
	public DynamicObject(Texture texture){
		super(texture);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
		control(spriteBatch);
		
	}
	
	public void control(SpriteBatch batch){
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			this.setPosition(this.getX()-0.1f, this.getY()-(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.UP)){
			this.setPosition(this.getX()+0.1f, this.getY()+(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			this.setPosition(this.getX()+(0.1f), this.getY()-(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			this.setPosition(this.getX()-(0.1f), this.getY()+(0.1f/2));
		}
	}
}
