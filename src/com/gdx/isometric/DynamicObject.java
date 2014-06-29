package com.gdx.isometric;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DynamicObject extends IsometricObject{

	private Tile placedTile;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private TextureRegion[][] textureRegion;
	private Orientation orientation;
	
	public DynamicObject(FileHandle file) {
		textureAtlas = new TextureAtlas(file);
		orientation = Orientation.NORTH;
//		animation = new Animation(1/15f, textureAtlas.getRegions());
//		textureRegion = new TextureRegion[4][1];
//		textureRegion[0][0] = textureAtlas.findRegion("01");
//		textureRegion[1][0] = textureAtlas.findRegion("02");
//		textureRegion[2][0] = textureAtlas.findRegion("03");
//		textureRegion[3][0] = textureAtlas.findRegion("04");
	}
	
	public DynamicObject(TextureRegion textureRegion) {
		super(textureRegion);
		orientation = Orientation.NORTH;
	}
	
	public DynamicObject(Texture texture){
		super(texture);
		orientation = Orientation.NORTH;
	}
	
	public void geser(){
		this.setPosition(this.getX()-0.1f, this.getY()-(0.1f/2));
	}
	
	public boolean cekX(int xLeft, int xRight){
		if(getX() >= xLeft && getX() < xRight){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean cekY(int yTop, int yBottom){
		if(getY() <= yTop && getY() > yBottom){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
		//Control
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			orientation = Orientation.SOUTH;
			//this.setPosition(this.getX()-0.1f, this.getY()-(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.UP)){
			orientation = Orientation.NORTH;
			//this.setPosition(this.getX()+0.1f, this.getY()+(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			orientation = Orientation.EAST;
			//this.setPosition(this.getX()+0.1f, this.getY()-(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			orientation = Orientation.WEST;
			//this.setPosition(this.getX()-0.1f, this.getY()+(0.1f/2));
		}
	}
	
	public Orientation getOrientation() {
		return orientation;
	};
}
