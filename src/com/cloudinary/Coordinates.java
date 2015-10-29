package com.cloudinary;

import java.util.ArrayList;
import java.util.Collection;

import com.cloudinary.utils.Rectangle;
import com.cloudinary.utils.StringUtils;
import com.cloudinary.codename1.util.CN1String;

public class Coordinates {

	Collection<Rectangle> coordinates = new ArrayList<Rectangle>();

	public Coordinates() {
	}

	public Coordinates(Collection<Rectangle> coordinates) {
		this.coordinates = coordinates;
	}

	public Coordinates(int[] rect) {
		Collection<Rectangle> coordinates = new ArrayList<Rectangle>();
		if (rect.length != 4) {
			throw new IllegalArgumentException("Must supply exactly 4 values for coordinates (x,y,width,height)");
		}
		coordinates.add(new Rectangle(rect[0], rect[1], rect[2], rect[3]));
		this.coordinates = coordinates;
	}

	public Coordinates(Rectangle rect) {
		Collection<Rectangle> coordinates = new ArrayList<Rectangle>();
		coordinates.add(rect);
		this.coordinates = coordinates;
	}

	public Coordinates(String stringCoords) throws IllegalArgumentException {
		Collection<Rectangle> coordinates = new ArrayList<Rectangle>();
		for (String stringRect : new CN1String(stringCoords).split("\\|")) {
			if (StringUtils.isEmpty(stringRect))
				continue;
			String[] elements = new CN1String(stringRect).split(",");
			if (elements.length != 4) {
				throw new IllegalArgumentException("Must supply exactly 4 values for coordinates (x,y,width,height) "+elements.length+" supplied: "+stringRect);
			}
			coordinates.add(new Rectangle(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), Integer
					.parseInt(elements[3])));
		}
		this.coordinates = coordinates;
	}

	public static Coordinates parseCoordinates(Object coordinates) throws IllegalArgumentException {
		if (coordinates instanceof Coordinates) {
			return (Coordinates) coordinates;
		} else if (coordinates instanceof int[]) {
			return new Coordinates((int[]) coordinates);
		} else if (coordinates instanceof Rectangle) {
			return new Coordinates((Rectangle) coordinates);
		} else {
			return new Coordinates(coordinates.toString());
		}
	}

	public void addRect(Rectangle rect) {
		this.coordinates.add(rect);
	}

	public Collection<Rectangle> underlaying() {
		return this.coordinates;
	}

	@Override
	public String toString() {
		ArrayList<String> rects = new ArrayList<String>();
		for (Rectangle rect : this.coordinates) {
			rects.add(rect.x + "," + rect.y + "," + rect.width + "," + rect.height);
		}
		return StringUtils.join(rects, "|");
	}

}
