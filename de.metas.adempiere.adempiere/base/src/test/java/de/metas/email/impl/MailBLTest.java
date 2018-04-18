package de.metas.email.impl;

import static org.junit.Assert.assertEquals;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

import de.metas.email.IMailBL;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MailBLTest
{
	private MailBL mailBL;

	@Before
	public void init()
	{
		mailBL = (MailBL)Services.get(IMailBL.class);
	}

	@Test
	public void test()
	{
		assertMailValid(false, null);
		assertMailValid(false, "");
		assertMailValid(false, "   ");

		assertMailValid(true, "user@domain");
		assertMailValid(true, "user@domain.tld");
		assertMailValid(true, "user.name@domain.tld");
		assertMailValid(true, "user_name@domain.tld");
		assertMailValid(true, "user_name123@domain.tld");

		assertMailValid(false, "user_without_domain");
		assertMailValid(false, "user@domain@invalid");
	}

	private void assertMailValid(final boolean validExpected, final String email)
	{
		boolean validActual = false;
		try
		{
			mailBL.validateEmail(email);
			validActual = true;
		}
		catch (AdempiereException ex)
		{
			validActual = false;
			System.out.println("Address `" + email + "` not valid because: " + ex.getLocalizedMessage());
		}

		assertEquals("Invalid email expected for " + email, validExpected, validActual);
	}
}
