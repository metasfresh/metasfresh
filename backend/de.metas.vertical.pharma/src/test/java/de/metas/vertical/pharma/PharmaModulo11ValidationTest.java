package de.metas.vertical.pharma;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.adempiere.test.AdempiereTestHelper;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

// TODO
public class PharmaModulo11ValidationTest
{
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@Before
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

	}

	@Test
	public void testModulo11_PZN7_Valid1() throws CheckDigitException
	{

		final String btm = "1234562";

		assertTrue(PharmaModulo11Validator.isValid(btm));

	}

	@Test
	public void testModulo11_PZN7_Valid2()
	{
		final String pzn = "0012345";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_PZN7_Valid3()
	{
		final String pzn = "6319429";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_PZN7_Valid4()
	{
		final String pzn = "4877800";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_PZN7_Valid5()
	{
		final String pzn = "0003211";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_PZN8_Valid1()
	{
		final String pzn = "00987650";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_PZN8_Valid2()
	{
		final String pzn = "04877800";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_PZN8_Valid3()
	{
		final String pzn = "02010215";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_PZN8_Valid4()
	{
		final String pzn = "07563545";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_PZN8_Valid5()
	{
		final String pzn = "00517944";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_InvalidLength_TooSmall()
	{
		final String pzn = "005179";

		assertFalse(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_InvalidLength_TooBig()
	{
		final String pzn = "005179444";

		assertFalse(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_InvalidPZN7()
	{
		final String pzn = "0003212";

		assertFalse(PharmaModulo11Validator.isValid(pzn));
	}

	@Test
	public void testModulo11_InvalidPZN8()
	{
		final String pzn = "02010216";

		assertFalse(PharmaModulo11Validator.isValid(pzn));
	}

}
