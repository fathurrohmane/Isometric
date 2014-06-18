package com.gdx.isometric;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Isometric {

	private TextureAtlas atlasTiles;
	private TextureAtlas atlasObjects;
	private String[][] dataTiles;
	private String[][][] dataObjects;
	private int sizeMapX, sizeMapY;
	private Tile[][] tiles;
	private Object[][][] objects;
	
	public Vector2 position;
	public float width;
	public float height;

	// Create new Isometric | Input tile isometic and map
	public Isometric(FileHandle tileFile, FileHandle dataMapFile, int sizeMapX,
			int sizeMapY) {
		this.sizeMapX = sizeMapX;
		this.sizeMapY = sizeMapY;
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
				System.out.println(line);
				for (int y = 0; y < line.length(); y++) {// loop for y axis
					if (y % 2 == 0 && counterX != sizeMapX) {
						System.out.println(counterX + " " + counterY);
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
						(y - x) * (tiles[x][y].getHeight() - 15) / 2);//NEED EDIT
				tiles[x][y].setTilePos(new Vector2(x, y));
			}
		}
	}
	//need to edit
	public Vector2 isoTo2D(int x, int y) {
		Vector2 tempPos = new Vector2(0, 0);
		tempPos.x = (y / tiles[x][y].getHeight() / 2) - (x / tiles[x][y].getWidth() / 2);
		tempPos.y = (y / tiles[x][y].getHeight() / 2) + (x / tiles[x][y].getWidth() / 2);
		return tempPos;
//		map.x = (screen.x / TILE_WIDTH_HALF + screen.y / TILE_HEIGHT_HALF) / 2;
//		map.y = (screen.y / TILE_HEIGHT_HALF -(screen.x / TILE_WIDTH_HALF)) / 2;
		
	}

	public Vector2 twoDToIso(int x, int y) {
		Vector2 tempPos = new Vector2(0, 0);
		tempPos.x = (x + y) * tiles[x][y].getWidth() / 2;
		tempPos.y = (y - x) * (tiles[x][y].getHeight() - 15) / 2;//NEED EDIT
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
					if(objects[x][y][q] != null){
						objects[x][y][q].draw(batch);
					}
				}
			}
		}
	}
	
	public void addObject(FileHandle objectFile, FileHandle dataMapFile){
		atlasObjects = new TextureAtlas(objectFile);
		readDataObject(dataMapFile);
	}
	
	private void readDataObject(FileHandle dataObjectFile){
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				dataObjectFile.read()));
		try {
			dataObjects = new String[sizeMapX][sizeMapY][4];
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
			int quadran = 1;
			for (int x = counterLines - 1; x >= 0; x--) {// loop for x axis
				String line = lines[x];
				//System.out.println(line);
				for (int y = 0; y < line.length(); y++) {// loop for y axis
					if (y % 2 == 0 && counterX != sizeMapX*2) {
						dataObjects[counterX][counterY][quadran] = line.charAt(y) + ""
								+ line.charAt(y + 1);
						if(quadran == 1){
							quadran = 0;
						}else if(quadran == 0){
							quadran = 1;
							counterX++;
						}else if(quadran == 2){
							quadran = 3;
						}else if(quadran == 3){
							quadran = 2;
							counterX++;
						}
					}
				}
				if(x % 2 == 0){
					quadran = 1;
					counterY++;
				}else{
					quadran = 2;
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
		objects = new Object[sizeMapX*2][sizeMapY*2][4];

		//int quadran = 1;
		for (int x = 0; x < sizeMapX; x++) {
			for (int y = 0; y < sizeMapY; y++) {
				for (int q = 0; q < 4; q++) {
					if(!dataObjects[x][y][q].equals("00")){
						objects[x][y][q] = new Object(atlasObjects.findRegion(dataObjects[x][y][q]));
						switch (q) {
						case 0:
							objects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() / 2, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.75f);
							break;
						case 1:
							objects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() * 0.25f, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.5f);
							break;
						case 2:
							objects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() / 2, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.25f);
							break;
						case 3:
							objects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() * 0.75f,tiles[x][y].getY() + tiles[x][y].getHeight() / 2);
							break;

						default:
							break;
						}
					}else{
						objects[x][y][q] = null;
					}
				}
				
			}
		}
	}
	
	public void setPosition(Vector2 position) {
		for (int x = 0; x < sizeMapX; x++) {
			for (int y = 0; y < sizeMapY; y++) {
				tiles[x][y].setPosition(
						position.x + (x + y) * tiles[x][y].getWidth() / 2,
						position.y + (y - x) * (tiles[x][y].getHeight() - 15) / 2);//NEED EDIT
				tiles[x][y].setTilePos(new Vector2(x, y));
				for (int q = 0; q < 4; q++) {
					if(objects[x][y][q] != null){
						switch (q) {
						case 0:
							objects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() / 2, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.75f);
							break;
						case 1:
							objects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() * 0.25f, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.5f);
							break;
						case 2:
							objects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() / 2, tiles[x][y].getY() + tiles[x][y].getHeight() * 0.25f);
							break;
						case 3:
							objects[x][y][q].setPosition(tiles[x][y].getX() + tiles[x][y].getWidth() * 0.75f,tiles[x][y].getY() + tiles[x][y].getHeight() / 2);
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
}
//http://clintbellanger.net/articles/isometric_math/