package artcreator.creator.impl;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class Settings {
	private float toothpickDistance;
	private int numToothpicks;
	private String[] aspectRatios = { "16:9", "4:3", "1:1", "2:3" };
	private Map<Integer, String> colorPalette;

	public Settings() {}
	
	public Settings(float toothpickDistance, int numToothpicks) {
		super();
		this.toothpickDistance = toothpickDistance;
		this.numToothpicks = numToothpicks;
		this.colorPalette = new Hashtable<>();
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

	public Map<Integer, String> getColorPalette() {
		return colorPalette;
	}

	public void setColorPalette(Map<Integer, String> colorPalette) {
		this.colorPalette = colorPalette;
	}
}
