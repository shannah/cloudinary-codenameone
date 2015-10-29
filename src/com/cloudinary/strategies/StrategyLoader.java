package com.cloudinary.strategies;

import java.util.List;

public class StrategyLoader {

	@SuppressWarnings("unchecked")
	public static <T> T load(Class clazz) {
		T result = null;
		try {
			//Class<?> clazz = Class.forName(className);
			result = (T) clazz.newInstance();
		} catch (Exception e) {
		}
		return result;
	}

	public static <T> T find(List<Class> strategies) {
		for (int i = 0; i < strategies.size(); i++) {
			T strategy = load(strategies.get(i));
			if (strategy != null) {
				return strategy;
			}
		}
		return null;
		
	}

	public boolean exists(List<Class> strategies) {
		return find(strategies) != null;
	}
	
}
