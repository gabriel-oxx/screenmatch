package br.com.media.screenmatch.models;

import net.bytebuddy.implementation.bytecode.Throw;

public enum Category {
	ACTION("Ação"),
	COMEDY("Comédia"),
	CRIME("Crime"),
	DRAMA("Drama");

	private String portugueseCategory;

	Category(String portugueseCategory) {
		this.portugueseCategory = portugueseCategory;
	}

	public static Category fromPortuguese(String text) {
		for (Category c : Category.values()) {
			if (c.portugueseCategory.equalsIgnoreCase(text)) {
				return c;
			}
		}
		throw new IllegalArgumentException("Nenhumma categoria encontradacom o nome de " + text);
	}
}
