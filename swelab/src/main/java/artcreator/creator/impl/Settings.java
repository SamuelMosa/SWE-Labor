package artcreator.creator.impl;

import java.util.Dictionary;

public class Settings {
	private float toothpickDistance;
	private int numToothpicks;
	private String[] aspectRatios = { "16:9", "4:3", "1:1", "2:3" };
	private Dictionary<Integer, String> colorPalette;

	public Settings(float toothpickDistance, int numToothpicks, String[] aspectRatios,
			Dictionary<Integer, String> colorPalette) {
		super();
		this.toothpickDistance = toothpickDistance;
		this.numToothpicks = numToothpicks;
		this.aspectRatios = aspectRatios;
		this.colorPalette = colorPalette;
	}
}
