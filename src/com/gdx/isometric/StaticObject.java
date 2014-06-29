package com.gdx.isometric;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class StaticObject extends Sprite {

	private Vector2 tilePosOnMap;
	private int quadran;
	private boolean walkable;
	
	public StaticObject(TextureRegion textureRegion) {
		super(textureRegion);
		walkable = false;
	}
	
	public StaticObject(TextureRegion textureRegion, Boolean walkable) {
		super(textureRegion);
		this.walkable = walkable;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
	}
	
	public boolean isWalkable() {
		return walkable;
	}
	
	
	
}
