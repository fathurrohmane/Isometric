package com.gdx.isometric;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Isometric {

	private TextureAtlas atlasTiles;
	private TextureAtlas atlasStaticObjects;
	private String[][] dataTiles;
	private String[][][] dataStaticObjects;
	private int sizeMapX, sizeMapY;
	private Tile[][] tiles;
	private StaticObject[][][] staticObjects;
	
	public Vector2 position;
	public float width;
	public float height;
	private float tileHeight;
	
	private DynamicObject dynamicObject;

	// Create new Isometric | Input tile isometic and map
	public Isometric(FileHandle tileFile, FileHandle dataMapFile, int sizeMapX,
			int sizeMapY, float tileHight) {
		this.sizeMapX = sizeMapX;
		this.sizeMapY = sizeMapY;
		this.tileHeight = tileHight;
		atlasTiles = new TextureAtlas(tileFile);
		readDataTiles(dataMapFile);
		createTiles();
		position = new Vector2(0, 0);
		setWidth();
		setHeight();
	}

	private void readDataTiles(FileHandle dataTileFile) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				dataTileFile.read()));
		try {
			dataTiles = new String[sizeMapX][sizeMapY];
			String[] lines = new String[sizeMapY];
			int counterLines = 0;
			// split map data per line
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				else {
					lines[counterLines] = line;
					counterLines++;
				}
			}
			// split map data line into x and y
			int counterX = 0;
			int counterY = 0;
			for (int x = counterLines - 1; x >= 0; x--) {// loop for x axis
				String line = lines[x];
				//System.out.println(line);
				for (int y = 0; y < line.length(); y++) {// loop for y axis
					if (y % 2 == 0 && counterX != sizeMapX) {
						//System.out.println(counterX + " " + counterY);
						dataTiles[counterX][counterY] = line.charAt(y) + ""
								+ line.charAt(y + 1);
						counterX++;
					}
				}
				counterX = 0;
				counterY++;
			}
			//System.out.println("SPLIT DONE");
			for (int x = 0; x < sizeMapX; x++) {
				for (int y = 0; y < sizeMapY; y++) {
					//System.out.print(dataTiles[x][y] + " ");
				}
				//System.out.println();
			}
		} catch (Exception ex) {
			throw new GdxRuntimeException("Error reading text file: "
					+ dataTileFile, ex);
		}
	}

	private void createTiles() {
		tiles = new Tile[sizeMapX][sizeMapY];
		for (int x = 0; x < sizeMapX; x++) {
			for (int y = 0; y < sizeMapY; y++) {
				// System.out.println(x+","+y);
				tiles[x][y] = new Tile(atlasTiles.findRegion(dataTiles[x][y]));
				tiles[x][y].setTileName(dataTiles[x][y]);
				tiles[x][y].setPosition(
						(x + y) * tiles[x][y].getWidth() / 2,
						(y - x) * (tiles[x][y].getHeight() - tileHeight) / 2);
				tiles[x][y].setTilePos(new Vector2(x, y));
			}
		}
	}
	//need to edit
	public Vector2 isoTo2D(int x, int y) {
		Vector2 tempPos = new Vector2(0, 0);
		tempPos.x = (x / tiles[x][y].getWidth() / 2) + (y / tiles[x][y].getHeight() / 2);
		tempPos.y = (y / tiles[x][y].getHeight() / 2) - (x / tiles[x][y].getWidth() / 2);
		System.out.println("IsoTO2D :"+x+","+y+" -> "+tempPos.x+","+tempPos.y);
		System.out.println("Dynamic :"+dynamicObject.getX()+","+dynamicObject.getY());
		return tempPos;
//		map.x = (screen.x / TILE_WIDTH_HALF + screen.y / TILE_HEIGHT_HALF) / 2;
//		map.y = (screen.y / TILE_HEIGHT_HALF -(screen.x / TILE_WIDTH_HALF)) / 2;
		
	}

	public Vector2 twoDToIso(int x, int y) {
		Vector2 tempPos = new Vector2(0, 0);
		tempPos.x = (x + y) * tiles[x][y].getWidth() / 2;
		tempPos.y = (y - x) * (tiles[x][y].getHeight() - tileHeight) / 2;
		System.out.println("TwoDToIdo :"+x+","+y+" -> "+tempPos.x+","+tempPos.y);
		System.out.println("Dynamic :"+dynamicObject.getX()+","+dynamicObject.getY());
		
		return tempPos;
//		Basic isometric map to screen is:
//		screen.x = (map.x - map.y) * TILE_WIDTH_HALF;
//		screen.y = (map.x + map.y) * TILE_HEIGHT_HALF;
	}

	public void draw(SpriteBatch batch) {
		for (int x = 0; x < sizeMapX; x++) {
			for (int y = sizeMapY - 1; y >= 0; y--) {
				tiles[x][y].draw(batch);
				for (int q = 0; q < 4; q++) {
					if(staticObjects[x][y][q] != null){
						staticObjects[x][y][q].draw(batch);
					}
				}
				if(dynamicObject != null){
					dynamicObject.draw(batch);
				}
			}
		}
	}
	
	public void addStaticObject(FileHandle objectFile, FileHandle dataMapFile){
		atlasStaticObjects = new TextureAtlas(objectFile);
		readDataObject(dataMapFile);
	}
	
	private void readDataObject(FileHandle dataObjectFile){
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				dataObjectFile.read()));
		try {
			dataStaticObjects = new String[sizeMapX][sizeMapY][4];
			String[] lines = new String[sizeMapY*2];
			int counterLines = 0;
			// split map data per line
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				else {
					lines[counterLines] = line;
					counterLines++;
				}
			}
			// split map data line into x and y
			int counterX = 0;
			int counterY = 0;
			int quadran;
			
			if(counterLines % 2 == 0){
				quadran = 1;
			}else{
				quadran = 0;
			}
			
			for (int x = counterLines - 1; x >= 0; x--) {// loop for x axis
				String line = lines[x];
				//System.out.println(line);
				for (int y = 0; y < line.length(); y++) {// loop for y axis
					if (y % 2 == 0 && counterX != sizeMapX*2) {
						dataStaticObjects[counterX][counterY][quadran] = line.charAt(y) + ""
								+ line.charAt(y + 1);
						if(quadran == 1){
							quadran = 2;
						}else if(quadran == 2){
							quadran = 1;
							counterX++;
						}else if(quadran == 0){
							quadran = 3;
						}else if(quadran == 3){
							quadran = 0;
							counterX++;
						}
					}
				}
				if(x % 2 == 0){
					quadran = 1;
					counterY++;
				}else{
					quadran = 0;
				}
				counterX = 0;
			}
		} catch (Exception ex) {
			throw new GdxRuntimeException("Error reading text file: "
					+ dataObjectFile, ex);
		}
		
		createObjects();
	}
	
	private void createObjects() {
		staticObjects = new StaticObject[sizeMapX*2][sizeMapY*2][4];

		//int quadran = 1;
		for (int x = 0; x < sizeMapX; x++) {
			for (int y = 0; y < sizeMapY; y++) {
				for (int q = 0; q < 4; q++) {
					if(!dataStaticObjects[x][y][q].equals("00")){
						staticObjects[x][y][q] = new StaticObject(atlasStaticObjects.findRegion(dataStaticObjects[x][y][q]));
						switch (q) {
						case 0:
							staticObjects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() / 2, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.75f);
							break;
						case 1:
							staticObjects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() * 0.25f, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.5f);
							break;
						case 2:
							staticObjects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() / 2, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.25f);
							break;
						case 3:
							staticObjects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() * 0.75f,tiles[x][y].getY() + tiles[x][y].getHeight() / 2);
							break;

						default:
							break;
						}
					}else{
						staticObjects[x][y][q] = null;
					}
				}
				
			}
		}
	}
	
	public void addDynamicObject(FileHandle file, Vector2 pos){
		//dynamicObject = new DynamicObject(file);
		dynamicObject = new DynamicObject(new TextureAtlas(file).findRegion("01"));
		dynamicObject.setPosition(tiles[(int)pos.x][(int)pos.y].getX(), tiles[(int)pos.x][(int)pos.y].getY());
	}
	
	public void setPosition(Vector2 position) {
		for (int x = 0; x < sizeMapX; x++) {
			for (int y = 0; y < sizeMapY; y++) {
				tiles[x][y].setPosition(
						position.x + (x + y) * tiles[x][y].getWidth() / 2,
						position.y + (y - x) * (tiles[x][y].getHeight() - 15) / 2);//NEED EDIT
				tiles[x][y].setTilePos(new Vector2(x, y));
				for (int q = 0; q < 4; q++) {
					if(staticObjects[x][y][q] != null){
						switch (q) {
						case 0:
							staticObjects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() / 2, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.75f);
							break;
						case 1:
							staticObjects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() * 0.25f, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.5f);
							break;
						case 2:
							staticObjects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() / 2, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.25f);
							break;
						case 3:
							staticObjects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() * 0.75f,tiles[x][y].getY() + tiles[x][y].getHeight() / 2);
							break;

						default:
							break;
						}
					}
				}
			}
		}
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	private void setWidth() {
		this.width = sizeMapX*tiles[0][0].getWidth() / 2;
	}
	
	private void setHeight() {
		this.height = sizeMapY*tiles[0][0].getHeight() / 2;
	}
	
	public void isColliding(IsometricObject object){
		//int row1 = object.
	}
	
	
}
//http://clintbellanger.net/articles/isometric_math/