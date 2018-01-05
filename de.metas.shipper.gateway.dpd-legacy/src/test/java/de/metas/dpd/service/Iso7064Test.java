package de.metas.dpd.service;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.junit.Assert.*;
import org.junit.Test;



public class Iso7064Test {

	@Test
	public void getVal() {

		System.out.println((char) 37);

		assertEquals(Iso7064.getVal('0'), 0);
		assertEquals(Iso7064.getVal('9'), 9);
		assertEquals(Iso7064.getVal('A'), 10);
		assertEquals(Iso7064.getVal('Z'), 35);
	}

	@Test
	public void getChar() {

		assertEquals(Iso7064.getChar(0), '0');
		assertEquals(Iso7064.getChar(9), '9');
		assertEquals(Iso7064.getChar(10), 'A');
		assertEquals(Iso7064.getChar(35), 'Z');
	}

	@Test
	public void compute() {

		assertEquals(Iso7064.compute("007110601632532948375179276"), 'A');
		assertEquals(Iso7064.compute("01631234567890"), 'Y');

		assertEquals(Iso7064.compute("01575007385814"), 'M');
		assertEquals(Iso7064.compute("008778901575007385814136276"), 'K');
	}

}
