package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.junit.Assert;
import org.junit.Test;

public class UtilReplaceTests
{
	@Test
	public void test_replaceNonDigitCharsWithZero1()
	{
		final String stringToModify = "   9   io   * && ^^^% i	";
		
		final String expected =       "000900000000000000000000";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
	@Test
	public void test_replaceNonDigitCharsWithZero2()
	{
		final String stringToModify = "   9   io   80 && ^17 i	!@#$%0";
		
		final String expected =       "000900000000800000017000000000";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
	
	@Test
	public void test_replaceNonDigitCharsWithZero3()
	{
		final String stringToModify = "0";
		
		final String expected =       "0";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
	@Test
	public void test_replaceNonDigitCharsWithZero4()
	{
		final String stringToModify = "123456789";
		
		final String expected =       "123456789";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
	
	@Test
	public void test_replaceNonDigitCharsWithZero5()
	{
		final String stringToModify = "~!@#$%^&*()_-+=    	";
		
		final String expected =       "00000000000000000000";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
}
