package artcreator.creator.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Settings {
	private float toothpickDistance;
	private int numToothpicks;
	private String[] aspectRatios = { "16:9", "4:3", "1:1", "2:3" };
	private List<String> colorPalette;

	public Settings() {
	}

	public Settings(float toothpickDistance, int numToothpicks) {
		super();
		this.toothpickDistance = toothpickDistance;
		this.numToothpicks = numToothpicks;
		this.colorPalette = new ArrayList<>();
	}
	
	public int getColorId(Color color) {
	    for (int i = 0; i < colorPalette.size(); i++) {
	        if (Color.decode(colorPalette.get(i)).equals(color)) {
	            return i;
	        }
	    }
	    return -1; // falls die Farbe nicht gefunden wurde
	}

	public float getToothpickDistance() {
		return toothpickDistance;
	}

	public void setToothpickDistance(float toothpickDistance) {
		this.toothpickDistance = toothpickDistance;
	}

	public int getNumToothpicks() {
		return numToothpicks;
	}

	public void setNumToothpicks(int numToothpicks) {
		this.numToothpicks = numToothpicks;
	}

	public String[] getAspectRatios() {
		return aspectRatios;
	}

	public void setAspectRatios(String[] aspectRatios) {
		this.aspectRatios = aspectRatios;
	}

	public int insertColor(String colorCode) {
		colorPalette.add(colorCode);
		return colorPalette.size() - 1;
	}

	public void outputColors() {
		for (int i = 0; i < colorPalette.size(); i++) {
			System.out.println(i + ": " + colorPalette.get(i));
		}
	}

	public List<String> getColorPalette() {
		return colorPalette;
	}

	public void setColorPalette(List<String> colorPalette) {
		this.colorPalette = colorPalette;
	}
	
}
