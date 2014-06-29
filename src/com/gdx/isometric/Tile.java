package com.gdx.isometric;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Tile extends IsometricObject{
	
	private String tileName;
	private Vector2 tilePosOnMap;
	private BitmapFont coordinateText;
	private Boolean walkable;
	
	public Tile(TextureRegion textureRegion){
		super(textureRegion);
		coordinateText = new BitmapFont();
	}
	public void setTileName(String tileName) {
		this.tileName = tileName;
	}
	public String getTileName() {
		return tileName;
	}
	public Vector2 getTilePos() {
		return tilePosOnMap;
	}
	public void setTilePos(Vector2 tilePos) {
		this.tilePosOnMap = tilePos;
	}
	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
		//coordinateText.draw(spriteBatch, tilePosOnMap.x+","+tilePosOnMap.y, this.getX(), this.getY());
	}
}
