package de.metas.printing.client.util;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class MapFormatTest
{
	@Test
	public void test_format_simple() throws Exception
	{
		final String pattern = "getNextPrintPackage/{SessionID}/{TransactionID}";
		final Map<String, String> args = new HashMap<String, String>();
		args.put("SessionID", "1");
		args.put("TransactionID", "2");

		final String strExpected = "getNextPrintPackage/1/2";
		final String strActual = MapFormat.format(pattern, args);

		Assert.assertEquals(strExpected, strActual);
	}

	@Test
	public void test_format_duplicate_args() throws Exception
	{
		final String pattern = "getNextPrintPackage/{SessionID}/{TransactionID}/and_again_session/{SessionID}/end";
		final Map<String, String> args = new HashMap<String, String>();
		args.put("SessionID", "1");
		args.put("TransactionID", "2");

		final String strExpected = "getNextPrintPackage/1/2/and_again_session/1/end";
		final String strActual = MapFormat.format(pattern, args);

		Assert.assertEquals(strExpected, strActual);
	}

	@Test
	public void test_format_missing_args() throws Exception
	{
		final String pattern = "getNextPrintPackage/{SessionID}/{TransactionID}/and_a_missing_arg/{MissingArgID}/end";
		final Map<String, String> args = new HashMap<String, String>();
		args.put("SessionID", "1");
		args.put("TransactionID", "2");

		final String strExpected = "getNextPrintPackage/1/2/and_a_missing_arg/{MissingArgID}/end";
		final String strActual = MapFormat.format(pattern, args);

		Assert.assertEquals(strExpected, strActual);
	}

	@Test
	public void test_format_null_arguments() throws Exception
	{
		final String pattern = "getNextPrintPackage";

		final String strExpected = pattern;
		final String strActual = MapFormat.format(pattern, null);

		Assert.assertEquals(strExpected, strActual);
	}

	@Test(expected = NullPointerException.class)
	public void test_format_null_pattern() throws Exception
	{
		final String pattern = null;
		final Map<String, String> args = new HashMap<String, String>();
		args.put("SessionID", "1");
		args.put("TransactionID", "2");

		final String strExpected = pattern;
		final String strActual = MapFormat.format(pattern, args); // shall throw NPE

		Assert.assertEquals(strExpected, strActual);
	}
}
