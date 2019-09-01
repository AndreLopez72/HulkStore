package com.todouno.hulkstore.infraestructure.helpers.util;

import java.util.Random;

import com.todouno.hulkstore.infraestructure.helpers.util.NumberGenerator;

public class IsbnGenerator implements NumberGenerator {

	@Override
    public String generarNumero() {
        return "13-84356-" + Math.abs(new Random().nextInt());
    }
	
}