package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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


import java.util.Properties;

import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

import de.metas.printing.api.IPrintClientsBL;
import de.metas.printing.model.I_AD_Print_Clients;
import de.metas.util.Services;

public class PrintClientsBLTest extends AbstractPrintingTest
{
	@Test
	public void test_createPrintClientsEntry()
	{
		final Properties ctx = Env.deriveCtx(Env.getCtx());
		final int adSessionId = 12345;
		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, adSessionId);

		final String hostKey = "hostKeyTest01";

		final I_AD_Print_Clients entry1 = Services.get(IPrintClientsBL.class).createPrintClientsEntry(ctx, hostKey);

		Assert.assertNotNull("Printing clients entry shall be created", entry1);
		Assert.assertEquals("Invalid AD_Session_ID", adSessionId, entry1.getAD_Session_ID());
		Assert.assertEquals("Invalid HostKey", hostKey, entry1.getHostKey());
	}

	@Test
	public void test_createPrintClientsEntry_NoDuplicates()
	{
		final Properties ctx = Env.deriveCtx(Env.getCtx());
		final int adSessionId = 12345;
		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, adSessionId);

		final String hostKey1 = "hostKeyTest01-01";
		final I_AD_Print_Clients entry1 = Services.get(IPrintClientsBL.class).createPrintClientsEntry(ctx, hostKey1);

		final I_AD_Print_Clients entry2 = Services.get(IPrintClientsBL.class).createPrintClientsEntry(ctx, hostKey1);
		Assert.assertEquals("No duplicate records shall be created for same hostkey",
				entry1.getAD_Print_Clients_ID(), entry2.getAD_Print_Clients_ID());
	}

}
