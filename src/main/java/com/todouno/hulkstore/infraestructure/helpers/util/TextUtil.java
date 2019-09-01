package com.todouno.hulkstore.infraestructure.helpers.util;

public class TextUtil {

	public String limpiar(String limpiarTexto) {
        return limpiarTexto.replaceAll("\\s+", " ");
    }
	
}
