package de.metas.vertical.pharma;

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

//TODO
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
	public void testModulo11_BTMNo() throws CheckDigitException
	{

		final String  btm = "2123456";

		assertTrue(PharmaModulo11Validator.isValid(btm));

	}

	@Test
	public void testModulo11_PZNNo()
	{
		final String pzn = "00987650";

		assertTrue(PharmaModulo11Validator.isValid(pzn));
	}

}
