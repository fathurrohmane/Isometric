package com.gdx.isometric;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class IsometricObject extends Sprite {

	public IsometricObject(){
		super();
	}
	
	public IsometricObject(TextureRegion textureRegion) {
		super(textureRegion);
	}

	public IsometricObject(Texture texture) {
		super(texture);
	}
}
