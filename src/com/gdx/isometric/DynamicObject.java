package com.gdx.isometric;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class DynamicObject extends IsometricObject{

	private Tile placedTile;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private TextureRegion[][] textureRegion;
	private Orientation orientation;
	private ShapeRenderer rectangle = new ShapeRenderer();
	
	public DynamicObject(FileHandle file) {
		textureAtlas = new TextureAtlas(file);
		orientation = Orientation.NORTH;
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
			this.setPosition(this.getX()-0.1f, this.getY()-(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.UP)){
			orientation = Orientation.NORTH;
			this.setPosition(this.getX()+0.1f, this.getY()+(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			orientation = Orientation.EAST;
			this.setPosition(this.getX()+0.1f, this.getY()-(0.1f/2));
		}else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			orientation = Orientation.WEST;
			this.setPosition(this.getX()-0.1f, this.getY()+(0.1f/2));
		}
	}
	
	public Orientation getOrientation() {
		return orientation;
	};
	
	public void drawRectangle(){
		rectangle.begin(ShapeType.Rectangle);
		rectangle.setColor(1, 1, 0, 1);
		rectangle.rect(getX(), getY(), getWidth(), getHeight());
		rectangle.end();
	}
}
