package dev.anime.rpg.base;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	/** Takes the list passed and returns a copy of it. **/
	public static List<?> copyList(List<?> list) {
		List<Object> newList = new ArrayList<Object>();
		for (Object obj : list) {
			newList.add(obj);
		}
		return newList;
	}
	
	/** Adds two Object[] together to form a longer combined Object[]. **/
	public static Object[] addArrays(Object[] array1, Object[] array2) {
		int newLength = array1.length + array2.length;
		Object[] newArray = new Object[newLength];
		for (int i = 0; i < newLength; i++) {
			if (i < array1.length) {
				newArray[i] = array1[i];
			} else {
				newArray[i] = array2[i - array1.length];
			}
		}
		return newArray;
	}
	
	/** Checks if positionX and positionY are within the x, y, width, height box. **/
	public static boolean isWithin(int x, int y, int width, int height, int positionX, int positionY) {
		return positionX >= x && positionX <= x + width && positionY >= y && positionY <= y + height;
	}
	
	/** Checks if positionX and positionY are within the Rectangle's bounds. **/
	public static boolean isWithin(Rectangle rect, int positionX, int positionY) {
		return isWithin((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), positionX, positionY);
	}
	
}
