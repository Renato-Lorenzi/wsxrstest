package br.com.rml.utils;

import br.com.rml.model.Dojo;

public enum Entity {

	DOJO("dojos", Dojo.class);

	private String entityName;
	private Class<?> clazz;

	Entity(String entityName, Class<?> clazz) {
		this.entityName = entityName;
		this.clazz = clazz;
	}

	public String getEntityName() {
		return entityName;
	}

	public static Entity byClass(Class<?> clazz) {
		for (Entity entity : values()) {
			if (entity.clazz.equals(clazz)) {
				return entity;
			}
		}
		return null;
	}
}
