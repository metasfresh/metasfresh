package org.compiere.db;

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

public class DatabaseTest
{
	@Test
	public void test_convertDecimalPatternToPG()
	{
		// NOTE: "FM" means "fill mode (suppress padding blanks and trailing zeroes)"
		// see http://www.postgresql.org/docs/9.1/static/functions-formatting.html#FUNCTIONS-FORMATTING-NUMERIC-TABLE
		test_convertDecimalPatternToPG("00000", "FM00000");
		test_convertDecimalPatternToPG("#####", "FM99999");
		test_convertDecimalPatternToPG("00###", "FM00999");
	}

	private void test_convertDecimalPatternToPG(final String javaDecimalFormat, final String expectedPGFormat)
	{
		final String actualPGFormat = Database.convertDecimalPatternToPG(javaDecimalFormat);
		Assert.assertEquals("Invalid converted PG format for java format: " + javaDecimalFormat, expectedPGFormat, actualPGFormat);
	}
}
