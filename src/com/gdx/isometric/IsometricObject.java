package com.gdx.isometric;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class IsometricObject extends Sprite {

	protected float nextX;
	protected float nextY;
	protected float widhtForCollision;
	protected float heightForCollision;
	protected boolean useCustomWidthAndHeight;
	
	public IsometricObject(){
		super();
	}
	
	public IsometricObject(TextureRegion textureRegion) {
		super(textureRegion);
	}

	public IsometricObject(Texture texture) {
		super(texture);
	}
	
	public float top(){
		if(useCustomWidthAndHeight){
			return this.getY() - this.heightForCollision / 2;
		}else{
			return this.getY() - this.getHeight() / 2;
		}		 
	}
	
	public float bottom(){
		if(useCustomWidthAndHeight){
			return this.getY() + this.heightForCollision / 2;
		}else{
			return this.getY() + this.getHeight() / 2;
		}	
	}
	
	public float left(){
		if(useCustomWidthAndHeight){
			return this.getX() - this.widhtForCollision / 2;
		}else{
			return this.getX() - this.getWidth() / 2;
		}
	}
	
	public float right(){
		if(useCustomWidthAndHeight){
			return this.getX() + this.widhtForCollision / 2;
		}else{
			return this.getX() + this.getWidth() / 2;
		}
	}
	
	public float nextTop(){
		if(useCustomWidthAndHeight){
			return this.nextY - this.heightForCollision / 2;
		}else{
			return this.nextY - this.getHeight() / 2;
		}		 
	}
	
	public float nextBottom(){
		if(useCustomWidthAndHeight){
			return this.nextY + this.heightForCollision / 2;
		}else{
			return this.nextY + this.getHeight() / 2;
		}	
	}
	
	public float nextLeft(){
		if(useCustomWidthAndHeight){
			return this.nextX - this.widhtForCollision / 2;
		}else{
			return this.nextX - this.getWidth() / 2;
		}
	}
	
	public float nextRight(){
		if(useCustomWidthAndHeight){
			return this.nextX + this.widhtForCollision / 2;
		}else{
			return this.nextX + this.getWidth() / 2;
		}
	}
}
